package com.lt.win.service.io.dto.promotions;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: wells
 * @date: 2020/5/4
 * @description:
 */
@Data
@ApiModel(value = "EnvelopeDateResDto", description = "优惠活动领取红包时间响应实体")
public class EnvelopeDateResDto {
    @ApiModelProperty(name = "startTime", value = "开始时间")
    private Integer startTime = 0;
    @ApiModelProperty(name = "endTime", value = "结束时间")
    private Integer endTime = 0;
    @ApiModelProperty(name = "status", value = "状态->0:关闭,1:开启")
    private Integer status = 0;
}
