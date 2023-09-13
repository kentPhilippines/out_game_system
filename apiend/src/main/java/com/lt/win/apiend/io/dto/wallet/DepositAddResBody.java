package com.lt.win.apiend.io.dto.wallet;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>
 * 钱包-充值-添加充值记录-充值成功 响应body
 * </p>
 *
 * @author andy
 * @since 2020/4/15
 */
@Data
public class DepositAddResBody {

    @ApiModelProperty(name = "category", value = "充值方式: 1-银行卡 2-微信 3-支付宝")
    private Integer category;

    @ApiModelProperty(name = "coin", value = "充值金额", example = "100.00")
    private BigDecimal coin;

    @ApiModelProperty(name = "orderId", value = "订单号", example = "202003221900567338")
    private Long orderId;

    @ApiModelProperty(name = "createdAt", value = "提交时间", example = "1586617634")
    private Integer createdAt;

    @ApiModelProperty(name = "bankName", value = "收款银行")
    private String bankName;

    @ApiModelProperty(name = "accountName", value = "收款人")
    private String accountName;

    @ApiModelProperty(name = "bankAccount", value = "收款卡号")
    private String bankAccount;

    @ApiModelProperty(name = "depRealname", value = "打款人姓名", required = true)
    private String depRealname;

}
