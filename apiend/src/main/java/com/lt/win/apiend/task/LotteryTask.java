package com.lt.win.apiend.task;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lt.win.dao.generator.po.*;
import com.lt.win.dao.generator.service.LotteryBetslipsService;
import com.lt.win.dao.generator.service.LotteryOpenService;
import com.lt.win.dao.generator.service.LotteryPlateService;
import com.lt.win.service.base.UserCoinBase;
import com.lt.win.service.cache.redis.LotteryCache;
import com.lt.win.utils.DateNewUtils;
import com.lt.win.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @Auther: Jess
 * @Date: 2023/9/15 12:17
 * @Description:彩票定时任务
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LotteryTask {
    private final LotteryPlateService lotteryPlateServiceImpl;
    private final LotteryOpenService lotteryOpenServiceImpl;
    private final LotteryBetslipsService lotteryBetslipsServiceImpl;
    private final UserCoinBase userCoinBase;
    private final LotteryCache lotteryCache;

    /**
     * 每期间隔时间(秒)
     **/
    private static final int INTERVAL_SECOND = 300;

    private static final String PK10 = "pk10";

    /**
     * @Description 生成随机的开奖号码
     * @Param []
     **/
    @Scheduled(cron = "15 */5 * * * ?")
    public void initFirst() {
        String periodsNo = lotteryCache.getUpPeriodsNo();
        log.info("开始首次生产开奖号码：{}", periodsNo);
        initLotteryOpen();
    }

    /**
     * @Description 生成随机的开奖号码
     * @Param []
     **/
    @Scheduled(cron = "20 */5 * * * ?")
    public void initSecond() {
        String periodsNo = lotteryCache.getUpPeriodsNo();
        log.info("开始二次生成开奖号码：{}", periodsNo);
        initLotteryOpen();
    }

    /**
     * @Description 生产开奖号码
     * @Param []
     **/
    public void initLotteryOpen() {
        int toDaySecond = DateNewUtils.now() - DateNewUtils.todayStart();
        int num = toDaySecond / INTERVAL_SECOND;
        num = toDaySecond % INTERVAL_SECOND == 0 ? num : num + 1;
        List<Integer> list = IntStream.range(1, 11)
                .boxed()
                .collect(Collectors.toList());
        Integer now = DateUtils.getCurrentTime();
        List<LotteryMainPlate> lotteryMainPlateList = lotteryCache.getLotteryMainPlateList();
        //生成期号
        String periodsNo = DateUtils.yyyyMMdd2(DateUtils.getCurrentTime()) + String.format("%03d", num);
        for (LotteryMainPlate mainPlate : lotteryMainPlateList) {
            Collections.shuffle(list);
            Integer openCode = list.get(0);
            LotteryOpen lotteryOpen = lotteryOpenServiceImpl.getOne(new LambdaQueryWrapper<LotteryOpen>()
                    .eq(LotteryOpen::getMainCode, mainPlate.getCode())
                    .eq(LotteryOpen::getPeriodsNo, periodsNo));
            if (Objects.isNull(lotteryOpen)) {
                lotteryOpen = new LotteryOpen();
                lotteryOpen.setPeriodsNo(periodsNo);
                lotteryOpen.setLotteryCode(PK10);
                lotteryOpen.setLotteryName(PK10);
                lotteryOpen.setCreatedAt(now);
            }
            lotteryOpen.setMainCode(mainPlate.getCode());
            lotteryOpen.setOpenCode(openCode);
            lotteryOpen.setOpenAllCode(StringUtils.join(list, ","));
            lotteryOpen.setUpdatedAt(now);
            lotteryOpenServiceImpl.saveOrUpdate(lotteryOpen);
        }
    }

    /**
     * @Description 结算
     * @Param []
     **/
    @Scheduled(cron = "5 */5 * * * ?")
    public void settleFirst() {
        String periodsNo = lotteryCache.getUpPeriodsNo();
        log.info("开始首次结算：{}", periodsNo);
        settle();
    }


    /**
     * @Description 二次结算，防止第一次未结算完
     * @Param []
     **/
    @Scheduled(cron = "10 */5 * * * ?")
    public void settleMore() {
        String periodsNo = lotteryCache.getUpPeriodsNo();
        log.info("开始二次结算：{}", periodsNo);
        settle();
    }

    /**
     * @Description 结算
     * @Param []
     **/
    public void settle() {
        String periodsNo = lotteryCache.getUpPeriodsNo();
        // String periodsNo = "20230915208";
        List<LotteryMainPlate> lotteryMainPlateList = lotteryCache.getLotteryMainPlateList();
        Integer now = DateNewUtils.now();
        LotteryType lotteryType = lotteryCache.getLotteryType();
        for (LotteryMainPlate mainPlate : lotteryMainPlateList) {
            Integer mainCode = mainPlate.getCode();
            LotteryOpen lotteryOpen = lotteryOpenServiceImpl.getOne(new LambdaQueryWrapper<LotteryOpen>()
                    .eq(LotteryOpen::getMainCode, mainCode)
                    .eq(LotteryOpen::getPeriodsNo, periodsNo));
            if (1 == lotteryOpen.getStatus()) {
                log.error("当前期已开奖");
                return;
            }
            LotteryPlate plate = lotteryCache.getLotteryPlate(mainCode, lotteryOpen.getOpenCode());
            List<LotteryBetslips> lotteryBetslipsList = lotteryBetslipsServiceImpl.lambdaQuery()
                    .eq(LotteryBetslips::getPeriodsNo, periodsNo)
                    .eq(LotteryBetslips::getMainCode, mainCode)
                    .eq(LotteryBetslips::getStatus, 0)
                    .list();
            //中奖更新注单，钱包表，开奖结果表状态，板块表
            if (CollUtil.isNotEmpty(lotteryBetslipsList)) {
                lotteryBetslipsList.forEach(lotteryBetslips -> {
                    lotteryBetslips.setPayoutCode(lotteryOpen.getOpenCode());
                    lotteryBetslips.setStatus(1);
                    lotteryBetslips.setUpdatedAt(now);
                    if (lotteryBetslips.getBetCode().equals(lotteryOpen.getOpenCode())) {
                        BigDecimal coinPayout = lotteryBetslips.getCoinBet().multiply(lotteryType.getOdds())
                                .setScale(2, RoundingMode.DOWN);
                        lotteryBetslips.setCoinPayout(coinPayout);
                        userCoinBase.updateConcurrentUserCoin(coinPayout, lotteryBetslips.getUid());
                    }
                });
                lotteryBetslipsServiceImpl.updateBatchById(lotteryBetslipsList);
            }

            lotteryOpen.setStatus(1);
            lotteryOpen.setUpdatedAt(now);
            lotteryOpenServiceImpl.updateById(lotteryOpen);
            plate.setPayoutCount(plate.getPayoutCount() + 1);
            plate.setUpdatedAt(now);
            lotteryPlateServiceImpl.updateById(plate);
        }
    }


}
