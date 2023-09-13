package com.lt.win.backend.io.bo.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>
 * 转账记录-列表查询 响应body
 * </p>
 *
 * @author andy
 * @since 2020/3/19
 */
@Data
public class TransferRecordListResBody {

    @ApiModelProperty(name = "orderId", value = "订单ID")
    private String orderId;

    @ApiModelProperty(name = "uid", value = "UID")
    private Integer uid;

    @ApiModelProperty(name = "coin", value = "账变金额")
    private BigDecimal coin;

    @ApiModelProperty(name = "coinBefore", value = "用户余额")
    private BigDecimal coinBefore;

    @ApiModelProperty(name = "category", value = "类型:0-上分 1-下分")
    private Integer category;

    @ApiModelProperty(name = "updatedAt", value = "操作时间")
    private Integer updatedAt;

    @ApiModelProperty(name = "status", value = "状态: 0-转账中  1-转账失败 2-上分成功未确认 3-上分成功已确认")
    private Integer status;
}
