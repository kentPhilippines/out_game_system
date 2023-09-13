package com.lt.win.apiend.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lt.win.apiend.cache.redis.GameCache;
import com.lt.win.apiend.io.bo.HomeParams;
import com.lt.win.apiend.io.bo.PlatformParams.PlatListResInfo;
import com.lt.win.apiend.io.dto.platform.GameListInfo;
import com.lt.win.apiend.io.dto.platform.GameModelDto;
import com.lt.win.apiend.io.dto.platform.SlotGameFavoriteReqDto;
import com.lt.win.apiend.io.dto.platform.SlotGameReqDto;
import com.lt.win.apiend.service.IGameService;
import com.lt.win.dao.generator.po.GameSlot;
import com.lt.win.dao.generator.po.GameSlotFavorite;
import com.lt.win.dao.generator.po.PlatList;
import com.lt.win.dao.generator.service.GameSlotFavoriteService;
import com.lt.win.dao.generator.service.impl.GameSlotServiceImpl;
import com.lt.win.service.base.UserServiceBase;
import com.lt.win.service.cache.redis.GameSlotCache;
import com.lt.win.service.cache.redis.PlatListCache;
import com.lt.win.service.exception.BusinessException;
import com.lt.win.service.io.constant.ConstData;
import com.lt.win.service.io.dto.BaseParams;
import com.lt.win.service.io.enums.DeviceEnum;
import com.lt.win.service.thread.ThreadHeaderLocalData;
import com.lt.win.utils.BeanConvertUtils;
import com.lt.win.utils.DateUtils;
import com.lt.win.utils.components.pagination.ReqPage;
import com.lt.win.utils.components.pagination.ResPage;
import com.lt.win.utils.components.response.CodeInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static com.lt.win.service.io.dto.BaseParams.HeaderInfo;
import static com.lt.win.utils.components.pagination.BasePage.ASC_SORT;

/**
 * @author: David
 * @date: 06/04/2020
 * @description:
 */
@Service
@Slf4j
public class GameServiceImpl implements IGameService {
    public static final String MODEL = "model";
    @Resource
    GameSlotServiceImpl gameSlotServiceImpl;
    @Autowired
    GameSlotFavoriteService gameSlotFavoriteServiceImpl;
    @Resource
    UserServiceBase userServiceBase;
    @Resource
    GameSlotCache gameSlotCache;
    @Resource
    private GameCache gameCache;
    @Resource
    private PlatListCache platListCache;

    /**
     * 获取游戏列表
     *
     * @return 游戏列表
     */
    @Override
    public List<HomeParams.GameIndexResDto> list() {
        return gameCache.getGroupGameListCache();
    }


    /**
     * 游戏列表
     *
     * @return 游戏列表
     */
    @Override
    public List<GameListInfo> gameList(List<Integer> groups) {
        List<GameListInfo> list = gameCache.getGameListResCache();
        if (CollectionUtil.isEmpty(list) || CollectionUtil.isEmpty(groups)) {
            return list;
        }
        return list.stream().filter(s -> groups.contains(s.getGroupId())).collect(Collectors.toList());
    }

    /**
     * 游戏平台列表
     *
     * @return 平台列表
     */
    @Override
    public List<PlatListResInfo> platList() {
        return gameCache.getPlatListResCache();
    }


    /**
     * 检查游戏状态
     *
     * @param gameId gameId
     * @return 游戏属性
     */
    @Override
    public GameModelDto checkGameStatus(Integer gameId) {
        GameModelDto gameModelDto = gameCache.getGamePropCache(gameId);
        if (gameModelDto == null) {
            throw new BusinessException(CodeInfo.GAME_NOT_EXISTS);
        } else if (gameModelDto.getGameStatus() != 1 && !gameId.toString().startsWith(String.valueOf(ConstData.SLOT_GAME_CATEGORY))) {
            throw new BusinessException(CodeInfo.GAME_NOT_OPEN);
        } else if (gameModelDto.getPlatStatus() != 1) {
            throw new BusinessException(CodeInfo.GAME_UNDER_MAINTENANCE);
        }

        return gameModelDto;
    }
//
//    @Override
//    public Map<Integer, List<PlatFactoryParams.PlatSlotGameResDto>> casinoList(List<Integer> groups, Integer size, String name) {
//        List<GameListInfo> list = gameCache.getGameListResCache();
//        Map<Integer, List<PlatFactoryParams.PlatSlotGameResDto>> map = new HashMap<>();
//        if (CollectionUtil.isEmpty(list) || CollectionUtil.isEmpty(groups)) {
//            return null;
//        }
//        Map<Integer, List<Integer>> mapList = list.stream()
//                .filter(s -> groups.contains(s.getGroupId()))
//                .collect(Collectors.toMap(GameListInfo::getGroupId, item -> {
//                    List<Integer> list1 = CollectionUtil.newArrayList();
//                    list1.add(item.getId());
//                    return list1;
//                }, (n1, n2) -> {
//                    n1.addAll(n2);
//                    return n1;
//                }));
//        ReqPage<SlotGameReqDto> req = new ReqPage<>();
//        req.setPage(1, size);
//        SlotGameReqDto dto = new SlotGameReqDto();
//        dto.setIsCasino(1);
//        dto.setCategory(4);
//        dto.setName(name);
//        req.setData(dto);
//        mapList.forEach((k, v) -> {
//            req.getData().setIds(v);
//            map.put(k, this.slotGameList(req).getList());
//        });
//        return map;
//    }
//
//    /**
//     * 获取老虎机游戏列表
//     *
//     * @param dto {"id"， "category", "name"}
//     * @return 老虎机游戏信息
//     */
//    @Override
//    public ResPage<PlatFactoryParams.PlatSlotGameResDto> slotGameList(ReqPage<SlotGameReqDto> dto) {
//        // 获取Header信息 Token、lang、device
//        HeaderInfo headLocalData = ThreadHeaderLocalData.HEADER_INFO_THREAD_LOCAL.get();
//        // 游客访问设定uid=0
//        List<PlatListResInfo> platList = gameCache.getPlatListResCache();
//        List<Integer> platIdList = platList.stream().map(PlatListResInfo::getId).collect(Collectors.toList());
//        int uid = headLocalData.getId() != null ? headLocalData.getId() : 0;
//        LambdaQueryChainWrapper<GameSlot> eq = gameSlotServiceImpl.lambdaQuery()
//                .in(CollectionUtil.isNotEmpty(dto.getData().getIds()), GameSlot::getGameId, dto.getData().getIds())
//                .in(!Objects.isNull(headLocalData.getDevice()), GameSlot::getDevice, 0, headLocalData.getDevice().equals(DeviceEnum.D.getValue()) ? 1 : 2)
//                .in(CollectionUtil.isNotEmpty(platIdList), GameSlot::getPlatId, platIdList)
//                .eq(Objects.nonNull(dto.getData().getIsCasino()), GameSlot::getIsCasino, dto.getData().getIsCasino())
//                .likeRight(StringUtils.isNotBlank(dto.getData().getName()), GameSlot::getName, dto.getData().getName())
//                .eq(GameSlot::getStatus, 1);
//        Integer category = dto.getData().getCategory();
//        switch (category) {
//            // 种类:0-全部游戏 1-热门游戏 2-最新游戏 3-我的收藏
//            case 0:
//                eq.orderByDesc(GameSlot::getHotStar);
//                break;
//            case 1:
//                eq.orderByDesc(GameSlot::getHotStar)
//                        .orderByDesc(GameSlot::getSort);
//                break;
//            case 2:
//                eq.orderByDesc(GameSlot::getIsNew)
//                        .orderByDesc(GameSlot::getSort);
//                break;
//            case 3:
//                if (uid == 0) {
//                    return new ResPage<>();
//                }
//                List<GameSlotFavorite> favoriteList = userServiceBase.gameFavoriteByUid(uid);
//                if (CollectionUtil.isEmpty(favoriteList)) {
//                    eq.in(GameSlot::getId, CollectionUtil.newArrayList(""))
//                            .in(GameSlot::getGameId, CollectionUtil.newArrayList(0))
//                            .orderByDesc(GameSlot::getHotStar)
//                            .orderByDesc(GameSlot::getSort);
//                } else {
//                    eq.in(GameSlot::getId, favoriteList.stream().map(GameSlotFavorite::getGameSlotId).collect(Collectors.toList()))
//                            .in(GameSlot::getGameId, favoriteList.stream().map(GameSlotFavorite::getGameId).collect(Collectors.toList()))
//                            .orderByDesc(GameSlot::getHotStar)
//                            .orderByDesc(GameSlot::getSort);
//                }
//                break;
//            case 4:
//                eq.orderByDesc(GameSlot::getUpdatedAt);
//            default:
//                break;
//        }
//        // 分页数据结果集
//        Page<GameSlot> page = eq.page(dto.getPage());
//
//        if (StringUtils.isNotBlank(dto.getSortKey())) {
//            if (ASC_SORT.equals(dto.getSortKey().toUpperCase(Locale.US))) {
//                page.setOrders(OrderItem.ascs(dto.getSortField()));
//            }
//            if (dto.getSortKey().equals(dto.getSortKey().toUpperCase(Locale.US))) {
//                page.setOrders(OrderItem.descs(dto.getSortField()));
//            }
//        }
//        Map<Integer, String> platMap = platList.stream().collect(Collectors.toMap(PlatListResInfo::getId, PlatListResInfo::getName));
//        page = Optional.ofNullable(eq.page(page)).orElseThrow(() -> new BusinessException(CodeInfo.GAME_NOT_EXISTS));
//        List<String> favoriteList = userServiceBase.slotFavoriteByUid(uid);
//        Page<PlatSlotGameResDto> returnPage = BeanConvertUtils.copyPageProperties(
//                page,
//                PlatSlotGameResDto::new,
//                (ori, dest) -> {
//                    dest.setIsFavorite(favoriteList.contains(ori.getId().concat("-").concat(String.valueOf(ori.getGameId()))) ? 1 : 0);
//                    dest.setPlatName(platMap.get(ori.getPlatId()));
//                }
//        );
//        ResPage<PlatSlotGameResDto> m = new ResPage<>();
//        m.setCurrent(returnPage.getCurrent());
//        m.setSize(returnPage.getSize());
//        m.setTotal(returnPage.getTotal());
//        m.setList(returnPage.getRecords());
//        m.setPages(returnPage.getPages());
//        return m;
//
//    }

    /**
     * 收藏游戏
     *
     * @param dto {gameId, gameSlotId, direction}
     * @return true:成功 false:失败
     */
    @Override
    public Boolean slotGameFavorite(SlotGameFavoriteReqDto dto, BaseParams.HeaderInfo user) {
        GameSlot slotId = gameSlotServiceImpl.lambdaQuery()
                .eq(GameSlot::getId, dto.getGameSlotId())
                .eq(GameSlot::getGameId, dto.getGameId())
                .one();
        if (slotId == null) {
            throw new BusinessException(CodeInfo.GAME_NOT_EXISTS);
        }

        GameSlotFavorite one = gameSlotFavoriteServiceImpl.lambdaQuery()
                .eq(GameSlotFavorite::getUid, user.getId())
                .eq(GameSlotFavorite::getGameId, dto.getGameId())
                .eq(GameSlotFavorite::getGameSlotId, dto.getGameSlotId())
                .one();

        if (dto.getDirection().equals(1) && null == one) {
            Integer time = DateUtils.getCurrentTime();
            GameSlotFavorite gsf = new GameSlotFavorite();
            gsf.setUid(user.getId());
            gsf.setGameId(dto.getGameId());
            gsf.setGameSlotId(dto.getGameSlotId());
            gsf.setCreatedAt(time);
            gsf.setUpdatedAt(time);
            gameSlotFavoriteServiceImpl.save(gsf);
        } else if (dto.getDirection().equals(0) && null != one) {
            gameSlotFavoriteServiceImpl.removeById(one.getId());
        } else {
            if (dto.getDirection().equals(1)) {
                throw new BusinessException(CodeInfo.GAME_SLOT_FAVORITE_ALREADY);
            } else {
                throw new BusinessException(CodeInfo.GAME_SLOT_FAVORITE_NOT_YET);

            }
        }
        return Boolean.TRUE;
    }

    @Override
    public <T> T platFactory(Integer gameId) {
        return null;
    }


//    @Override
//    public <T> T platFactory(Integer gameId) {
//        GameModelDto game = checkGameStatus(gameId);
//        JSONObject jsonObject = JSON.parseObject(game.getGameModel());
//        if (null == jsonObject || jsonObject.getString(MODEL) == null) {
//            throw new BusinessException(CodeInfo.PLAT_FACTORY_NOT_EXISTS);
//        }
//
//        T factory;
//        if (game.getGroupId().equals(ConstData.SLOT_GAME_CATEGORY)) {
//            // 老虎机游戏专用
//            factory = (T) PlatSlotAbstractFactory.init(jsonObject.getString(MODEL), game.getPlatModel());
//        } else {
//            factory = (T) PlatAbstractFactory.init(jsonObject.getString(MODEL), game.getPlatModel());
//        }
//
//        if (null == factory) {
//            throw new BusinessException(CodeInfo.PLAT_FACTORY_NOT_EXISTS);
//        }
//
//        return factory;
//    }

//    /**
//     * 根据 PlatId 获取游戏父工厂类
//     *
//     * @param platId 平台ID
//     * @return 游戏工厂父类
//     * @author David
//     * @date 07/05/2020
//     */
//    @Override
//    public PlatAbstractFactory platFactoryByPlatId(Integer platId) {
//        PlatList one = platListCache.platList(platId);
//        if (null == one || one.getStatus() != 1) {
//            throw new BusinessException(CodeInfo.PLAT_FACTORY_NOT_EXISTS);
//        }
//
//        PlatAbstractFactory platAbstractFactory = PlatAbstractFactory.initByPlat(one.getName());
//        if (null == platAbstractFactory) {
//            throw new BusinessException(CodeInfo.PLAT_FACTORY_NOT_EXISTS);
//        }
//
//        return platAbstractFactory;
//    }

//    @Override
//    public PlatSlotGameResDto gameDetails(String id, Integer platId) {
//        // 获取Header信息 Token、lang、device
//        BaseParams.HeaderInfo headLocalData = ThreadHeaderLocalData.HEADER_INFO_THREAD_LOCAL.get();
//        // 游客访问设定uid=0
//        List<PlatListResInfo> platList = gameCache.getPlatListResCache();
//
//        int uid = headLocalData.getId() != null ? headLocalData.getId() : 0;
//        GameSlot gameSlot = gameSlotCache.oneWithoutEx(id, platId);
//
//        Map<Integer, String> platMap = platList.stream().collect(Collectors.toMap(PlatListResInfo::getId, PlatListResInfo::getName));
//
//        List<String> favoriteList = userServiceBase.slotFavoriteByUid(uid);
//        PlatSlotGameResDto platSlotGameResDto = new PlatSlotGameResDto();
//        BeanUtils.copyProperties(gameSlot, platSlotGameResDto);
//        platSlotGameResDto.setIsFavorite(favoriteList.contains(gameSlot.getId().concat("-").concat(String.valueOf(gameSlot.getGameId()))) ? 1 : 0);
//        platSlotGameResDto.setPlatName(platMap.get(gameSlot.getPlatId()));
//        return platSlotGameResDto;
//    }
}
