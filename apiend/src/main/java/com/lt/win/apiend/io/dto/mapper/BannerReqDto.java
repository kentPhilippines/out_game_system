package com.lt.win.apiend.io.dto.mapper;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: wells
 * @date: 2020/5/1
 * @description:
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "BannerReqDto", description = "Banner响应实体")
public class BannerReqDto {
    @ApiModelProperty(name = "category", value = "类型:1-首页 2-活动", example = "11")
    private Integer category;
}
