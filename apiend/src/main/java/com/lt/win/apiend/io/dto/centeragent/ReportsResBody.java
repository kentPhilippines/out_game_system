package com.lt.win.apiend.io.dto.centeragent;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>
 * 代理中心-我的报表 响应body
 * </p>
 *
 * @author andy
 * @since 2020/4/22
 */
@Data
public class ReportsResBody {
    @ApiModelProperty(name = "totalCount", value = "玩家活跃度:总注册人数", example = "2000")
    private int totalCount;
    @ApiModelProperty(name = "newAddCount", value = "玩家活跃度:新增人数", example = "50")
    private int newAddCount;
    @ApiModelProperty(name = "firstDepositCount", value = "玩家活跃度:首充笔数", example = "5")
    private int firstDepositCount;
    @ApiModelProperty(name = "firstDepositCoin", value = "玩家活跃度:首充金额", example = "1000.00")
    private BigDecimal firstDepositCoin;
    @ApiModelProperty(name = "secondDepositCount", value = "玩家活跃度:二充笔数", example = "5")
    private int secondDepositCount;
    @ApiModelProperty(name = "secondDepositCoin", value = "玩家活跃度:二充金额", example = "1000.00")
    private BigDecimal secondDepositCoin;

    @ApiModelProperty(name = "totalDepositCoin", value = "充值与提现:充值总额", example = "2000")
    private BigDecimal totalDepositCoin;
    @ApiModelProperty(name = "totalWithdrawalCoin", value = "充值与提现:提现总额", example = "2000")
    private BigDecimal totalWithdrawalCoin;

    @ApiModelProperty(name = "rewardsCoin", value = "奖金与佣金:邀请奖励", example = "100")
    private BigDecimal rewardsCoin;
    @ApiModelProperty(name = "commissionCoin", value = "奖金与佣金:佣金奖励", example = "100")
    private BigDecimal commissionCoin;
}
