package com.lt.win.service.io.dto.promotions;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author: wells
 * @date: 2020/4/8
 * @description:
 */
@Data
@Builder
@ApiModel(value = "ApplicationActivityReqDto", description = "优惠活动优惠活动请求实体")
public class ApplicationActivityReqDto {
    @ApiModelProperty(name = "id", value = "活动ID", example = "1")
    private Integer id;
    @ApiModelProperty(name = "code", value = "活动标识", example = "Daily first deposit")
    private String code;
    @ApiModelProperty(name = "availableCoin", value = "可得彩金", example = "88.8")
    private BigDecimal availableCoin = BigDecimal.ZERO;
    @ApiModelProperty(name = "mosaicCoin", value = "需打流水", example = "77.7")
    private BigDecimal mosaicCoin = BigDecimal.ZERO;
    @ApiModelProperty(name = "info", value = "活动扩展信息", example = "{}")
    private String info = "{}";
}
