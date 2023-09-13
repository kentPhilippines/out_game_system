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
@ApiModel(value = "LuxuriousGiftResDto", description = "优惠活动豪礼列表响应实体")
public class LuxuriousGiftResDto {
    @ApiModelProperty(name = "id", value = "ID", example = "1")
    private Integer id;
    @ApiModelProperty(name = "name", value = "礼物名称", example = "Airpods")
    private String name;
    @ApiModelProperty(name = "required", value = "申请条件(等级、投注金额)", example = "20000")
    private Integer required;
    @ApiModelProperty(name = "imgZh", value = "简体图片")
    private String imgZh;
    @ApiModelProperty(name = "imgTw", value = "繁体图片")
    private String imgTw;
    @ApiModelProperty(name = "isRecive", value = "是否领取-》0:否,1:是", example = "1")
    private Integer isRecive;
}