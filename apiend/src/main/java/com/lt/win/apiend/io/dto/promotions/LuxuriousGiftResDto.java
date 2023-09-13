package com.lt.win.apiend.io.dto.promotions;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: wells
 * @date: 2020/5/8
 * @description:
 */
@Data
@ApiModel(value = "LuxuriousGiftResDto", description = "优惠活动豪礼列表响应实体")
public class LuxuriousGiftResDto {
    @ApiModelProperty(name = "id", value = "ID", example = "1")
    private Integer id;
    @ApiModelProperty(name = "name", value = "礼物名称", example = "Airpods")
    private String name;
    @ApiModelProperty(name = "required", value = "申请条件(等级、投注金额)", example = "20000")
    private String required;
    @ApiModelProperty(name = "img", value = "简体图片")
    private String img;
    @ApiModelProperty(name = "receiveStatus", value = " 状态-》1-未领取（未达到条件，不能领取）2-待领取（达到条件，未去领） 3-已领取", example = "1")
    private Integer receiveStatus = 1;
    @ApiModelProperty(name = "levelId", value = "会员等级")
    private Integer levelId = 0;
}