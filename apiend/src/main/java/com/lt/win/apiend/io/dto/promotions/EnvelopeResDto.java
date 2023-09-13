package com.lt.win.apiend.io.dto.promotions;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author: wells
 * @date: 2020/5/2
 * @description:
 */
@Data
@ApiModel(value = "EnvelopeResDto", description = "优惠活动生成红包响应实体")
public class EnvelopeResDto {
    @ApiModelProperty(name = "index", value = "下标", example = "1")
    private Integer index;
    @ApiModelProperty(name = "coin", value = "金额", example = "1.2")
    private BigDecimal coin;
}
