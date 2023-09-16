package com.lt.win.apiend.task;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lt.win.dao.generator.po.LotteryBetslips;
import com.lt.win.dao.generator.po.LotteryOpen;
import com.lt.win.dao.generator.po.LotteryPlate;
import com.lt.win.dao.generator.po.LotteryType;
import com.lt.win.dao.generator.service.LotteryBetslipsService;
import com.lt.win.dao.generator.service.LotteryOpenService;
import com.lt.win.dao.generator.service.LotteryPlateService;
import com.lt.win.dao.generator.service.LotteryTypeService;
import com.lt.win.service.base.UserCoinBase;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
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
    private final LotteryTypeService lotteryTypeServiceImpl;
    private final UserCoinBase userCoinBase;

    /**
     * 每期间隔时间(分钟)
     **/
    private static final int INTERVAL = 5;
    /**
     * 每期间隔时间(秒)
     **/
    private static final int INTERVAL_SECOND = 300;

    private static final String PK10 = "pk10";

    /**
     * @Description 生成随机的开奖号码
     * @Param []
     **/
    @Scheduled(cron = "0 0 0 * * ?")
    public void initLotteryOpen() {
        int totalMin = 24 * 60;
        int num = totalMin / INTERVAL;
        List<Integer> list = IntStream.range(1, 11)
                .boxed()
                .collect(Collectors.toList());
        Map<Integer, String> plateMap = lotteryPlateServiceImpl.list().stream()
                .collect(Collectors.toMap(LotteryPlate::getCode, LotteryPlate::getName));
        Integer now = DateUtils.getCurrentTime();
        List<LotteryOpen> openList = new ArrayList<>();
        for (int i = 1; i <= num; i++) {
            Collections.shuffle(list);
            //生成期号
            String periodsNo = DateUtils.yyyyMMdd2(DateUtils.getCurrentTime()) + String.format("%03d", i);
            Integer openCode = list.get(0);
            LotteryOpen lotteryOpen = new LotteryOpen();
            lotteryOpen.setPeriodsNo(periodsNo);
            lotteryOpen.setLotteryCode(PK10);
            lotteryOpen.setLotteryName(PK10);
            lotteryOpen.setOpenCode(openCode);
            lotteryOpen.setOpenName(plateMap.get(openCode));
            lotteryOpen.setOpenAllCode(StringUtils.join(list, ","));
            lotteryOpen.setCreatedAt(now);
            lotteryOpen.setUpdatedAt(now);
            openList.add(lotteryOpen);
        }
        lotteryOpenServiceImpl.saveBatch(openList);
    }

    /**
     * @Description 结算
     * @Param []
     **/
    @Scheduled(cron = "0 */5 * * * ?")
    public void settleFirst() {
        settle();
    }


    /**
     * @Description 二次结算，防止第一次未结算完
     * @Param []
     **/
    //@Scheduled(cron = "10 */5 * * * ?")
    public void settleMore() {
        settle();
    }

    /**
     * @Description 结算
     * @Param []
     **/
    public void settle() {
        String periodsNo = getUpPeriodsNo();
        // String periodsNo = "20230915208";
        LotteryOpen lotteryOpen = lotteryOpenServiceImpl.getOne(new LambdaQueryWrapper<LotteryOpen>().eq(LotteryOpen::getPeriodsNo, periodsNo));
        if (1 == lotteryOpen.getStatus()) {
            log.error("当前期已开奖");
            return;
        }
        LotteryType lotteryType = lotteryTypeServiceImpl.getOne(new LambdaQueryWrapper<LotteryType>()
                .eq(LotteryType::getCode, PK10));
        LotteryPlate plate = lotteryPlateServiceImpl.getById(lotteryOpen.getOpenCode());
        List<LotteryBetslips> lotteryBetslipsList = lotteryBetslipsServiceImpl.lambdaQuery()
                .eq(LotteryBetslips::getPeriodsNo, periodsNo)
                .eq(LotteryBetslips::getStatus, 0)
                .list();
        Integer now = DateNewUtils.now();
        //中奖更新注单，钱包表，开奖结果表状态，板块表
        lotteryBetslipsList.forEach(lotteryBetslips -> {
            lotteryBetslips.setPayoutCode(lotteryOpen.getOpenCode());
            lotteryBetslips.setPayoutName(lotteryOpen.getOpenName());
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
        lotteryOpen.setStatus(1);
        lotteryOpen.setUpdatedAt(now);
        lotteryOpenServiceImpl.updateById(lotteryOpen);
        plate.setPayoutCount(plate.getPayoutCount() + 1);
        plate.setUpdatedAt(now);
        lotteryPlateServiceImpl.updateById(plate);
    }

    /**
     * @return java.lang.String
     * @Description 获取当前期号
     * @Param []
     **/
    private String getUpPeriodsNo() {
        int upTime = DateNewUtils.now() - INTERVAL_SECOND;
        int toDaySecond = DateNewUtils.now() - INTERVAL_SECOND - DateNewUtils.todayStart();
        toDaySecond = toDaySecond < 0 ? toDaySecond + 24 * 3600 : toDaySecond;
        int num = toDaySecond / INTERVAL_SECOND;
        num = toDaySecond % INTERVAL_SECOND == 0 ? num : num + 1;
        return DateUtils.yyyyMMdd2(upTime) + String.format("%03d", num);
    }

}
