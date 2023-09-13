package com.lt.win.apiend.io.dto.wallet;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * <p>
 * 钱包-提现-添加提现记录 请求body
 * </p>
 *
 * @author andy
 * @since 2020/4/8
 */
@Data
public class WithdrawalAddReqBody {
    @NotNull(message = "ID不能为空")
    @ApiModelProperty(name = "id", value = "用户银行卡主键Id", example = "1")
    private Integer id;

    @NotNull(message = "金额不能为空")
    @ApiModelProperty(name = "coin", value = "提现金额", required = true, example = "100.00")
    private BigDecimal coin;

    @NotNull(message = "资金密码不能为空")
    @ApiModelProperty(name = "coinPassword", value = "资金密码", required = true)
    private String coinPassword;
}
