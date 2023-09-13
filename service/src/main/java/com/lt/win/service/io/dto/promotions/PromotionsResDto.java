package com.lt.win.service.io.dto.promotions;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: wells
 * @date: 2020/4/15
 * @description:
 */
@Data
@ApiModel(value = "PromotionsResDto", description = "优惠活动详情响应实体")
public class PromotionsResDto {
    @ApiModelProperty(name = "id", value = "ID", example = "1")
    private Long id;
    @ApiModelProperty(name = "category", value = "类型", example = "1")
    private Integer category;
    @ApiModelProperty(name = "startedAt", value = "开始时间", example = "1585991266")
    private Integer startedAt;
    @ApiModelProperty(name = "endedAt", value = "结束时间", example = "1585991266")
    private Integer endedAt;
}
