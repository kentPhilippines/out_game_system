package com.lt.win.backend.service;

import com.lt.win.backend.io.dto.Platform;

import java.util.List;


/**
 * @author: David
 * @date: 06/04/2020
 * @description:
 */
public interface IPlatformService {
    /**
     * 游戏列表
     *
     * @return 游戏列表
     */
    List<Platform.GameListInfo> gameList(Platform.GameListReqDto dto);

    /**
     * 游戏列表
     *
     * @return 游戏列表
     */
    List<Platform.PlatListResInfo> platList();



    /**
     * 根据GameID 获取游戏处理工厂类
     *
     * @param gameId 游戏ID
     * @param <T>    范型
     * @return 游戏工厂/老虎机工厂
     * @author David
     * @date 07/05/2020
     */
    <T> T platFactory(Integer gameId);

    /**
     * 游戏类型->平台名称->游戏名称->三级联动
     *
     * @return
     */
    List<Platform.GameGroupDict> getCascadeByGameGroupList();

}
