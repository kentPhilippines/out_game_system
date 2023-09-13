package com.lt.win.backend.redis;

import com.alibaba.fastjson.JSON;
import com.lt.win.backend.io.dto.Platform;
import com.lt.win.dao.generator.po.GameGroup;
import com.lt.win.dao.generator.po.GameList;
import com.lt.win.dao.generator.po.GameSlot;
import com.lt.win.dao.generator.po.PlatList;
import com.lt.win.dao.generator.service.GameGroupService;
import com.lt.win.dao.generator.service.GameListService;
import com.lt.win.dao.generator.service.GameSlotService;
import com.lt.win.dao.generator.service.PlatListService;
import com.lt.win.service.cache.KeyConstant;
import com.lt.win.service.cache.redis.PlatListCache;
import com.lt.win.utils.BeanConvertUtils;
import com.lt.win.utils.JedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * <p>
 * redis缓存类:首页信息
 * </p>
 *
 * @author David
 * @since 2020/6/23
 */
@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GameCache {
    private final JedisUtil jedisUtil;
    private final GameListService gameListServiceImpl;
    private final PlatListService platListServiceImpl;
    private final GameSlotService gameSlotServiceImpl;
    private final GameGroupService gameGroupServiceImpl;
    private final PlatListCache platListCache;

    /**
     * 获取平台列表(返回API)
     *
     * @return 平台列表
     */
    public List<Platform.PlatListResInfo> getPlatListResCache() {
        String key = KeyConstant.PLATFORM_HASH;
        String subKey = KeyConstant.PLATFORM_HASH_PLAT_LIST;
        String value = jedisUtil.hget(key, subKey);
        if (StringUtils.isNotBlank(value)) {
            return JSON.parseArray(value, Platform.PlatListResInfo.class);
        }

        List<Platform.PlatListResInfo> collect = platListServiceImpl.lambdaQuery()
                .eq(PlatList::getStatus, 1)
                .orderByDesc(PlatList::getSort)
                .list()
                .stream()
                .map(o -> BeanConvertUtils.beanCopy(o, Platform.PlatListResInfo::new))
                .collect(Collectors.toList());

        if (!collect.isEmpty()) {
            jedisUtil.hset(key, subKey, JSON.toJSONString(collect));
        }
        return collect;
    }

    /**
     * 获取游戏列表
     *
     * @return 游戏列表
     */
    public List<Platform.GameListInfo> getGameListResCache() {
        String key = KeyConstant.PLATFORM_HASH;
        String subKey = KeyConstant.PLATFORM_HASH_GAME_LIST;

        String data = jedisUtil.hget(key, subKey);
        if (StringUtils.isNotBlank(data)) {
            return JSON.parseArray(data, Platform.GameListInfo.class);
        }
        List<Platform.GameListInfo> collect = gameListServiceImpl.lambdaQuery()
                .in(GameList::getStatus, 0, 1, 2)
                .orderByDesc(GameList::getSort)
                .orderByDesc(GameList::getId)
                .list()
                .stream()
                .map(o -> BeanConvertUtils.beanCopy(o, Platform.GameListInfo::new))
                .collect(Collectors.toList());

        if (!collect.isEmpty()) {
            jedisUtil.hset(key, subKey, JSON.toJSONString(collect));
        }
        return collect;
    }

    public void updateGroupGameListCache() {
        jedisUtil.del(KeyConstant.PLATFORM_HASH);
    }

    public void delGameListCache() {
        platListCache.delGameCache();
    }

    /**
     * 获取游戏组列表
     *
     * @return 游戏组列表
     */
    public List<GameGroup> getGameGroupCache() {
        String key = KeyConstant.PLATFORM_HASH;
        String subKey = KeyConstant.PLATFORM_HASH_GAME_GROUP_LIST;

        String data = jedisUtil.hget(key, subKey);
        if (StringUtils.isNotBlank(data)) {
            return JSON.parseArray(data, GameGroup.class);
        }
        List<GameGroup> collect = gameGroupServiceImpl.lambdaQuery()
                .eq(GameGroup::getStatus, 1)
                .orderByDesc(GameGroup::getSort)
                .orderByDesc(GameGroup::getId)
                .list();

        if (!collect.isEmpty()) {
            jedisUtil.hset(key, subKey, JSON.toJSONString(collect));
        }
        return collect;
    }

    /**
     * HB哈巴游戏列表
     *
     * @return HB哈巴游戏列表
     */
    public List<GameSlot> getGameSlotListCache() {
        String key = KeyConstant.PLATFORM_HASH;
        String subKey = KeyConstant.GAME_SLOT_HASH;

        String data = jedisUtil.hget(key, subKey);
        if (StringUtils.isNotBlank(data)) {
            return JSON.parseArray(data, GameSlot.class);
        }

        List<GameSlot> gameSlotList = gameSlotServiceImpl.lambdaQuery().eq(GameSlot::getStatus, 1).list();
        if (Optional.ofNullable(gameSlotList).isPresent()) {
            jedisUtil.hset(key, subKey, JSON.toJSONString(gameSlotList));
        }
        return gameSlotList;
    }


    public void delGamePropCache(Integer id) {
        jedisUtil.hdel(KeyConstant.GAME_PROP_HASH, id.toString());
    }
}