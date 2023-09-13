package com.lt.win.apiend.io.dto.wallet;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>
 * 钱包-转账-转账记录 响应body
 * </p>
 *
 * @author andy
 * @since 2020/4/8
 */
@Data
public class TransferListResBody {

    @ApiModelProperty(name = "createdAt", value = "时间", example = "1586334785")
    private Integer createdAt;

    @ApiModelProperty(name = "status", value = "状态:0-提交申请 1-成功 2-失败", example = "0")
    private Integer status;

    @ApiModelProperty(name = "category", value = "类型:0-转出(主->第三方) 1-转入(第三方->主)", example = "1")
    private Integer category;

    @ApiModelProperty(name = "coin", value = "金额", example = "100.00")
    private BigDecimal coin;

    @ApiModelProperty(name = "method", value = "转账方式", example = "主账户->泛亚电竞")
    private String method;
}
