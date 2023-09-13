package com.lt.win.apiend.io.dto.wallet;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 钱包-卡片管理-添加银行卡 请求body
 * </p>
 *
 * @author andy
 * @since 2020/4/7
 */
@Data
public class BankAddReqBody {
    @ApiModelProperty(name = "accountName", value = "持卡人", example = "王老五", required = true)
    private String accountName;

    @NotEmpty(message = "银行卡号不能为空")
    @ApiModelProperty(name = "bankAccount", value = "银行卡号", example = "622513568889088888", required = true)
    private String bankAccount;

    @NotNull(message = "银行id不能为空")
    @ApiModelProperty(name = "bankId", value = "银行Id", example = "5", required = true)
    private Integer bankId;

    @NotEmpty(message = "开户行地址不能为空")
    @ApiModelProperty(name = "address", value = "开户行地址", example = "香港九龙湾支行", required = true)
    private String address;
}
