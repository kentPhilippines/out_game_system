package com.lt.win.service.io.dto.promotions;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author: wells
 * @date: 2020/4/7
 * @description:
 */
@Data
@ApiModel(value = "PromotionsInfoResDto", description = "优惠活动详情请求实体")
public class PromotionsInfoResDto {
    @ApiModelProperty(name = "codeZh", value = "中文名称")
    private String codeZh;
    @ApiModelProperty(name = "applicaCoin", value = "申请金额", example = "99.9")
    private BigDecimal applicaCoin = BigDecimal.ZERO;
    @ApiModelProperty(name = "availableCoin", value = "可得彩金", example = "88.8")
    private BigDecimal availableCoin = BigDecimal.ZERO;
    @ApiModelProperty(name = "mosaicCoin", value = "需打流水", example = "77.7")
    private BigDecimal mosaicCoin = BigDecimal.ZERO;
    @ApiModelProperty(name = "description", value = "活动描述信息")
    private String description = "";
}
