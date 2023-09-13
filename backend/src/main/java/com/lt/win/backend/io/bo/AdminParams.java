package com.lt.win.backend.io.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @Description: AdminController 入参、出参详细信息
 * @Author: David
 * @Date: 03/06/2020
 */
public interface AdminParams {
    /**
     * 登录或创建用户
     */
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @ApiModel(value = "LoginRequest", description = "管理员登录")
    class LoginRequest {
        @NotBlank(message = "用户名不能为空")
        @ApiModelProperty(required = true, name = "username", value = "用户名", example = "A2001")
        private String username;

        @NotBlank(message = "密码不能为空")
        @ApiModelProperty(required = true, name = "password", value = "密码", example = "******")
        private String password;

        @ApiModelProperty(required = true, name = "VerificationCode", value = "验证码", example = "673245")
        private String VerificationCode;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @ApiModel(value = "LoginResponse", description = "管理员登录")
    class LoginResponse {
        @ApiModelProperty(name = "id", value = "UID", example = "2001")
        private Integer id;

        @ApiModelProperty(name = "username", value = "用户名", example = "A2001")
        private String username;

        @ApiModelProperty(name = "role", value = "1-超级管理员，2-系统管理员，3-代理", example = "1")
        private Integer role;

        @ApiModelProperty(name = "adminGroupId", value = "用户组ID", example = "1")
        private Integer adminGroupId;

        @ApiModelProperty(name = "token", value = "ApiToken", example = "******")
        private String apiToken;

    }
}
