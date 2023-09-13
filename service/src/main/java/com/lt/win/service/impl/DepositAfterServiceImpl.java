package com.lt.win.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.lt.win.dao.generator.po.*;
import com.lt.win.dao.generator.service.CoinDepositRecordService;
import com.lt.win.dao.generator.service.CoinRewardsService;
import com.lt.win.dao.generator.service.UserLevelService;
import com.lt.win.dao.generator.service.UserService;
import com.lt.win.service.base.ActivityServiceBase;
import com.lt.win.service.base.NoticeBase;
import com.lt.win.service.cache.redis.CoinRewardCache;
import com.lt.win.service.cache.redis.PromotionsCache;
import com.lt.win.service.io.dto.DepositFlagAnaCount;
import com.lt.win.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.lt.win.service.common.Constant.*;

/**
 * @Auther: wells
 * @Date: 2022/9/26 15:18
 * @Description:
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DepositAfterServiceImpl {
    private final PromotionsCache promotionsCache;
    private final CoinRewardCache coinRewardCache;
    private final CoinRewardsService coinRewardsServiceImpl;
    private final CoinDepositRecordService coinDepositRecordServiceImpl;
    private final UserService userServiceImpl;
    private final UserLevelService userLevelServiceImpl;
    private final NoticeBase noticeBase;
    private final ActivityServiceBase activityServiceBase;

    /**
     * @Description 活动首充与续充触发
     * @Param [uid, coin]
     **/
    public void depositActivity(Integer uid, BigDecimal coin, DepositFlagAnaCount depositFlagAnaCount) {
        CoinRewards coinReward = coinRewardCache.getCoinReward(uid);
        if (Objects.isNull(coinReward)) {
            return;
        }
        //首充，续充，快乐周末判断最小最大金额
        boolean isDepositActivity = coinReward.getReferId() == 1 || coinReward.getReferId() == 2 || coinReward.getReferId() == 4;
        if (!isDepositActivity) {
            return;
        }
        //快乐周末
        if (coinReward.getReferId() == 4) {
            happyWeek(coinReward, uid, coin);
            return;
        }
        //首充与续充
        depositActivityProcess(coinReward, coin, depositFlagAnaCount);

    }


    /**
     * @Description 快乐周末
     * @Param [coinReward, uid, coin]
     **/
    public void happyWeek(CoinRewards coinReward, Integer uid, BigDecimal coin) {
        Promotions promotionsCache = this.promotionsCache.getPromotionsCache(4);
        JSONObject jsonObject = JSONObject.parseObject(promotionsCache.getInfo());
        Integer weekDays = jsonObject.getInteger("weekdays");
        var time = ZonedDateTime.now().withHour(0).withMinute(0).withSecond(0);
        var todayWeekDays = time.getDayOfWeek().getValue();
        //配置的星期几与今天的日期是否一样
        if (weekDays != todayWeekDays) {
            return;
        }
        //开始时间与结束时间
        int startTime = (int) ZonedDateTime.now().withHour(0).withMinute(0).withSecond(0).toInstant().getEpochSecond();
        int endTime = (int) ZonedDateTime.now().withHour(23).withMinute(59).withSecond(59).toInstant().getEpochSecond();
        //判断今天的充值笔数
        Integer count = coinDepositRecordServiceImpl.lambdaQuery()
                .eq(CoinDepositRecord::getUid, uid)
                .ge(CoinDepositRecord::getCreatedAt, startTime)
                .le(CoinDepositRecord::getCreatedAt, endTime)
                .eq(CoinDepositRecord::getStatus, 1).count();
        if (SATISFIED.equals(coinReward.getStatus()) && count > 1) {
            activityServiceBase.lastActivityFinish(coinReward);
            return;
        }

        if (!APPLYING.equals(coinReward.getStatus())) {
            return;
        }
        BigDecimal minCoin = jsonObject.getBigDecimal("minCoin");
        BigDecimal maxCoin = jsonObject.getBigDecimal("maxCoin");
        //充值金额大于最小充值额
        if (coin.compareTo(minCoin) < 0) {
            return;
        }
        //充值金额大于最大充值时，则充值金额为最大值
        coin = coin.compareTo(maxCoin) > 0 ? maxCoin : coin;
        if (count == 1) {
            int validTime = endTime - DateUtils.getCurrentTime();
            updateCoinReward(jsonObject, coinReward, coin, validTime);
        }
    }

    /**
     * @Description 活动首充与续充
     * @Param [activityFlag]
     **/

    public void depositActivityProcess(CoinRewards coinReward, BigDecimal coin, DepositFlagAnaCount depositFlagAnaCount) {
        int validTime = 2 * 7 * 24 * 3600;
        //活动已满足，但是充值阶段无法完成打码量，则取消活动
        if (SATISFIED.equals(coinReward.getStatus())) {
            if (coinReward.getReferId() == 1 && depositFlagAnaCount.getCount() > 0) {
                activityServiceBase.lastActivityFinish(coinReward);
                return;
            }
            if (coinReward.getReferId() == 2 && depositFlagAnaCount.getCount() > 1) {
                activityServiceBase.lastActivityFinish(coinReward);
                return;
            }
        }
        //活动对应类型判断
        if (coinReward.getReferId() == 1 && depositFlagAnaCount.getDepositFlag() != 1) {
            return;
        }

        if (coinReward.getReferId() == 2 && depositFlagAnaCount.getDepositFlag() != 2) {
            return;
        }

        if (!APPLYING.equals(coinReward.getStatus())) {
            return;
        }
        Promotions promotionsCache = this.promotionsCache.getPromotionsCache(coinReward.getReferId());
        JSONObject jsonObject = JSONObject.parseObject(promotionsCache.getInfo());
        BigDecimal minCoin = jsonObject.getBigDecimal("minCoin");
        BigDecimal maxCoin = jsonObject.getBigDecimal("maxCoin");
        //充值金额大于最小充值额
        if (coin.compareTo(minCoin) < 0) {
            return;
        }
        //充值金额大于最大充值时，则充值金额为最大值
        coin = coin.compareTo(maxCoin) > 0 ? maxCoin : coin;
        updateCoinReward(jsonObject, coinReward, coin, validTime);
    }

    /**
     * @Description 修改奖金表状态
     * @Param [jsonObject, coinReward, coin]
     **/
    public void updateCoinReward(JSONObject jsonObject, CoinRewards coinReward, BigDecimal coin, Integer validTime) {
        Integer time = DateUtils.getCurrentTime();
        Integer flowClaim = jsonObject.getInteger("flowClaim");
        BigDecimal rate = jsonObject.getBigDecimal("rate");
        coin = coin.multiply(rate).setScale(2, RoundingMode.DOWN);
        coinReward.setCoin(coin);
        coinReward.setCodes(coin.multiply(BigDecimal.valueOf(flowClaim)));
        coinReward.setUpdatedAt(time);
        coinReward.setStatus(1);
        coinRewardsServiceImpl.updateById(coinReward);
        //活动有效期两个星期
        coinRewardCache.setCoinReward(coinReward, validTime);
    }

    /**
     * @return int
     * @Description 获取用户的充值标识
     * @Param [uid]
     **/
    public DepositFlagAnaCount getDepStatus(Integer uid) {
        DepositFlagAnaCount depositFlagAnaCount = new DepositFlagAnaCount();
        Integer count = coinDepositRecordServiceImpl.lambdaQuery()
                .eq(CoinDepositRecord::getUid, uid)
                .eq(CoinDepositRecord::getStatus, 1).count();
        Promotions promotionsCache = this.promotionsCache.getPromotionsCache(2);
        JSONObject jsonObject = JSONObject.parseObject(promotionsCache.getInfo());
        Integer depositNums = jsonObject.getInteger("depositNums");
        depositFlagAnaCount.setCount(count);
        int depositFlag = 9;
        if (count == 0) {
            depositFlag = 1;
        } else if (count.equals(depositNums - 1)) {
            depositFlag = 2;
        }
        depositFlagAnaCount.setDepositFlag(depositFlag);
        return depositFlagAnaCount;
    }

    /**
     * @Description 检查用户vip升级
     * @Param [uid, realAmount, currency]
     **/
    @Async
    public void checkUserVip(Integer uid, BigDecimal realAmount) {
        User user = userServiceImpl.getById(uid);
        List<UserLevel> userLevelList = userLevelServiceImpl.lambdaQuery()
                .orderByAsc(UserLevel::getId).list();
        Optional<UserLevel> userLevelOption = userLevelList.stream()
                .filter(level -> level.getId().equals(user.getLevelId()))
                .findFirst();
        if (userLevelOption.isEmpty()) {
            return;
        }
        UserLevel userLevel = userLevelOption.get();
        int score = user.getScore() + (realAmount.multiply(userLevel.getScoreUpgradeRate()).intValue());
        int maxScore = userLevelList.get(userLevelList.size() - 1).getScoreUpgradeMax();
        //分数已达最高则无需升级
        if (user.getScore() >= maxScore) {
            return;
        }
        Integer levelId = user.getLevelId();
        for (int i = 0; i < userLevelList.size(); i++) {
            UserLevel level = userLevelList.get(i);
            if (score >= level.getScoreUpgradeMin() && score <= level.getScoreUpgradeMax()) {
                levelId = level.getId();
            }
            if (i == userLevelList.size() - 1 && score >= level.getScoreUpgradeMax()) {
                levelId = level.getId();
            }
        }
        score = Math.min(score, maxScore);
        user.setLevelId(levelId);
        user.setScore(score);
        user.setUpdatedAt(DateUtils.getCurrentTime());
        userServiceImpl.updateById(user);
        noticeBase.depositCoinChange(uid, new BigDecimal(score));
    }
}
