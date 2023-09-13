package com.lt.win.apiend.io.dto.platform;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author: David
 * @date: 13/04/2020
 * @description:
 */
@Data
@Builder
public class GameBalanceResDto {
    @ApiModelProperty(name = "id", value = "游戏ID", example = "101")
    private Integer id;

    @ApiModelProperty(name = "coin", value = "余额", example = "100.00")
    private BigDecimal coin;
}
