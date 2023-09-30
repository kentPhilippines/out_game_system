package com.lt.win.service.base;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lt.win.dao.generator.po.*;
import com.lt.win.dao.generator.service.LotteryBetslipsService;
import com.lt.win.dao.generator.service.LotteryOpenService;
import com.lt.win.service.cache.redis.LotteryCache;
import com.lt.win.service.exception.BusinessException;
import com.lt.win.utils.DateNewUtils;
import com.lt.win.utils.DateUtils;
import com.lt.win.utils.components.response.CodeInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @Auther: Jess
 * @Date: 2023/9/20 14:00
 * @Description:
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LotteryCommBase {
    private final LotteryOpenService lotteryOpenServiceImpl;
    private final LotteryBetslipsService lotteryBetslipsServiceImpl;
    private final UserCoinBase userCoinBase;
    private final LotteryCache lotteryCache;


    private static final String PK10 = "pk10";

    /**
     * @Description 结算
     * @Param []
     **/
    @Transactional(rollbackFor = Exception.class)
    public void settle() {
        String periodsNo = lotteryCache.getUpPeriodsNo();
        List<LotteryMainPlate> lotteryMainPlateList = lotteryCache.getLotteryMainPlateList();
        for (LotteryMainPlate mainPlate : lotteryMainPlateList) {
            LotteryOpen lotteryOpen = lotteryOpenServiceImpl.getOne(new LambdaQueryWrapper<LotteryOpen>()
                    .eq(LotteryOpen::getMainCode, mainPlate.getCode())
                    .eq(LotteryOpen::getPeriodsNo, periodsNo));
            if (1 == lotteryOpen.getStatus()) {
                log.error("当前期已开奖");
                continue;
            }
            settle(periodsNo, mainPlate.getCode(), null);
        }
    }

    /**
     * @Description 单注开奖
     * @Param [betId]
     **/
    @Transactional(rollbackFor = Exception.class)
    public boolean settle(Long betId) {
        LotteryBetslips lotteryBetslips = lotteryBetslipsServiceImpl.getById(betId);
        if (0 != lotteryBetslips.getStatus()) {
            throw new BusinessException(CodeInfo.LOTTERY_BET_SETTLE_EXCEPTION);
        }
        return settle(lotteryBetslips.getPeriodsNo(), lotteryBetslips.getMainCode(), betId);
    }

    /**
     * @Description 单注开奖
     * @Param [betId]
     **/
    @Transactional(rollbackFor = Exception.class)
    public boolean settle(String periodsNo, Integer mainCode) {
        return settle(periodsNo, mainCode, null);
    }

    /**
     * @Description 每个主板每期开奖
     * @Param [periodsNo, mainCode]
     **/
    public boolean settle(String periodsNo, Integer mainCode, Long betId) {
        Integer now = DateNewUtils.now();
        LotteryType lotteryType = lotteryCache.getLotteryType();
        LotteryOpen lotteryOpen = lotteryOpenServiceImpl.getOne(new LambdaQueryWrapper<LotteryOpen>()
                .eq(LotteryOpen::getMainCode, mainCode)
                .eq(LotteryOpen::getPeriodsNo, periodsNo));
        List<LotteryBetslips> lotteryBetslipsList = lotteryBetslipsServiceImpl.lambdaQuery()
                .eq(LotteryBetslips::getPeriodsNo, periodsNo)
                .eq(LotteryBetslips::getMainCode, mainCode)
                .eq(Objects.nonNull(betId), LotteryBetslips::getId, betId)
                .eq(LotteryBetslips::getStatus, 0)
                .list();
        //中奖更新注单，钱包表，开奖结果表状态，板块表
        if (CollUtil.isNotEmpty(lotteryBetslipsList)) {
            lotteryBetslipsList.forEach(lotteryBetslips -> {
                lotteryBetslips.setPayoutCode(lotteryOpen.getOpenCode());
                lotteryBetslips.setStatus(1);
                lotteryBetslips.setUpdatedAt(now);
                List<Integer> openCodeAList = Stream.of(lotteryOpen.getOpenCode().split(","))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());
                if (openCodeAList.contains(lotteryBetslips.getBetCode())) {
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
        return true;
    }

    /**
     * @Description 生产开奖号码
     * @Param []
     **/
    public void initLotteryOpen() {
        List<Integer> list = IntStream.range(1, 11)
                .boxed()
                .collect(Collectors.toList());
        Integer now = DateUtils.getCurrentTime();
        List<LotteryMainPlate> lotteryMainPlateList = lotteryCache.getLotteryMainPlateList();
        //生成期号
        String periodsNo = lotteryCache.getCurrPeriodsNo();
        for (LotteryMainPlate mainPlate : lotteryMainPlateList) {
            List<LotteryBetslips> lotteryBetslipsList = lotteryBetslipsServiceImpl.lambdaQuery()
                    .eq(LotteryBetslips::getMainCode, mainPlate.getCode())
                    .eq(LotteryBetslips::getPeriodsNo, periodsNo)
                    .list();
            if (CollUtil.isNotEmpty(lotteryBetslipsList)) {
                Map<Integer, List<LotteryBetslips>> map = lotteryBetslipsList.stream().collect(Collectors.groupingBy(LotteryBetslips::getBetCode));
                Map<Integer, BigDecimal> treeMap = new TreeMap<>();
                map.forEach((betCode, betslips) -> {
                    BigDecimal betCoin = betslips.stream().map(LotteryBetslips::getCoinBet).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
                    treeMap.put(betCode, betCoin);
                });
                List<Map.Entry<Integer, BigDecimal>> betList = new ArrayList<>(treeMap.entrySet());
                //然后通过比较器来实现排序
                betList.sort(Map.Entry.comparingByValue());
                List<Integer> betCodeList = betList.stream().map(Map.Entry::getKey).collect(Collectors.toList());
                List<Integer> noBetList = list.stream().filter(code -> !betCodeList.contains(code)).collect(Collectors.toList());
                Collections.shuffle(noBetList);
                noBetList.addAll(betCodeList);
                list = noBetList;
            } else {
                Collections.shuffle(list);
            }

            LotteryOpen lotteryOpen = lotteryOpenServiceImpl.getOne(new LambdaQueryWrapper<LotteryOpen>()
                    .eq(LotteryOpen::getMainCode, mainPlate.getCode())
                    .eq(LotteryOpen::getPeriodsNo, periodsNo));
            if (Objects.nonNull(lotteryOpen)) {
                continue;
            }
            lotteryOpen = new LotteryOpen();
            lotteryOpen.setPeriodsNo(periodsNo);
            lotteryOpen.setLotteryCode(PK10);
            lotteryOpen.setLotteryName(PK10);
            lotteryOpen.setCreatedAt(now);
            lotteryOpen.setMainCode(mainPlate.getCode());
            lotteryOpen.setOpenCode(StringUtils.join(list.subList(0, 5), ","));
            lotteryOpen.setOpenAllCode(StringUtils.join(list, ","));
            lotteryOpen.setUpdatedAt(now);
            lotteryOpenServiceImpl.saveOrUpdate(lotteryOpen);
        }
    }
}
