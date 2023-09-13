package com.lt.win.service.io.dto.promotions;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: wells
 * @date: 2020/5/2
 * @description:
 */
@Data
@ApiModel(value = "IsReceiveReqDto", description = "优惠活动红包雨请求实体")
public class IsReceiveReqDto {
    @ApiModelProperty(name = "id", value = "ID", example = "5")
    private Integer id;
}
