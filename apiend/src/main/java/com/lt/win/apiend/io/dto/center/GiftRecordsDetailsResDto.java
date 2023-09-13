package com.lt.win.apiend.io.dto.center;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 礼物记录详情 响应body
 * </p>
 *
 * @author andy
 * @since 2020/7/17
 */
@Data
public class GiftRecordsDetailsResDto {

    @ApiModelProperty(name = "giftName", value = "礼物名称", example = "1")
    private String giftName;

    @ApiModelProperty(name = "addr", value = "收获地址", example = "香港特别行政区|香港岛|中西区|安慰我说梵蒂冈")
    private String addr;

    @ApiModelProperty(name = "status", value = "状态:0-申请中 1-同意 2-拒绝 3-已发货 4-已送达", example = "1")
    private Integer status;

    @ApiModelProperty(name = "createdAt", value = "时间", example = "1587480634")
    private Integer createdAt;
}

