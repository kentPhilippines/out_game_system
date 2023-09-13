package com.lt.win.apiend.io.dto.centeragent;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>
 * 代理中心-我的报表-充值与提现详情-用户详情-列表ResBody
 * </p>
 *
 * @author andy
 * @since 2020/7/24
 */
@Data
@Builder
public class DepositWithdrawalUserDetailsResBody {
    @ApiModelProperty(name = "promoCode", value = "邀请码", example = "3426301")
    private Integer promoCode;

    @ApiModelProperty(name = "username", value = "用户名")
    private String username;

    @ApiModelProperty(name = "avatar", value = "头像")
    private String avatar;

    @ApiModelProperty(name = "levelId", value = "会员等级ID")
    private Integer levelId;

    @ApiModelProperty(name = "totalDepositCoin", value = "充值总额", example = "2000")
    private BigDecimal totalDepositCoin;

    @ApiModelProperty(name = "totalWithdrawalCoin", value = "提现总额", example = "2000")
    private BigDecimal totalWithdrawalCoin;

}
