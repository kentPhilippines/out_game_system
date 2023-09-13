package com.lt.win.service.io.dto.promotions;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author: wells
 * @date: 2020/5/3
 * @description:
 */
@Data
@ApiModel(value = "ReceiveEnvelopeReqDto", description = "优惠活动领取红包请求实体")
public class ReceiveEnvelopeReqDto {
    @ApiModelProperty(name = "coin", value = "金额", example = "10")
    private BigDecimal coin;
}
