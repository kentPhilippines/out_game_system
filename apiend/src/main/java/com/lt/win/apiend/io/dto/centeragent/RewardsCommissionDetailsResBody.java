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
public class RewardsCommissionDetailsResBody {

    @ApiModelProperty(name = "monthName", value = "月份", example = "2020-07-01")
    private Integer monthName;

    @ApiModelProperty(name = "username", value = "用户名称", example = "andy")
    private String username;

    @ApiModelProperty(name = "coin", value = "金额/投注总额/首充金额/首日充值;佣金的投注总额(当佣金奖励类型为0-流水佣金或2-满额人头彩金时，展示改字段的值)", example = "2000")
    private BigDecimal coin;

    @ApiModelProperty(name = "rewardsCoin", value = "返利/奖励/佣金金额", example = "2000")
    private BigDecimal rewardsCoin;

    @ApiModelProperty(name = "createdAt", value = "充值/提现时间", example = "1587226981")
    private Integer createdAt;

    @ApiModelProperty(name = "uid", value = "UID", example = "1")
    private Integer uid;

    @ApiModelProperty(name = "levelId", value = "会员等级ID")
    private Integer levelId;

    @ApiModelProperty(name = "avatar", value = "头像")
    private String avatar;

    @ApiModelProperty(name = "category", value = "邀请奖励类型:0-被邀请奖金1-邀请奖金 2-充值返利;佣金奖励类型:0-流水佣金 1-活跃会员佣金 2-满额人头彩金")
    private Integer category;

    @ApiModelProperty(name = "commissionCount", value = "佣金会员数:当佣金奖励类型为1时展示该字段", example = "3")
    private Integer commissionCount;

    @ApiModelProperty(name = "commissionId", value = "佣金记录Id:当佣金奖励类型为1时展示该字段", example = "91524020444467201")
    private Long commissionId;
}
