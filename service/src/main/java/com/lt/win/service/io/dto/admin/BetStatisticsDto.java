package com.lt.win.service.io.dto.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 游戏投注统计
 *
 * @author fangzs
 * @date 2022/8/29 12:41
 */
@Data
@ApiModel("游戏投注统计")
public class BetStatisticsDto implements Serializable {

    @ApiModelProperty("币种")
    private String currency;

    @ApiModelProperty("总投注")
    private BigDecimal totalStake;

    @ApiModelProperty("总盈亏")
    private BigDecimal totalProfit;
}
