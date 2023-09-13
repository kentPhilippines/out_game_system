package com.lt.win.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lt.win.backend.io.bo.user.*;
import com.lt.win.backend.io.bo.user.*;

import java.util.List;

/**
 * <p>
 * 会员管理mapper
 * </p>
 *
 * @author andy
 * @since 2020/3/12
 */
public interface UserManagerMapper extends BaseMapper<ListReqBody> {
    /**
     * 列表查询
     *
     * @param page
     * @param po
     * @return
     */
    Page<ListResBody> list(Page page, ListReqBody po);

    /**
     * 在线人数列表查询
     *
     * @param page
     * @param po
     * @return
     */
    Page<OnlineUserCountListResBody> onlineUserCountList(Page<?> page, OnlineUserCountListReqBody po);

    /**
     * 团队在线
     *
     * @param po
     * @return
     */
    Page<ListResBody> listOnline(Page page, ListOnlineReqBody po);

    /**
     * 会员详情
     *
     * @param po
     * @return
     */
    DetailResBody getDetail(DetailReqBody po);

    /**
     * 获取会员信息
     *
     * @return
     */
    AddUserResBody getUserInfo(Integer uid);

    /**
     * 会员管理-会员旗列表查询
     *
     * @return
     */
    List<ListFlagResBody> listFlag();

    /**
     * 会员旗管理-已被使用的会员旗列表
     *
     * @return
     */
    Page<ListFlagUsedPO> listFlagUsed(Page page, ListFlagUsedReqBody po);

    /**
     * 获取用户拥有的会员旗
     *
     * @param bitCode
     * @return
     */
    List<Integer> listUserFlag(Integer bitCode);
}
