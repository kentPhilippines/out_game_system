package com.lt.win.backend.io.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @description:综合走势图-游戏盈亏与投注注数PO
 * @author: andy
 * @date: 2020/8/10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlatProfitAndBetCountChartPo {
    @ApiModelProperty(name = "name", value = "月份")
    private String name;
    @ApiModelProperty(name = "count", value = "数量")
    private Integer count;
    @ApiModelProperty(name = "count", value = "金额")
    private BigDecimal coin;
}
