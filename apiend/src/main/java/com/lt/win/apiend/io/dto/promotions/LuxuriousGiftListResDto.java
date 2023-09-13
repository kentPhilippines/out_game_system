package com.lt.win.apiend.io.dto.promotions;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author: wells
 * @date: 2020/5/8
 * @description:
 */
@Data
@ApiModel(value = "LuxuriousGiftListResDto", description = "优惠活动豪礼响应实体")
public class LuxuriousGiftListResDto {
    @ApiModelProperty(name = "areadyCoin", value = "已完成金额", example = "100")
    private String areadyCoin;
    @ApiModelProperty(name = "requireCoin", value = "申请金额", example = "1000")
    private String requiredCoin;
    @ApiModelProperty(name = "LuxuriousGifList", value = "豪礼列表")
    List<LuxuriousGiftResDto> LuxuriousGifList;
}