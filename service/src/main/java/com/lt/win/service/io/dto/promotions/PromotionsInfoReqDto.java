package com.lt.win.service.io.dto.promotions;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author: wells
 * @date: 2020/4/16
 * @description:
 */
@Data
@ApiModel(value = "PromotionsInfoReDto", description = "优惠活动详情请求实体")
public class PromotionsInfoReqDto {
    @NotNull(message = "ID不能为空！")
    @ApiModelProperty(name = "id", value = "ID", example = "1")
    private Integer id;
}
