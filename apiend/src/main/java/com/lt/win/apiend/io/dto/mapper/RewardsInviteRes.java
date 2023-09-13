package com.lt.win.apiend.io.dto.mapper;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * <p>
 * 代理中心-我的报表-奖金与佣金详情 查询邀请好友奖励记录
 * </p>
 *
 * @author andy
 * @since 2020/7/25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RewardsInviteRes {
    @ApiModelProperty(name = "monthName", value = "月份", example = "2020-07-01")
    private String monthName;

    @ApiModelProperty(name = "username", value = "用户名称", example = "andy")
    private String username;

    @ApiModelProperty(name = "coin", value = "金额", example = "2000")
    private BigDecimal coin;

    @ApiModelProperty(name = "rewardsCoin", value = "充值返利/邀请奖金/流水返佣", example = "2000")
    private BigDecimal rewardsCoin;

    @ApiModelProperty(name = "createdAt", value = "充值/提现时间", example = "1587226981")
    private Integer createdAt;

    @ApiModelProperty(name = "uid", value = "UID", example = "1")
    private Integer uid;

    @ApiModelProperty(name = "category", value = "类型:1-邀请奖金 2-充值返利")
    private Integer category;
}
