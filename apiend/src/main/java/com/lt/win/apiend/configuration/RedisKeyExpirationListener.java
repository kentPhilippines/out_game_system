package com.lt.win.apiend.configuration;

import com.lt.win.dao.generator.po.CoinDepositRecord;
import com.lt.win.dao.generator.po.CoinRewards;
import com.lt.win.dao.generator.service.CoinDepositRecordService;
import com.lt.win.dao.generator.service.CoinRewardsService;
import com.lt.win.service.cache.redis.CoinRewardCache;
import com.lt.win.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import static com.lt.win.service.cache.KeyConstant.COIN_REWARD_HASH;

/**
 * @Auther: wells
 * @Date: 2022/10/8 18:32
 * @Description:
 */
@Slf4j
@Component
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {
    @Autowired
    private CoinRewardsService coinRewardsServiceImpl;
    @Autowired
    private CoinRewardCache coinRewardCache;

    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String expireKey = message.toString();
        if (expireKey.contains(COIN_REWARD_HASH)) {
            String uid = expireKey.split(":")[1];
            log.info("expireKey=" + expireKey);
            CoinRewards coinReward = coinRewardCache.getCoinReward(Integer.valueOf(uid));
            //修改奖金表记录
            coinRewardsServiceImpl.lambdaUpdate()
                    .set(CoinRewards::getStatus, 3)
                    .eq(CoinRewards::getUid, uid)
                    .eq(CoinRewards::getStatus,1)
                    .update();
        }
    }
}