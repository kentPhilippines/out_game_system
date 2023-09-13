package com.lt.win.apiend.io.dto.centeragent;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * <p>
 * 充值与提现总额
 * </p>
 *
 * @author andy
 * @since 2020/7/24
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepositWithdrawalDto {
    @ApiModelProperty(name = "totalDepositCoin", value = "充值总额", example = "2000")
    private BigDecimal totalDepositCoin;

    @ApiModelProperty(name = "totalWithdrawalCoin", value = "提现总额", example = "2000")
    private BigDecimal totalWithdrawalCoin;
}
