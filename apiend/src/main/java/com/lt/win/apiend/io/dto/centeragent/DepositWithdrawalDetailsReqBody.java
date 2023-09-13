package com.lt.win.apiend.io.dto.centeragent;

import com.lt.win.apiend.io.dto.StartEndTime;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 代理中心-我的报表-充值与提现详情ReqBody
 * /p>
 *
 * @author andy
 * @since 2020/7/24
 */
@Data
public class DepositWithdrawalDetailsReqBody extends StartEndTime {

    @ApiModelProperty(name = "username", value = "用户名称", example = "andy")
    private String username;

    @NotNull(message = "类型不能为空")
    @ApiModelProperty(name = "category", value = "类型:1-存款 2-提款", example = "1")
    private Integer category;
}
