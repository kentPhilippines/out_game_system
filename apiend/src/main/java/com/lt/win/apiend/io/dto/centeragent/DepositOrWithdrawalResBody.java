package com.lt.win.apiend.io.dto.centeragent;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * <p>
 * 代理中心-充值提现 响应
 * </p>
 *
 * @author andy
 * @since 2020/10/20
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepositOrWithdrawalResBody {
    @ApiModelProperty(name = "uid", value = "UID", example = "1")
    private Integer uid;
    @ApiModelProperty(name = "username", value = "用户名称", example = "andy")
    private String username;
    @ApiModelProperty(name = "coin", value = "充值金额/提现金额", example = "2000")
    private BigDecimal coin;
    @ApiModelProperty(name = "payCoin", value = "到账金额:充值展示", example = "100.00")
    private BigDecimal payCoin;

    @ApiModelProperty(name = "status", value = "充值/提现状态:充值状态[1-手动到账 2-自动到账 9-管理员充值];提现状态[1-成功]", example = "1")
    private Integer status;
    @ApiModelProperty(name = "createdAt", value = "充值/提现时间", example = "1587226981")
    private Integer createdAt;

    @ApiModelProperty(name = "bankName", value = "银行名称", example = "andy")
    private String bankName;
    @ApiModelProperty(name = "bankAccount", value = "银行账号:提现展示", example = "6225123412348888")
    private String bankAccount;

    @ApiModelProperty(name = "category", value = "充值类型:1-银联 2-微信 3-支付宝 4-QQ 5-QR扫码", example = "1")
    private Integer category;
}
