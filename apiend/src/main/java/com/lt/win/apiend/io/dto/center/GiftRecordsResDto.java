package com.lt.win.apiend.io.dto.center;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: David
 * @date: 17/04/2020
 * @description:
 */
@Data
public class GiftRecordsResDto {
    @ApiModelProperty(name = "id", value = "ID", example = "1")
    private Long id;

    @ApiModelProperty(name = "postNo", value = "快递单号", example = "shanghai0123123123")
    private String postNo;

    @ApiModelProperty(name = "giftId", value = "礼品ID", example = "3")
    private Integer giftId;

    @ApiModelProperty(name = "giftName", value = "礼物名称", example = "1")
    private String giftName;

    @ApiModelProperty(name = "addr", value = "收获地址", example = "香港特别行政区|香港岛|中西区|安慰我说梵蒂冈")
    private String addr;

    @ApiModelProperty(name = "status", value = "状态:0-申请中 1-同意 2-拒绝 3-已发货 4-已送达", example = "1")
    private Integer status;

    @ApiModelProperty(name = "mark", value = "备注", example = "")
    private String mark;

    @ApiModelProperty(name = "createdAt", value = "创建时间", example = "1587480634")
    private Integer createdAt;
}

