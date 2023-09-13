package com.lt.win.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lt.win.backend.io.bo.PlatManager;
import com.lt.win.backend.io.po.ListPlatPO;

/**
 * <p>
 * 游戏管理->平台管理
 * </p>
 *
 * @author andy
 * @since 2020/6/9
 */
public interface PlatManagerMapper extends BaseMapper<PlatManager.ListPlatReqBody> {
    /**
     * 平台管理-列表
     *
     * @param page
     * @param po
     * @return
     */
    Page<ListPlatPO> listPlat(Page page, PlatManager.ListPlatReqBody po);
}
