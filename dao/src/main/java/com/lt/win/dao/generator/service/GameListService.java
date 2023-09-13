package com.lt.win.dao.generator.service;

import com.lt.win.dao.generator.po.GameList;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lt.win.dao.generator.po.GameSlot;

/**
 * <p>
 * 游戏类型表 服务类
 * </p>
 *
 * @author David
 * @since 2022-09-20
 */
public interface GameListService extends IService<GameList> {

    /**
     * 基于游戏id查询游戏
     * @param id 游戏id
     * @return 游戏
     */
    GameList queryGameListById(Integer id);

}
