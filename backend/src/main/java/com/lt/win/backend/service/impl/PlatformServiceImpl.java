package com.lt.win.backend.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.google.common.collect.Lists;
import com.lt.win.backend.base.DictionaryBase;
import com.lt.win.backend.io.dto.Platform;
import com.lt.win.backend.redis.GameCache;
import com.lt.win.backend.service.IAdminInfoBase;
import com.lt.win.backend.service.IPlatformService;
import com.lt.win.dao.generator.po.GameList;
import com.lt.win.dao.generator.po.GameSlot;
import com.lt.win.service.cache.redis.PlatListCache;
import com.lt.win.service.exception.BusinessException;
import com.lt.win.service.io.bo.GameCommonBo;
import com.lt.win.service.io.constant.ConstData;
import com.lt.win.service.io.enums.LangEnum;
import com.lt.win.utils.components.response.CodeInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author: David
 * @date: 06/04/2020
 * @description:
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PlatformServiceImpl implements IPlatformService {
    private final GameCache gameCache;
    private final DictionaryBase dictionaryBase;
    private final PlatListCache platListCache;

    /**
     * 游戏列表
     *
     * @return 游戏列表
     */
    @Override
    public List<Platform.GameListInfo> gameList(Platform.GameListReqDto dto) {
        List<Platform.GameListInfo> gameListResCache = gameCache.getGameListResCache();
        if (dto.getGroupId() == 0) {
            return gameListResCache;
        } else {
            return gameListResCache.stream().filter(gameListInfo -> gameListInfo.getGroupId().equals(dto.getGroupId())).collect(Collectors.toList());
        }
    }

    /**
     * 游戏平台列表
     *
     * @return 平台列表
     */
    @Override
    public List<Platform.PlatListResInfo> platList() {
        return gameCache.getPlatListResCache();
    }


    /**
     * 根据GameID 获取游戏处理工厂类
     *
     * @param gameId 游戏ID
     * @param <T>    范型
     * @return 游戏工厂/老虎机工厂
     * @author David
     * @date 07/05/2020
     */
    @Override
    public <T> T platFactory(Integer gameId) {
        GameList game = platListCache.gameList(gameId);
        if (game == null) {
            throw BusinessException.buildException(CodeInfo.CONFIG_INVALID, gameId);
        }
        GameCommonBo.GameConfig config = JSON.parseObject("").toJavaObject(GameCommonBo.GameConfig.class);

        T factory;
//        if (game.getGroupId().equals(ConstData.SLOT_GAME_CATEGORY)) {
//            // 老虎机游戏专用
//            factory = (T) PlatSlotAbstractFactory.init(config.getModel(), config.getParent());
//        } else {
//            factory = (T) PlatAbstractFactory.init(config.getModel(), config.getParent());
//        }
//
//        if (null == factory) {
//            throw new BusinessException(CodeInfo.PLAT_FACTORY_NOT_EXISTS);
//        }

        return null;
    }

    /**
     * 游戏类型->平台名称->游戏名称->三级联动
     *
     * @return 三级联动列表
     */
    @Override
    public List<Platform.GameGroupDict> getCascadeByGameGroupList() {
        // 当前登录账号
        var currentAdmin = IAdminInfoBase.getHeadLocalData();
        // 一级:游戏类型list
        var gameGroupCache = gameCache.getGameGroupCache();
        // 二级:平台名称list
        var gameListCache = gameCache.getGameListResCache();
        // 三级:游戏名称list
        var gameSlotListCache = gameCache.getGameSlotListCache();

        // 返回
        return gameGroupCache.stream().map(gameGroup -> {
            var groupId = gameGroup.getId();
            // 一级:游戏类型
            var gameGroupDict = Platform.GameGroupDict.builder().groupId(groupId).groupName(gameGroup.getNameAbbr()).build();

            // 二级:平台名称list
            var gameListDictList = gameListCache.stream()
                    .filter(x -> x.getGroupId().equals(groupId))
                    .map(gameListCacheEntity -> {
                        var gameListDict = Platform.GameListDict.builder().gameListId(gameListCacheEntity.getId()).gameListName(gameListCacheEntity.getName()).build();

                        // 三级:游戏名称list
                        List<Platform.GameListSubDict> subDictList;

                        // 游戏列表(老虎机)
                        if (2 == gameListCacheEntity.getGroupId() && Optional.ofNullable(gameSlotListCache).isPresent() && !gameSlotListCache.isEmpty()) {
                            subDictList = getGameSlotListByLang(gameSlotListCache, currentAdmin.getLang());
                        } else {
                            subDictList = getGameSubListByModel(gameListCacheEntity.getModel());
                        }
                        // 关联游戏名称list
                        gameListDict.setList(subDictList);
                        return gameListDict;
                    })
                    .collect(Collectors.toList());

            // 关联平台名称list(二级)
            gameGroupDict.setList(gameListDictList);
            return gameGroupDict;
        }).collect(Collectors.toList());
    }

    /**
     * 获取游戏名称list-老虎机
     *
     * @param gameSlotList 老虎机列表
     * @param lang         语言
     * @return List<Platform.GameListSubDict>
     */
    private List<Platform.GameListSubDict> getGameSlotListByLang(List<GameSlot> gameSlotList, String lang) {
        List<Platform.GameListSubDict> list = new ArrayList<>();
        if (lang.equals(LangEnum.ZH.getValue())) {
            for (var o : gameSlotList) {
                if (null != o && StringUtils.isNotBlank(o.getId()) && StringUtils.isNotBlank(o.getNameZh())) {
                    list.add(Platform.GameListSubDict.builder().gameId(o.getId()).gameName(o.getNameZh()).build());
                }
            }
        } else {
            for (var o : gameSlotList) {
                if (null != o && StringUtils.isNotBlank(o.getId()) && StringUtils.isNotBlank(o.getName())) {
                    list.add(Platform.GameListSubDict.builder().gameId(o.getId()).gameName(o.getName()).build());
                }
            }
        }
        return list;
    }

    /**
     * 获取游戏名称ListByDictCategory
     *
     * @param model model
     * @return List<Platform.GameListSubDict>
     */
    private List<Platform.GameListSubDict> getGameSubListByModel(String model) {
        List<Platform.GameListSubDict> gameListSubDictList = new ArrayList<>();
        // 游戏列表
        var dictItemMap = dictionaryBase.getDictItemMapByModel(model);
        if (Optional.ofNullable(dictItemMap).isPresent() && !dictItemMap.isEmpty()) {
            // 字典码->字典名称
            dictItemMap.forEach((code, title) -> {
                if (StringUtils.isNotBlank(code) && StringUtils.isNotBlank(title)) {
                    gameListSubDictList.add(Platform.GameListSubDict.builder()
                            .gameId(code)
                            .gameName(title)
                            .build());
                }
            });
        }
        return gameListSubDictList;
    }
}
