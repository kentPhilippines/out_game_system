package com.lt.win.service.cache.redis;

import com.alibaba.fastjson.JSON;
import com.lt.win.dao.generator.po.CoinRewards;
import com.lt.win.dao.generator.service.CoinRewardsService;
import com.lt.win.service.cache.KeyConstant;
import com.lt.win.utils.JedisUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.util.Strings;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author: wells
 * @date: 2020/8/14
 * @description:
 */
@Component
public class CoinRewardCache {
    @Autowired
    private CoinRewardsService coinRewardsServiceImpl;

    @Autowired
    JedisUtil jedisUtil;

    /**
     * 获取活动领取记录列表
     * (申请,触发状态活动)
     */
    public CoinRewards getCoinReward(Integer uid) {
        String data = jedisUtil.get(KeyConstant.COIN_REWARD_HASH + ":" + uid);
        if (Strings.isNotEmpty(data)) {
            return JSON.parseObject(data, CoinRewards.class);
        }
        CoinRewards coinRewards = null;
        List<CoinRewards> coinRewardsList = coinRewardsServiceImpl.lambdaQuery()
                .eq(CoinRewards::getUid, uid)
                .in(CoinRewards::getStatus, 0, 1)  //已经申请，触发的活动
                .list();
        if (CollectionUtils.isNotEmpty(coinRewardsList)) {
            coinRewards = coinRewardsList.get(0);
            jedisUtil.set(KeyConstant.COIN_REWARD_HASH + ":" + uid, JSON.toJSONString(coinRewards));
        }
        return coinRewards;
    }

    /**
     * 修改活动领取记录列表
     * (申请,触发状态活动)
     */
    public void setCoinReward(@NotNull CoinRewards rewards) {
        jedisUtil.set(KeyConstant.COIN_REWARD_HASH + ":" + rewards.getUid(), JSON.toJSONString(rewards));
    }

    public void setCoinReward(@NotNull CoinRewards rewards, Integer time) {
        jedisUtil.setex(KeyConstant.COIN_REWARD_HASH + ":" + rewards.getUid(), time, JSON.toJSONString(rewards));
    }

    /**
     * 删除活动领取记录列表
     * (申请,触发状态活动)
     */
    public void delCoinReward(@NotNull CoinRewards rewards) {
        jedisUtil.del(KeyConstant.COIN_REWARD_HASH + ":" + rewards.getUid());
    }

}
