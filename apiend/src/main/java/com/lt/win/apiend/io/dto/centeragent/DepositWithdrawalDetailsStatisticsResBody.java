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
public class DepositWithdrawalDetailsStatisticsResBody {

    @ApiModelProperty(name = "totalCoin", value = "总额", example = "20000")
    private BigDecimal totalCoin;
}
