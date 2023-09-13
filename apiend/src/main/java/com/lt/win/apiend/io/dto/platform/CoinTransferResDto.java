package com.lt.win.apiend.io.dto.platform;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author: David
 * @date: 17/04/2020
 * @description:
 */
@Data
@Builder
public class CoinTransferResDto {
    @ApiModelProperty(name = "id", value = "游戏ID", example = "101")
    private Integer id;
    @ApiModelProperty(name = "coin", value = "账户余额", example = "100.00")
    private BigDecimal coin;
    @ApiModelProperty(name = "platCoin", value = "平台余额", example = "20.00")
    private BigDecimal platCoin;
}
