package com.lt.win.backend.io.bo.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * <p>
 * 会员管理-添加会员-请求body
 * </p>
 *
 * @author andy
 * @since 2020/3/12
 */
@Data
public class AddUserReqBody {
    @NotBlank(message = "username不能为空")
    @ApiModelProperty(required = true, name = "username", value = "用户名", example = "username123")
    @Pattern(regexp = "^(?=.*[a-zA-Z])[a-zA-Z\\d]{6,10}$", message = "6到10位字母、数字组成")
    private String username;
    @NotBlank(message = "passwordHash不能为空")
    @ApiModelProperty(required = true, name = "passwordHash", value = "登录密码", example = "1234556678")
    private String passwordHash;
    @ApiModelProperty(required = true, name = "supUsername", value = "上级代理", example = "david2020")
    private String supUsername;
    @NotNull(message = "role不能为空")
    @ApiModelProperty(required = true, name = "role", value = "账号类型:0-会员 1-代理", example = "0")
    private Integer role;
    @NotNull(message = "email不能为空")
    @ApiModelProperty(required = true, name = "email", value = "邮箱地址", example = "0")
    private String email;
    @ApiModelProperty(name = "levelId", value = "会员等级", example = "0")
    private String levelId;
}
