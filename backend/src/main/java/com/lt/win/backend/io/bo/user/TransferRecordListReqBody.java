package com.lt.win.backend.io.bo.user;

import com.lt.win.backend.io.bo.StartEndTime;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 转账记录-列表查询 请求body
 * </p>
 *
 * @author andy
 * @since 2020/3/19
 */
@Data
public class TransferRecordListReqBody extends StartEndTime {
    @ApiModelProperty(name = "orderId", value = "订单ID")
    private String orderId;

    @ApiModelProperty(name = "uid", value = "UID")
    private Integer uid;

    @ApiModelProperty(name = "category", value = "交易类型:0-上分 1-下分")
    private Integer category;

    @ApiModelProperty(name = "status", value = "状态: 0-转账中  1-转账失败 2-上分成功未确认 3-上分成功已确认")
    private Integer status;
}
