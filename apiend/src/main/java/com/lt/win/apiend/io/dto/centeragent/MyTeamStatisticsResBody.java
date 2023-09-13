package com.lt.win.apiend.io.dto.centeragent;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>
 * 代理中心-我的团队-统计 响应body
 * </p>
 *
 * @author andy
 * @since 2020/4/22
 */
@Data
public class MyTeamStatisticsResBody {
    @ApiModelProperty(name = "registerCount", value = "下级人数(注册人数)", example = "50")
    private int registerCount = 0;
    @ApiModelProperty(name = "firstDepositCount", value = "玩家活跃度:首充笔数", example = "5")
    private int firstDepositCount = 0;
    @ApiModelProperty(name = "firstDepositCoin", value = "玩家活跃度:首充金额", example = "1000.00")
    private BigDecimal firstDepositCoin = BigDecimal.ZERO;
    @ApiModelProperty(name = "secondDepositCount", value = "玩家活跃度:二充笔数", example = "5")
    private int secondDepositCount = 0;
    @ApiModelProperty(name = "secondDepositCoin", value = "玩家活跃度:二充金额", example = "1000.00")
    private BigDecimal secondDepositCoin = BigDecimal.ZERO;

    @ApiModelProperty(name = "totalDepositCoin", value = "充值与提现:充值总额", example = "2000")
    private BigDecimal totalDepositCoin = BigDecimal.ZERO;
    @ApiModelProperty(name = "totalWithdrawalCoin", value = "充值与提现:提现总额", example = "2000")
    private BigDecimal totalWithdrawalCoin = BigDecimal.ZERO;

    @ApiModelProperty(name = "rewardsCoin", value = "奖金与佣金:邀请奖励", example = "100")
    private BigDecimal rewardsCoin = BigDecimal.ZERO;
    @ApiModelProperty(name = "commissionCoin", value = "奖金与佣金:佣金奖励", example = "100")
    private BigDecimal commissionCoin = BigDecimal.ZERO;
    @ApiModelProperty(name = "totalIncome", value = "累计收益", example = "200")
    private BigDecimal totalIncome = BigDecimal.ZERO;
}
