package com.lt.win.backend.service;

import com.lt.win.backend.io.bo.PlatManager;
import com.lt.win.backend.io.dto.Platform;
import com.lt.win.utils.components.pagination.ReqPage;
import com.lt.win.utils.components.pagination.ResPage;

import java.util.List;

/**
 * <p>
 * 游戏管理->平台管理
 * </p>
 *
 * @author andy
 * @since 2020/6/8
 */
public interface IPlatManagerService {
    /**
     * 平台管理-列表
     *
     * @param reqBody
     * @return
     */
    ResPage<PlatManager.ListPlatResBody> listPlat(ReqPage<PlatManager.ListPlatReqBody> reqBody);

    /**
     * 平台管理-修改平台
     *
     * @param reqBody
     */
    void updatePlat(PlatManager.UpdatePlatReqBody reqBody);

    /**
     * 平台子游戏管理-列表
     *
     * @param dto
     * @return
     */
    ResPage<PlatManager.ListSubGameResBody> listSubGame(ReqPage<PlatManager.ListSubGameReqBody> dto);

    /**
     * 平台子游戏管理-修改平台
     *
     * @param reqBody
     */
    void updateSubGame(PlatManager.UpdateSubGameReqBody reqBody);

    /**
     * 游戏列表
     *
     * @return
     */
    List<PlatManager.GameListResBody> gameList();


    List<Platform.GameListInfo> gameList(List<Integer> groups);
}
