package com.lt.win.backend.io.bo.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 会员列表-路线转移
 * </p>
 *
 * @author andy
 * @since 2020/3/21
 */
@Data
public class RouteTransferReqBody {
    @ApiModelProperty(name = "usernameSource", value = "待转移路线", example = "usernameSource")
    @NotBlank(message = "usernameSource不能为空")
    private String usernameSource;

    @ApiModelProperty(name = "usernameTarget", value = "目标路线", example = "usernameTarget")
    @NotBlank(message = "usernameTarget不能为空")
    private String usernameTarget;
}
