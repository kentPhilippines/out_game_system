package com.lt.win.apiend.io.dto.wallet;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 非银行卡支付信息实体:微信和支付宝
 * </p>
 *
 * @author andy
 * @since 2020/4/17
 */
@Data
public class PayUnBank {
    @ApiModelProperty(name = "payeeQrcode", value = "收款二维码")
    private String payeeQrcode;

    @ApiModelProperty(name = "id", value = "ID")
    private Integer id;

    @ApiModelProperty(name = "minpay", value = "最低支付", example = "100")
    private Integer minpay;

    @ApiModelProperty(name = "maxpay", value = "最高支付", example = "10000")
    private Integer maxpay;

    @ApiModelProperty(name = "range", value = "区间可选金额", example = "[100, 500, 1000, 2000, 5000, 10000]")
    private int[] range;
}
