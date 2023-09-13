package com.lt.win.apiend.io.dto.wallet;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * <p>
 * 钱包-充值-添加充值记录 请求body
 * </p>
 *
 * @author andy
 * @since 2020/4/8
 */
@Data
public class DepositAddReqBody {

    @NotNull(message = "金额不能为空")
    @ApiModelProperty(name = "coin", value = "充值金额", required = true, example = "100.00")
    private BigDecimal coin;

    @NotNull(message = "支付类型不能为空")
    @ApiModelProperty(name = "payType", value = "支付类型:0-离线 1-在线", required = true)
    private Integer payType;

    @NotNull(message = "支付ID不能为空")
    @ApiModelProperty(name = "payRefer", value = "支付Id", required = true)
    private Integer payRefer;

    @NotEmpty(message = "打款人姓名不能为空")
    @ApiModelProperty(name = "depRealname", value = "打款人姓名", required = true)
    private String depRealname;

}
