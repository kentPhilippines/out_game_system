package com.lt.win.apiend.service;

import com.lt.win.apiend.io.bo.HomeParams;
import com.lt.win.apiend.io.bo.PlatformParams;
import com.lt.win.apiend.io.dto.platform.*;
import com.lt.win.service.io.dto.BaseParams;
import com.lt.win.utils.components.pagination.ReqPage;
import com.lt.win.utils.components.pagination.ResPage;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author: David
 * @date: 06/04/2020
 * @description:
 */
public interface IGameService {
    /**
     * 获取有些列表
     *
     * @return 游戏列表
     */
    List<HomeParams.GameIndexResDto> list();


    GameModelDto checkGameStatus(Integer gameId);

//    Map<Integer, List<PlatFactoryParams.PlatSlotGameResDto>> casinoList(List<Integer> groups, Integer size, String name);
//
//    ResPage<PlatFactoryParams.PlatSlotGameResDto> slotGameList(ReqPage<SlotGameReqDto> dto);

    Boolean slotGameFavorite(SlotGameFavoriteReqDto dto, BaseParams.HeaderInfo user);

    <T> T platFactory(Integer gameId);

    /**
     * 根据 PlatId 获取游戏父工厂类
     *
     * @param platId 平台ID
     * @return 游戏工厂父类
     * @author David
     * @date 07/05/2020
     */
 //  PlatAbstractFactory platFactoryByPlatId(Integer platId);

    /**
     * 游戏列表
     *
     * @return 游戏列表
     */
    List<GameListInfo> gameList(List<Integer> groups);

    /**
     * 游戏列表
     *
     * @return 游戏列表
     */
    List<PlatformParams.PlatListResInfo> platList();

    /**
     * 游戏详情
     *
     * @param id     游戏id
     * @param platId 平台id
     * @return 游戏信息
     */
   // PlatFactoryParams.PlatSlotGameResDto gameDetails(String id, Integer platId);
}
