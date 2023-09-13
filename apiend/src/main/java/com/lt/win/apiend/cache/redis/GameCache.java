package com.lt.win.apiend.cache.redis;

import com.alibaba.fastjson.JSON;
import com.lt.win.apiend.io.bo.HomeParams.GameIndexResDto;
import com.lt.win.apiend.io.bo.PlatformParams.PlatListResInfo;
import com.lt.win.apiend.io.dto.platform.GameListDto;
import com.lt.win.apiend.io.dto.platform.GameListInfo;
import com.lt.win.apiend.io.dto.platform.GameModelDto;
import com.lt.win.apiend.mapper.GamePropMapper;
import com.lt.win.dao.generator.po.GameGroup;
import com.lt.win.dao.generator.po.GameList;
import com.lt.win.dao.generator.po.PlatList;
import com.lt.win.dao.generator.service.GameGroupService;
import com.lt.win.dao.generator.service.GameListService;
import com.lt.win.dao.generator.service.PlatListService;
import com.lt.win.service.cache.KeyConstant;
import com.lt.win.utils.BeanConvertUtils;
import com.lt.win.utils.JedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.alibaba.fastjson.JSON.parseObject;


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
    private final GameGroupService gameGroupServiceImpl;
    private final GameListService gameListServiceImpl;
    private final PlatListService platListServiceImpl;
    private final GamePropMapper gamePropMapper;

    /**
     * 获取平台列表(返回API)
     *
     * @return 平台列表
     */
    public List<PlatListResInfo> getPlatListResCache() {
        String key = KeyConstant.PLATFORM_HASH;
        String subKey = KeyConstant.PLATFORM_HASH_PLAT_LIST;
        String value = jedisUtil.hget(key, subKey);
        if (StringUtils.isNotBlank(value)) {
            return JSON.parseArray(value, PlatListResInfo.class);
        }

        List<PlatListResInfo> collect = platListServiceImpl.lambdaQuery()
                .eq(PlatList::getStatus, 1)
                .orderByDesc(PlatList::getSort)
                .orderByDesc(PlatList::getId)
                .list()
                .stream()
                .map(o -> BeanConvertUtils.beanCopy(o, PlatListResInfo::new))
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
    public List<GameListInfo> getGameListResCache() {
        String key = KeyConstant.PLATFORM_HASH;
        String subKey = KeyConstant.PLATFORM_HASH_GAME_LIST;

        String data = jedisUtil.hget(key, subKey);
        if (StringUtils.isNotBlank(data)) {
            return JSON.parseArray(data, GameListInfo.class);
        }
        List<GameListInfo> collect = gameListServiceImpl.lambdaQuery()
                .eq(GameList::getStatus, 1)
                .orderByDesc(GameList::getSort)
                .orderByDesc(GameList::getId)
                .list()
                .stream()
                .map(o -> BeanConvertUtils.beanCopy(o, GameListInfo::new))
                .collect(Collectors.toList());

        if (!collect.isEmpty()) {
            jedisUtil.hset(key, subKey, JSON.toJSONString(collect));
        }
        return collect;
    }

    /**
     * 获取游戏组-游戏列表
     *
     * @return 游戏组-游戏列表
     */
    public List<GameIndexResDto> getGroupGameListCache() {
        String key = KeyConstant.PLATFORM_HASH;
        String subKey = KeyConstant.PLATFORM_HASH_GROUP_GAME_LIST;

        String value = jedisUtil.hget(key, subKey);
        if (StringUtils.isNotBlank(value)) {
            return JSON.parseArray(value, GameIndexResDto.class);
        }

        // 游戏组
        List<GameGroup> gameGroup = gameGroupServiceImpl.lambdaQuery().select(GameGroup::getId, GameGroup::getName, GameGroup::getNameAbbr).eq(GameGroup::getStatus, 1).orderByDesc(GameGroup::getSort).orderByDesc(GameGroup::getId).list();
        // 游戏列表
        List<GameList> gameList = gameListServiceImpl.lambdaQuery()
                //.select(GameList::getId, GameList::getName, GameList::getGroupId, GameList::getModel, GameList::getStatus, GameList::getMaintenance)
                .in(GameList::getStatus, 1, 2)
                .orderByDesc(GameList::getSort)
                .orderByDesc(GameList::getId)
                .list();

        // 合并游戏至游戏组
        Map<Integer, List<GameListDto>> mapGroup = gameList.stream().collect(Collectors.toMap(
                GameList::getGroupId,
                game -> {
                    List<GameListDto> gameListDtolist = new ArrayList<>();
                    GameListDto gameListDto = BeanConvertUtils.copyProperties(game, GameListDto::new, (bo, sb) -> {
                       // sb.setSupportIframe(parseObject(bo.getModel()).getBooleanValue("iframe"));
                       // sb.setMaintenance(JSON.parseObject(bo.getMaintenance()));
                    });
                    gameListDtolist.add(gameListDto);
                    return gameListDtolist;
                },
                //  解决 MapKey 的冲突
                (List<GameListDto> existing, List<GameListDto> replacement) -> {
                    existing.addAll(replacement);
                    return existing;
                }
        ));

        // 过滤掉没有子游戏项目的项目组
        List<GameIndexResDto> collect = gameGroup.stream()
                .filter(gameGroup1 -> mapGroup.get(gameGroup1.getId()) != null)
                .map(o -> BeanConvertUtils.copyProperties(
                        o,
                        GameIndexResDto::new,
                        (game, gameIndexResDto) -> gameIndexResDto.setList(mapGroup.get(game.getId()))))
                .collect(Collectors.toList());

        if (!collect.isEmpty()) {
            jedisUtil.hset(key, subKey, JSON.toJSONString(collect));
        }
        return collect;
    }

    /**
     * 获取游戏属性(返回API)
     *
     * @return 游戏属性列表
     */
    public GameModelDto getGamePropCache(@NotNull Integer gameId) {
        String data = jedisUtil.hget(KeyConstant.GAME_PROP_HASH, gameId.toString());
        if (StringUtils.isNotBlank(data)) {
            return parseObject(data).toJavaObject(GameModelDto.class);
        }

        GameModelDto collect = gamePropMapper.getGameProp(gameId);
        if (collect != null) {
            jedisUtil.hset(KeyConstant.GAME_PROP_HASH, gameId.toString(), JSON.toJSONString(collect));
        }
        return collect;
    }


    public List<GameModelDto> getGamePropListCache(Integer gameId) {
        return gamePropMapper.getGamePropList(gameId);
    }
}
