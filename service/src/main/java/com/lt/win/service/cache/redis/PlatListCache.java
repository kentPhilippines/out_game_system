package com.lt.win.service.cache.redis;

import com.alibaba.fastjson.JSON;
import com.lt.win.dao.generator.po.GameList;
import com.lt.win.dao.generator.po.PlatList;
import com.lt.win.dao.generator.service.GameListService;
import com.lt.win.dao.generator.service.PlatListService;
import com.lt.win.service.cache.KeyConstant;
import com.lt.win.service.exception.BusinessException;
import com.lt.win.utils.JedisUtil;
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
public class PlatListCache {
    private final JedisUtil jedisUtil;
    private final PlatListService platListServiceImpl;
    private final GameListService gameListServiceImpl;

    /**
     * 获取平台
     *
     * @param id id
     * @return 配置参数
     * @author David
     */
    public PlatList platList(Integer id) {
        var data = jedisUtil.hget(KeyConstant.PLAT_LIST_HASH, id.toString());
        if (Optional.ofNullable(data).isPresent()) {
            return JSON.parseObject(data).toJavaObject(PlatList.class);
        }

        var one = Optional.ofNullable(
                platListServiceImpl.lambdaQuery().eq(PlatList::getId, id).one()
        ).orElseThrow(() -> BusinessException.buildException(CodeInfo.GAME_NOT_EXISTS));

        jedisUtil.hset(KeyConstant.PLAT_LIST_HASH, id.toString(), JSON.toJSONString(one));
        return one;
    }

    /**
     * 获取平台
     *
     * @param code code
     * @return 配置参数
     * @author David
     */
    public PlatList platList(String code) {
        var data = jedisUtil.hget(KeyConstant.PLAT_LIST_HASH, code);
        if (Optional.ofNullable(data).isPresent()) {
            return JSON.parseObject(data).toJavaObject(PlatList.class);
        }

        var one = Optional.ofNullable(
                platListServiceImpl.lambdaQuery().eq(PlatList::getCode, code).one()
        ).orElseThrow(() -> BusinessException.buildException(CodeInfo.GAME_NOT_EXISTS));

        jedisUtil.hset(KeyConstant.PLAT_LIST_HASH, code, JSON.toJSONString(one));
        return one;
    }

    /**
     * 根据平台Code获取平台配置信息
     *
     * @param code  code
     * @param clazz clazz
     * @param <T>   T
     * @return config配置信息
     * @author David
     */
    public <T> T platListConfig(String code, Class<T> clazz) {
        return JSON.parseObject(platList(code).getConfig()).toJavaObject(clazz);
    }

    /**
     * 根据平台Id获取平台配置信息
     *
     * @param id    id
     * @param clazz clazz
     * @param <T>   T
     * @return config配置信息
     * @author David
     */
    public <T> T platListConfig(Integer id, Class<T> clazz) {
        return JSON.parseObject(platList(id).getConfig()).toJavaObject(clazz);
    }


    /**
     * 获取游戏
     *
     * @param id id
     * @return 配置参数
     * @author David
     */
    public GameList gameList(Integer id) {
        var data = jedisUtil.hget(KeyConstant.GAME_LIST_HASH, id.toString());
        if (Optional.ofNullable(data).isPresent()) {
            return JSON.parseObject(data).toJavaObject(GameList.class);
        }

        var one = Optional.ofNullable(
                gameListServiceImpl.lambdaQuery().eq(GameList::getId, id).one()
        ).orElseThrow(() -> BusinessException.buildException(CodeInfo.GAME_NOT_EXISTS));

        jedisUtil.hset(KeyConstant.GAME_LIST_HASH, id.toString(), JSON.toJSONString(one));
        return one;
    }

    /**
     * 获取游戏
     *
     * @param code code
     * @return 配置参数
     * @author David
     */
    public GameList gameList(String code) {
        var data = jedisUtil.hget(KeyConstant.GAME_LIST_HASH, code);
        if (Optional.ofNullable(data).isPresent()) {
            return JSON.parseObject(data).toJavaObject(GameList.class);
        }

        var one = Optional.ofNullable(gameListServiceImpl.lambdaQuery()
                .eq(GameList::getName, code)
                .one()).orElseThrow(() -> BusinessException.buildException(CodeInfo.GAME_NOT_EXISTS));

        jedisUtil.hset(KeyConstant.GAME_LIST_HASH, code, JSON.toJSONString(one));
        return one;
    }

    /**
     * 根据平台Code获取平台配置信息
     *
     * @param code  code
     * @param clazz clazz
     * @param <T>   T
     * @return config配置信息
     * @author David
     */
    public <T> T gameListConfig(String code, Class<T> clazz) {
        return JSON.parseObject(platList(code).getConfig()).toJavaObject(clazz);
    }

    /**
     * 根据平台Id获取平台配置信息
     *
     * @param id    id
     * @param clazz clazz
     * @param <T>   T
     * @return config配置信息
     * @author David
     */
    public <T> T gameListConfig(Integer id, Class<T> clazz) {
        return JSON.parseObject(platList(id).getConfig()).toJavaObject(clazz);
    }

    /**
     * 删除平台配置缓存
     */
    public void delPlatCache() {
        jedisUtil.del(KeyConstant.PLAT_LIST_HASH);
    }

    /**
     * 删除游戏配置缓存
     */
    public void delGameCache() {
        jedisUtil.del(KeyConstant.GAME_LIST_HASH);
    }

    /**
     * 缓存三方游戏 RollbackId
     */
    public void setRollBackTransactionIdHash(String platName, String transactionId) {
        var key = String.format(KeyConstant.PLAT_ROLLBACK_ID_HASH, platName);
        jedisUtil.hset(key, transactionId, transactionId);
    }

    /**
     * 获取三方游戏 RollbackId
     */
    public Boolean getRollBackTransactionIdHash(String platName, String transactionId) {
        var key = String.format(KeyConstant.PLAT_ROLLBACK_ID_HASH, platName);
        return jedisUtil.hexists(key, transactionId);
    }

    /**
     * 删除三方游戏 RollbackId
     */
    public void delRollbackTransactionIdHash(String platName, String transactionId) {
        var key = String.format(KeyConstant.PLAT_ROLLBACK_ID_HASH, platName);
        jedisUtil.hdel(key, transactionId);
    }
}
