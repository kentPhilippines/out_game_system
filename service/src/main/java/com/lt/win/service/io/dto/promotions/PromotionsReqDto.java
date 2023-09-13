package com.lt.win.service.io.dto.promotions;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: wells
 * @date: 2020/4/7
 * @description:
 */
@Data
@ApiModel(value = "PromotionsReqDto", description = "优惠活动列表请求实体")
public class PromotionsReqDto {
    @ApiModelProperty(name = "categoty", value = "类型", example = "1")
    private Integer categoty;
    @ApiModelProperty(name = "subCategoty", value = "子类型", example = "1")
    private Integer subCategoty;
}
