package com.lt.win.service.base;

import com.alibaba.fastjson.JSON;
import com.lt.win.dao.generator.po.CoinDepositRecord;
import com.lt.win.dao.generator.po.CoinWithdrawalRecord;
import com.lt.win.dao.generator.service.CoinDepositRecordService;
import com.lt.win.dao.generator.service.CoinWithdrawalRecordService;
import com.lt.win.service.base.websocket.MessageBo;
import com.lt.win.service.cache.KeyConstant;
import com.lt.win.service.cache.redis.ConfigCache;
import com.lt.win.utils.JedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * <p>
 * 充值与提现通用业务
 * </p>
 *
 * @author andy
 * @since 2020/10/22
 */
@Component
public class DepositOrWithdrawalBase {
    @Resource
    private CoinWithdrawalRecordService coinWithdrawalRecordServiceImpl;
    @Resource
    private CoinDepositRecordService coinDepositRecordServiceImpl;
    @Resource
    private JedisUtil jedisUtil;
    @Resource
    private ConfigCache configCache;


    /**
     * 推送笔数的数据->提款
     *
     * @param device 消息推送:E(设备) : D-web端   B-后台
     * @return 提款笔数的数据
     */
    public MessageBo getPushWnData(String device) {
        return getPushWnDataCache(device);
    }

    /**
     * 推送笔数的数据->存款:线下
     * 备注：线上不推送
     *
     * @param device 消息推送:E(设备) : D-web端   B-后台
     * @return 推送存款笔数的数据
     */
    public MessageBo getPushDnData(String device) {
        return getPushDnDataCache(device);
    }

    /**
     * 推送笔数的数据->存款->线下:缓存
     * 备注：线上不推送
     *
     * @param device 消息推送:E(设备) : D-web端   B-后台
     * @return 推送存款笔数的数据
     */
    public MessageBo getPushDnDataCache(String device) {
        String key = KeyConstant.NETTY_PUSH_DN;
        String data = jedisUtil.get(key);
        if (StringUtils.isNotBlank(data)) {
            return JSON.parseObject(data).toJavaObject(MessageBo.class);
        }

        Integer count = coinDepositRecordServiceImpl.lambdaQuery()
                .eq(CoinDepositRecord::getStatus, 0)
                .count();
        MessageBo messageBo = MessageBo.builder()
                .E(device)
                // PUSH_DN(6, "推送存款笔数"),
                // PUSH_WN(7, "推送提款笔数"),
                // PUSH_ON(8, "推送在线人数"),
                .action("PUSH_DN")
                .N(count)
                .build();
        int time = Integer.parseInt(configCache.getRedisExpireTime());
        jedisUtil.setex(key, time, JSON.toJSONString(messageBo));
        return messageBo;
    }

    /**
     * 推送笔数的数据->提款:缓存
     *
     * @param device 消息推送:E(设备) : D-web端   B-后台
     * @return 提款笔数的数据
     */
    public MessageBo getPushWnDataCache(String device) {
        String key = KeyConstant.NETTY_PUSH_WN;
        String data = jedisUtil.get(key);
        if (StringUtils.isNotBlank(data)) {
            return JSON.parseObject(data).toJavaObject(MessageBo.class);
        }
        Integer count = coinWithdrawalRecordServiceImpl.lambdaQuery()
        .eq(CoinWithdrawalRecord::getStatus, 0).count();
        MessageBo messageBo = MessageBo.builder()
                .E(device)
                // PUSH_DN(6, "推送存款笔数"),
                // PUSH_WN(7, "推送提款笔数"),
                // PUSH_ON(8, "推送在线人数"),
                .action("PUSH_WN")
                .N(count)
                .build();
        int time = Integer.parseInt(configCache.getRedisExpireTime());
        jedisUtil.setex(key, time, JSON.toJSONString(messageBo));
        return messageBo;
    }
}
