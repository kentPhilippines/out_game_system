package com.lt.win.apiend.io.dto.wallet;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * <p>
 * 钱包-卡片管理-资产密码设置 请求body
 * </p>
 *
 * @author andy
 * @since 2020/4/8
 */
@Data
public class PasswordCoinSetReqBody {
    @NotEmpty(message = "passwordCoin不能为空")
    @ApiModelProperty(name = "passwordCoin", value = "资产密码", required = true)
    private String passwordCoin;
}
