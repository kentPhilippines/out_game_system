package com.lt.win.backend.io.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Auther: nobody
 * @Date: 2022/8/27 17:57
 * @Description:
 */

@Data
public class BetSlipsPO {

    @ApiModelProperty(name = "currency", value = "币种")
    private String currency;

    @ApiModelProperty(name = "coin", value = "统计字段")
    private BigDecimal coin;

}
