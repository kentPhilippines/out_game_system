package com.lt.win.service.io.dto.promotions;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: wells
 * @date: 2020/5/8
 * @description:
 */
@Data
@ApiModel(value = "LuxuriousGiftReceiveReqDto", description = "优惠活动豪礼领取请求实体")
public class LuxuriousGiftReceiveReqDto {
    @ApiModelProperty(name = "giftId", value = "ID", example = "1")
    private Integer giftId;
    @ApiModelProperty(name = "mark", value = "备注为JSONObject对象,字段number，addressId，email:,mark:,username")
    private JSONObject mark;
}
