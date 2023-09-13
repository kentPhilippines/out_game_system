package com.lt.win.service.cache.redis;

import com.alibaba.fastjson.JSON;
import com.lt.win.dao.generator.po.GameSlot;
import com.lt.win.dao.generator.service.GameSlotService;
import com.lt.win.service.cache.KeyConstant;
import com.lt.win.service.exception.BusinessException;
import com.lt.win.utils.JedisUtil;
import com.lt.win.utils.TextUtils;
import com.lt.win.utils.components.response.CodeInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;


/**
 * <p>
 * redis缓存类:缓存Admin
 * </p>
 *
 * @author David
 * @since 2020/6/23
 */
@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GameSlotCache {
    private final JedisUtil jedisUtil;
    private final GameSlotService gameSlotServiceImpl;

    /**
     * 获取老虎机游戏记录信息
     *
     * @param id     slot_game id
     * @param platId plat_id
     * @return res
     */
    public GameSlot one(String id, Integer platId) {
        var subKey = TextUtils.buildSubKey(id, platId.toString());
        var data = jedisUtil.hget(KeyConstant.GAME_SLOT_HASH, subKey);
        if (Optional.ofNullable(data).isPresent()) {
            return JSON.parseObject(data).toJavaObject(GameSlot.class);
        }

        var one = Optional.ofNullable(
                gameSlotServiceImpl.lambdaQuery()
                        .eq(GameSlot::getId, id)
                        .eq(GameSlot::getPlatId, platId)
                        .one()
        ).orElseThrow(() -> BusinessException.buildException(CodeInfo.GAME_NOT_EXISTS));

        jedisUtil.hset(KeyConstant.GAME_SLOT_HASH, subKey, JSON.toJSONString(one));
        return one;
    }

    /**
     * 获取老虎机游戏记录信息
     *
     * @param id     slot_game id
     * @param platId plat_id
     * @return res
     */
    public GameSlot oneWithoutEx(String id, Integer platId) {
        var subKey = TextUtils.buildSubKey(id, platId.toString());
        var data = jedisUtil.hget(KeyConstant.GAME_SLOT_HASH, subKey);
        if (Optional.ofNullable(data).isPresent()) {
            return JSON.parseObject(data).toJavaObject(GameSlot.class);
        }

        var one = gameSlotServiceImpl.lambdaQuery()
                .eq(GameSlot::getId, id)
                .eq(GameSlot::getPlatId, platId)
                .one();
        if (Optional.ofNullable(one).isPresent()) {
            jedisUtil.hset(KeyConstant.GAME_SLOT_HASH, subKey, JSON.toJSONString(one));
        }

        return one;
    }
}
