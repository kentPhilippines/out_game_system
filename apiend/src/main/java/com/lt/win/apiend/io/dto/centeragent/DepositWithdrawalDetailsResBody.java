package com.lt.win.apiend.io.dto.centeragent;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * <p>
 * 代理中心-我的报表-充值与提现详情ResBody
 * </p>
 *
 * @author andy
 * @since 2020/7/24
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepositWithdrawalDetailsResBody {
    @ApiModelProperty(name = "username", value = "用户名称", example = "andy")
    private String username;
    @ApiModelProperty(name = "coin", value = "提现金额", example = "2000")
    private BigDecimal coin;
    @ApiModelProperty(name = "payCoin", value = "充值金额", example = "2000")
    private BigDecimal payCoin;
    @ApiModelProperty(name = "status", value = "充值/提现状态:充值状态[1-手动到账 2-自动到账 9-管理员充值];提现状态[1-成功]", example = "1")
    private Integer status;
    @ApiModelProperty(name = "createdAt", value = "充值/提现时间", example = "1587226981")
    private Integer createdAt;
    @ApiModelProperty(name = "uid", value = "UID", example = "1")
    private Integer uid;
}
