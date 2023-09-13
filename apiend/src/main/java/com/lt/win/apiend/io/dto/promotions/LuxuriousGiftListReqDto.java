package com.lt.win.apiend.io.dto.promotions;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author: wells
 * @date: 2020/5/8
 * @description:
 */
@Data
@ApiModel(value = "LuxuriousGiftListReqDto", description = "优惠活动豪礼列表请求实体")
public class LuxuriousGiftListReqDto {
    @NotNull(message = "类型不能为空")
    @ApiModelProperty(name = "category", value = "类型-》类型:1-vip升级奖励 2-投注豪礼", example = "1")
    private Integer category;
    @ApiModelProperty(name = "status", value = "状态-》0：全部，1-未领取（未达到条件，不能领取）2-待领取（达到条件，未去领） 3-已领取", example = "1")
    private Integer status;
}
