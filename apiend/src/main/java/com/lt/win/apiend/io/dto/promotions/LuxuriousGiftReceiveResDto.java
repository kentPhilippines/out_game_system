package com.lt.win.apiend.io.dto.promotions;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;


/**
 * @author: wells
 * @date: 2020/8/3
 * @description:
 */
@Data
@Builder
@ApiModel(value = "LuxuriousGiftReceiveResDto", description = "优惠活动豪礼领取响应实体")
public class LuxuriousGiftReceiveResDto {
    @ApiModelProperty(name = "giftName", value = "礼物名称")
    private String giftName;
    @ApiModelProperty(name = "receiveName", value = "收货人")
    private String receiveName;
    @ApiModelProperty(name = "receiveAddress", value = "收货地址")
    private String receiveAddress;
    @ApiModelProperty(name = "orderId", value = "订单号")
    private String orderId;
    @ApiModelProperty(name = "submitAt", value = "提交时间")
    private Integer submitAt;
}
