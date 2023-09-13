package com.lt.win.service.io.dto.promotions;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author: wells
 * @date: 2020/4/7
 * @description:
 */
@Data
@ApiModel(value = "PromotionsGroupResDto", description = "优惠活动列表响应实体")
public class PromotionsGroupResDto {
    @ApiModelProperty(name = "id", value = "ID", example = "1")
    private Integer id;
    @ApiModelProperty(name = "codeZh", value = "名称", example = "首充优惠")
    private String codeZh;
    @ApiModelProperty(name = "promotionsResDtoList", value = "优惠活动详情列表")
    private List<PromotionsResDto> promotionsResDtoList;

}

