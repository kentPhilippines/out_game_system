package com.lt.win.service.base;

import com.alibaba.fastjson.JSONObject;
import com.lt.win.dao.generator.po.CoinRewards;
import com.lt.win.dao.generator.po.Promotions;
import com.lt.win.dao.generator.service.CoinRewardsService;
import com.lt.win.service.cache.redis.CoinRewardCache;
import com.lt.win.service.cache.redis.ConfigCache;
import com.lt.win.service.cache.redis.PlatListCache;
import com.lt.win.service.cache.redis.PromotionsCache;
import com.lt.win.service.io.dto.Betslips;
import com.lt.win.service.io.dto.CoinLog;
import com.lt.win.service.io.dto.UserCoinChangeParams;
import com.lt.win.utils.DateUtils;
import com.lt.win.utils.JedisUtil;
import com.lt.win.utils.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.common.Strings;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
//import org.zxp.esclientrhl.repository.ElasticsearchTemplate;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

import static com.lt.win.service.cache.KeyConstant.ACTIVITY_BET_SUM_HASH;
import static com.lt.win.service.common.Constant.APPLYING;
import static com.lt.win.service.common.Constant.SATISFIED;

/**
 * 活动打码量计算
 *
 * @Auther: nobody
 * @Date: 2022/8/20 09:12
 * @Description:
 */


@Component
@Slf4j
public class ActivityServiceBase {
//    @Resource
//    ElasticsearchTemplate<Betslips, String> betslipsElasticsearchTemplate;
    @Resource
    private JedisUtil jedisUtil;
    @Resource
    private CoinRewardCache coinRewardCache;
    @Resource
    private CoinRewardsService coinRewardsServiceImpl;
    @Resource
    private ConfigCache configCache;
    @Resource
    private PromotionsCache promotionsCache;
    @Resource
    private PlatListCache platListCache;


    /**
     * @Description 首单包赔处理
     * @Param [coinLog]
     **/
    public void RiskFreeBet(CoinLog coinLog) {
        try {
            Integer uid = coinLog.getUid();
            BigDecimal coin = coinLog.getCoin();
            CoinRewards coinReward = coinRewardCache.getCoinReward(uid);
            //状态为已满足活动条件的状态
            if (Objects.isNull(coinReward)) {
                return;
            }
            if (!SATISFIED.equals(coinReward.getStatus())) {
                return;
            }
            //首单包赔判断
            if (coinReward.getReferId() != 3) {
                return;
            }
            if (APPLYING.equals(coinReward.getStatus())) {
//                Betslips betslips = betslipsElasticsearchTemplate.getById(coinLog.getReferId().toString(), Betslips.class);
//                if (Objects.isNull(betslips) || betslips.getGameId() != 1) {
//                    return;
//                }
//                //首单如果赢了就结束此活动
//                if (betslips.getStake().compareTo(coinLog.getCoin()) < 0) {
//                    lastAddActivityCoin(coinLog);
//                    return;
//                }
                Promotions promotionsCache = this.promotionsCache.getPromotionsCache(3);
                JSONObject jsonObject = JSONObject.parseObject(promotionsCache.getInfo());
                Integer time = DateUtils.getCurrentTime();
                Integer flowClaim = jsonObject.getInteger("flowClaim");
                BigDecimal maxCoin = jsonObject.getBigDecimal("maxCoin");
                BigDecimal rate = jsonObject.getBigDecimal("rate");
                coin = coin.multiply(rate).setScale(2, RoundingMode.DOWN);
                coin = coin.compareTo(maxCoin) > 0 ? maxCoin : coin;
                coinReward.setCoin(coin);
                coinReward.setCodes(coin.multiply(BigDecimal.valueOf(flowClaim)));
                coinReward.setUpdatedAt(time);
                coinReward.setStatus(1);
                coinRewardsServiceImpl.updateById(coinReward);
                //活动有效期两个星期
                coinRewardCache.setCoinReward(coinReward, 2 * 7 * 24 * 3600);
                addCoin(uid, coin, coinReward);
            }
        } catch (
                Exception e) {
            log.error("首单包赔活动累计流水异常", e);
        }

    }

    /**
     * @Description 投注时累计活动期间累计投注金额
     * @Param [uid, coin]
     **/
    public void addActivityBetSum(CoinLog coinLog, BigDecimal coin) {
        try {
            Integer uid = coinLog.getUid();
            CoinRewards coinReward = coinRewardCache.getCoinReward(uid);
            //状态为已满足活动条件的状态
            if (Objects.isNull(coinReward)) {
                return;
            }
            if (!SATISFIED.equals(coinReward.getStatus())) {
                return;
            }
            addCoin(uid, coin, coinReward);
        } catch (Exception e) {
            log.error("活动累计流水异常", e);
        }
    }


    /**
     * @Description 退款时累计活动期间减去退款金额
     * @Param [uid, coin]
     **/
    public void subActivityBetSum(CoinLog coinLog) {
        Integer uid = coinLog.getUid();
        BigDecimal coin = coinLog.getCoin();
        CoinRewards coinReward = coinRewardCache.getCoinReward(uid);
        //状态为已满足活动条件的状态
        if (Objects.isNull(coinReward) || !SATISFIED.equals(coinReward.getStatus())) {
            return;
        }
        String data = jedisUtil.hget(ACTIVITY_BET_SUM_HASH, uid.toString());
        BigDecimal betSum = Strings.isEmpty(data) ? BigDecimal.ZERO : new BigDecimal(data);
        betSum = betSum.subtract(coin);
        jedisUtil.hset(ACTIVITY_BET_SUM_HASH, uid.toString(), betSum.toString());
    }

    public void lastAddActivityCoin(Integer uid, Integer category, BigDecimal coin) {
        CoinRewards coinReward = coinRewardCache.getCoinReward(uid);
        //状态为已满足活动条件的状态
        if (Objects.isNull(coinReward)) {
            return;
        }
        if (!SATISFIED.equals(coinReward.getStatus())) {
            return;
        }
        if ((coinReward.getReferId() == 1 || coinReward.getReferId() == 2 || coinReward.getReferId() == 4) && !SATISFIED.equals(coinReward.getStatus())) {
            return;
        }
        if (UserCoinChangeParams.FlowCategoryTypeEnum.BET.getCode() == category) {
            String data = jedisUtil.hget(ACTIVITY_BET_SUM_HASH, uid.toString());
            BigDecimal betSum = Strings.isEmpty(data) ? BigDecimal.ZERO : new BigDecimal(data);
            betSum = betSum.add(coin);
            BigDecimal codes = coinReward.getCodes();
            if (codes.compareTo(betSum) > 0) {
                lastActivityFinish(coinReward);
            } else {
                //满足打码量则派发彩金
                activityFinish(coinReward);
                jedisUtil.hdel(ACTIVITY_BET_SUM_HASH, uid.toString());
            }
        } else {
            lastActivityFinish(coinReward);
        }
    }

    /**
     * @Description 活动金额少于1的
     * @Param [coinLog]
     **/
    public void lastAddActivityCoin(CoinLog coinLog, BigDecimal coin) {
        lastAddActivityCoin(coinLog.getUid(), coinLog.getCategory(), coin);
    }

    public void lastAddActivityCoin(CoinLog coinLog) {
        lastAddActivityCoin(coinLog.getUid(), coinLog.getCategory(), coinLog.getCoin());
    }
    /**
     * @Description 活动结束
     * @Param [coinReward, uid]
     **/
    public void lastActivityFinish(CoinRewards coinReward) {
        Integer uid = coinReward.getUid();
        //修改奖金表记录
        coinRewardsServiceImpl.lambdaUpdate()
                .set(CoinRewards::getStatus, 3)
                .eq(CoinRewards::getUid, uid)
                .eq(CoinRewards::getStatus, 1)
                .update();
        jedisUtil.hdel(ACTIVITY_BET_SUM_HASH, uid.toString());
        coinRewardCache.delCoinReward(coinReward);
    }

    /**
     * @Description 累计流水
     * @Param [uid, coin, coinReward]
     **/
    private void addCoin(Integer uid, BigDecimal coin, CoinRewards coinReward) {
        String data = jedisUtil.hget(ACTIVITY_BET_SUM_HASH, uid.toString());
        BigDecimal betSum = Strings.isEmpty(data) ? BigDecimal.ZERO : new BigDecimal(data);
        betSum = betSum.add(coin);
        BigDecimal codes = coinReward.getCodes();
        if (codes.compareTo(betSum) > 0) {
            jedisUtil.hset(ACTIVITY_BET_SUM_HASH, uid.toString(), betSum.toString());
        } else {
            //满足打码量则派发彩金
            activityFinish(coinReward);
            jedisUtil.hdel(ACTIVITY_BET_SUM_HASH, uid.toString());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void activityFinish(CoinRewards coinRewards) {
        coinRewards.setStatus(2);
        coinRewards.setUpdatedAt(DateUtils.getCurrentTime());
        coinRewardsServiceImpl.updateById(coinRewards);
        coinRewardCache.delCoinReward(coinRewards);
        //修改用户金额
        UserCoinBase userCoinBase = (UserCoinBase) SpringUtils.getBean("userCoinBase");
        userCoinBase.userCoinChange(UserCoinChangeParams.UserCoinChangeReq.builder()
                .uid(coinRewards.getUid())
                .coin(coinRewards.getCoin())
                .referId(coinRewards.getId())
                .category(UserCoinChangeParams.FlowCategoryTypeEnum.AWARD.getCode())
                .outIn(1)
                .build());
    }

}
