package com.lt.win.apiend.io.dto.centeragent;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 代理中心-充值提现 请求
 * </p>
 *
 * @author andy
 * @since 2020/10/20
 */
@Data
public class DepositOrWithdrawalReqBody {
    @NotNull(message = "开始时间不能为空")
    @ApiModelProperty(name = "startTime", value = "开始时间", example = "1594437694", required = true)
    private Integer startTime;

    @NotNull(message = "结束时间不能为空")
    @ApiModelProperty(name = "endTime", value = "结束时间", example = "1594437694", required = true)
    private Integer endTime;

    @ApiModelProperty(name = "username", value = "用户名称", example = "andy")
    private String username;

    @NotNull(message = "类型不能为空")
    @ApiModelProperty(name = "category", value = "类型:1-充值 2-提现", example = "1", required = true)
    private Integer category;
}
