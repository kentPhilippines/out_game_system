package com.lt.win.apiend.io.dto.centeragent;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * <p>
 * 代理中心-我的报表-奖金与佣金详情ResBody
 * </p>
 *
 * @author andy
 * @since 2020/7/24
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RewardsCommissionDetailsStatisticsResBody {

    @ApiModelProperty(name = "totalCoin", value = "日充值总额/投注总额", example = "20000")
    private BigDecimal totalCoin;

    @ApiModelProperty(name = "totalRewardsCoin", value = "累计奖励/累计佣金", example = "2000")
    private BigDecimal totalRewardsCoin;

    @ApiModelProperty(name = "totalCoinRewardsInvited", value = "邀请奖励->全部->累计:被邀请奖金", example = "2000")
    private BigDecimal totalCoinRewardsInvited;
    @ApiModelProperty(name = "totalCoinRewardsInvite", value = "邀请奖励->全部->累计:邀请奖金", example = "2000")
    private BigDecimal totalCoinRewardsInvite;
    @ApiModelProperty(name = "totalCoinRewardsDeposit", value = "邀请奖励->全部->累计:充值返利", example = "2000")
    private BigDecimal totalCoinRewardsDeposit;
}
