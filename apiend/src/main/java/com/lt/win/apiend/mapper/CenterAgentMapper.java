package com.lt.win.apiend.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lt.win.apiend.io.dto.centeragent.ReportsReqBody;
import com.lt.win.apiend.io.dto.centeragent.SubordinateListReqBody;
import com.lt.win.apiend.io.dto.mapper.RewardsInviteReq;
import com.lt.win.apiend.io.dto.mapper.RewardsInviteRes;
import com.lt.win.dao.generator.po.User;

import java.math.BigDecimal;

/**
 * <p>
 * 代理中心
 * </p>
 *
 * @author andy
 * @since 2020/4/22
 */
public interface CenterAgentMapper {
    /**
     * 代理中心-我的报表
     *
     * @param po
     * @return
     */
    BigDecimal getReportsCoinStatistics(ReportsReqBody po);

    /**
     * 代理中心-我的报表-充值笔数
     *
     * @param po
     * @return
     */
    Integer getReportsDepositCountStatistics(ReportsReqBody po);

    /**
     * 代理中心-我的下级-列表
     *
     * @param page
     * @param po
     * @return
     */
    Page<User> subordinateList(Page page, SubordinateListReqBody po);

    /**
     * 代理中心-我的报表-奖金与佣金详情-列表 查询邀请好友奖励记录
     *
     * @param page
     * @param po
     * @return
     */
    Page<RewardsInviteRes> pageRewardsInviteList(Page page, RewardsInviteReq po);

    /**
     * 代理中心-我的报表-奖金与佣金详情-统计 邀请好友奖励
     *
     * @param po
     * @return
     */
    RewardsInviteRes getCoinRewardsInviteStatistics(RewardsInviteReq po);

    /**
     * 代理中心-我的下级-统计-直推
     * <p>
     * 描述:统计sup_uid_1=当前用户的数据
     *
     * @param uid
     * @param role
     * @return
     */
    Integer getSubordinateStatisticsZT(Integer uid, Integer role);

    /**
     * 代理中心-我的下级-统计-团队
     * <p>
     * 描述:统计sup_uid_1至sup_uid_6 = 当前用户的数据
     *
     * @param uid
     * @param role
     * @return
     */
    Integer getSubordinateStatisticsTD(Integer uid, Integer role);
}
