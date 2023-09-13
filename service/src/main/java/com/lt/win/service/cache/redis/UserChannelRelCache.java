package com.lt.win.service.cache.redis;

import com.lt.win.service.cache.KeyConstant;
import com.lt.win.utils.JedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: David
 * @date: 17/09/2020
 */
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Component
public class UserChannelRelCache {
    private final JedisUtil jedisUtil;

    /**
     * 设置UID、Chanel映射关系
     *
     * @param uid       uid
     * @param channelId channelId
     */
    public void setUidChannelRel(@NotNull Integer uid, String channelId) {
        // 同步添加 uid->channel channel->uid
        jedisUtil.hset(KeyConstant.REL_UID_CHANNEL, uid.toString(), channelId);
        jedisUtil.hset(KeyConstant.REL_UID_CHANNEL, channelId, uid.toString());
    }

    /**
     * 根据uid 获取 ChannelId
     *
     * @param uid uid
     * @return channelID
     */
    public String getChannelIdById(@NotNull Integer uid) {
        return jedisUtil.hget(KeyConstant.REL_UID_CHANNEL, uid.toString());
    }

    /**
     * 根据ChannelId 获取 Uid
     *
     * @param channelId channel id
     * @return uid
     */
    public Integer getUidByChannelId(String channelId) {
        String s = jedisUtil.hget(KeyConstant.REL_UID_CHANNEL, channelId);
        if (StringUtils.isNotBlank(s)) {
            return Integer.parseInt(s);
        }

        return null;
    }

    /**
     * 删除 ChannelId uid 关系映射
     *
     * @param channelId channelId
     */
    public void delUidChannelRel(String channelId) {
        String uid = jedisUtil.hget(KeyConstant.REL_UID_CHANNEL, channelId);
        String redisChannelId = jedisUtil.hget(KeyConstant.REL_UID_CHANNEL, uid);

        jedisUtil.hdel(KeyConstant.REL_UID_CHANNEL, channelId);
        if (channelId.equals(redisChannelId)) {
            jedisUtil.hdel(KeyConstant.REL_UID_CHANNEL, uid);
        }

    }

    /**
     * 获取WebSocket在线人数列表
     *
     * @return Uid列表
     */
    public List<Integer> getChannelUids() {
        return jedisUtil.hkeys(KeyConstant.REL_UID_CHANNEL)
                .parallelStream()
                .filter(x -> x.length() < 10)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    /**
     * 获取WebSocket在线人数
     *
     * @return 在线人数
     */
    public Long getChannelNums() {
        return jedisUtil.llen(KeyConstant.REL_UID_CHANNEL);
    }


    /**
     * 获取下级在线人数
     *
     * @param uidCollect
     * @return
     */
    public List<Integer> getSubordinateListChannelUids(List<Integer> uidCollect) {

        List<Integer> collect = jedisUtil.hkeys(KeyConstant.REL_UID_CHANNEL)
                .parallelStream()
                .filter(x -> x.length() < 10 && uidCollect.contains(Integer.parseInt(x)))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        return collect;
    }


}
