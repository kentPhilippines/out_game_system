package com.lt.win.service.io.dto.promotions;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: wells
 * @date: 2020/5/8
 * @description:
 */
@Data
@ApiModel(value = "LuxuriousGiftListReqDto", description = "优惠活动豪礼列表请求实体")
public class LuxuriousGiftListReqDto {
    @ApiModelProperty(name = "category", value = "类型-》类型:1-vip升级奖励 2-投注豪礼", example = "1")
    private Integer category;
}
