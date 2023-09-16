package com.lt.win.apiend.io.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.*;

/**
 * @author: David
 * @date: 06/06/2020
 */
public interface UserParams {
    /**
     * 登录入参
     */
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    class LoginReqDto {
        @NotNull(message = "用户名|邮箱不能为空")
        @ApiModelProperty(name = "username", value = "用户名", example = "A2001")
        private String username;
        @NotNull(message = "密码不能为空")
        @Pattern(regexp = "^[A-Za-z\\d~'`!@#￥$%^&*()-+_=:|]{4,16}$", message = "4到16位英文、字母、特殊符号组成")
        @ApiModelProperty(name = "password", value = "密码", example = "******")
        private String password;
        @ApiModelProperty(name = "谷歌验证码", value = "谷歌验证码", example = "213212")
        private Long googleCode;
    }

    /**
     * 登录入参
     */
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    class LoginByMobileReqDto {
        @NotNull(message = "手机号码不能为空")
        @ApiModelProperty(name = "mobile", value = "手机号码", example = "6363456787")
        private String mobile;

        @NotNull(message = "手机验证码不能为空")
        @Pattern(regexp = "^\\d{4,8}$", message = "4-8位数字")
        @ApiModelProperty(name = "verificationCode", value = "手机验证码", example = "456312")
        private Integer verificationCode;
    }

    /**
     * 注册入参
     */
    @EqualsAndHashCode(callSuper = true)
    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    class CreateReqDto extends SendSmsCodeReqDto {
        /**
         * 三晟体育 (最小长度：8 最大长度：48 不区分大小写)
         * TCG min=4 max=14
         */
        @NotNull(message = "用户名不能为空")
        @Pattern(regexp = "^[\\da-zA-Z]{6,10}$", message = "6到10位字母、数字组成")
        @ApiModelProperty(name = "username", value = "用户名", example = "A2001")
        private String username;

        @NotNull(message = "密码不能为空")
        @Pattern(regexp = "^[A-Za-z\\d~'`!@#￥$%^&*()-+_=:|]{4,16}$", message = "4到16位英文、字母、特殊符号组成")
        @ApiModelProperty(name = "password", value = "密码", example = "******")
        private String password;


        @ApiModelProperty(name = "smsCode", value = "手机验证码", example = "8888")
        private Long smsCode;

        @ApiModelProperty(name = "promoCode", value = "推广码", example = "8888")
        private Integer promoCode;

        @ApiModelProperty(name = "link", value = "推广域名", example = "testwww.xinbosports.com", hidden = true)
        private String link;
    }

    /**
     * 用户注册
     */
    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    class RegisterReqDto {
        @NotNull(message = "用户名不能为空")
        @Pattern(regexp = "^(?=.*[a-zA-Z])[a-zA-Z\\d]{6,10}$", message = "6到10位字母、数字组成")
        @ApiModelProperty(name = "username", value = "用户名", example = "A2001")
        private String username;
        @NotNull(message = "密码不能为空")
        @Pattern(regexp = "^[A-Za-z\\d~'`!@#￥$%^&*()-+_=:|]{4,16}$", message = "4到16位英文、字母、特殊符号组成")
        @ApiModelProperty(name = "password", value = "密码", example = "******")
        private String password;
       // @NotNull(message = "邮箱不能为空")
        @Pattern(regexp = "^[A-Za-z\\d]+([_.][A-Za-z\\d]+)*@([A-Za-z\\d\\-]+\\.)+[A-Za-z]{2,6}$", message = "Email format incorrect")
        @ApiModelProperty(name = "email", value = "邮箱", example = "123@gmail.com")
        private String email;
     //   @NotNull(message = "区号不能为空")
        @ApiModelProperty(name = "areaCode", value = "区号", example = "86")
        private String areaCode;
     //   @NotNull(message = "手机号码不能为空")
        @ApiModelProperty(name = "mobile", value = "手机号码", example = "13812345678")
        private String mobile;
        @ApiModelProperty(name = "link", value = "推广域名", example = "testwww.xinbosports.com", hidden = true)
        private String link;
        @NotNull(message = "推广码不能为空")
        @ApiModelProperty(name = "promoCode", value = "推广码")
        private String promoCode;
        @ApiModelProperty(name = "verifyCode", value = "验证码", example = "5500")
        private String verifyCode;
    }


    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @ApiModel(value = "UpdateProfileReqDto", description = "更新用户返回实体类(头像需单独更新)")
    class UpdateProfileReqDto {
        @Pattern(regexp = "^[\\da-zA-Z_/.]{6,64}$", message = "6到64位字母、数组、下划线、'/'、'.'组成")
        @ApiModelProperty(name = "avatar", value = "头像", example = "/avatar/1.png")
        private String avatar;
        @Pattern(regexp = "^[\\u4e00-\\u9fa5_a-zA-Z]{2,32}", message = "2到32位中文、字母、下划线组成")
        @ApiModelProperty(name = "realName", value = "真实姓名", example = "李四")
        private String realName;
        @Pattern(regexp = "^(19|20)\\d{2}-(1[0-2]|0?[1-9])-(0?[1-9]|[1-2]\\d|3[0-1])$", message = "生日格式不正确")
        @ApiModelProperty(name = "birthday", value = "生日", example = "1990-01-01")
        private String birthday;
        @Pattern(regexp = "^[\\u4e00-\\u9fa5_a-zA-Z]{2,32}", message = "2到32位中文、字母、下划线组成")
        @ApiModelProperty(name = "signature", value = "个性签名", example = "壁立千仞")
        private String signature;
        @Min(value = 0)
        @Max(value = 2)
        @ApiModelProperty(name = "sex", value = "性别", example = "1")
        private Integer sex;
        @ApiModelProperty(name = "address", value = "家庭地址", example = "香港|XX|AA|#1202栋，8888房")
        private String address;
    }

    /**
     * 验证登录密码
     */
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    class ValidatePasswordHashReqDto {
        @Pattern(regexp = "^[A-Za-z\\d~'`!@#￥$%^&*()-+_=:|]{4,16}$", message = "4到16位英文、字母、特殊符号组成")
        @ApiModelProperty(name = "passwordHash", value = "资金密码", example = "123qwe")
        private String passwordHash;
    }

    /**
     * 验证资金密码
     */
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    class ValidatePasswordCoinReqDto {
        @Pattern(regexp = "^[A-Za-z\\d~'`!@#￥$%^&*()-+_=:|]{4,16}$", message = "4到16位英文、字母、特殊符号组成")
        @ApiModelProperty(name = "passwordCoin", value = "资金密码", example = "123qwe")
        private String passwordCoin;
    }

    /**
     * 插入或新增用户地址
     */
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    class InsertSaveAddressReqDto {
        @Min(value = 0, message = "最小值0")
        @ApiModelProperty(name = "id", value = "地址ID", example = "1")
        private Integer id;

        @NotEmpty(message = "地址不能为空")
        @ApiModelProperty(name = "address", value = "会员地址", example = "香港|XX|AA|#1202栋，8888房")
        private String address;

        @NotEmpty(message = "姓名不能为空")
        @ApiModelProperty(name = "name", value = "收件人姓名", example = "张三")
        private String name;

        @NotEmpty(message = "区号不能为空")
        @ApiModelProperty(name = "areaCode", value = "区号")
        private String areaCode;

        @NotEmpty(message = "手机号码不能为空")
        @ApiModelProperty(name = "mobile", value = "手机号码")
        private String mobile;
    }

    /**
     * 删除或设置默认用户地址
     */
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    class DeleteOrDefaultAddressReqDto {
        @Min(value = 0, message = "ID最小0")
        @ApiModelProperty(name = "id", value = "地址ID", example = "1")
        private Integer id;
    }

    /**
     * 发送验证码
     */
    @Data
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    class SendSmsCodeReqDto {
        @ApiModelProperty(name = "areaCode", value = "区号", example = "86")
        private String areaCode;
        @ApiModelProperty(name = "mobile", value = "手机号码", example = "123123")
        private String mobile;
    }

    @Data
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    class SendSesCodeReqDto {
        @NotNull(message = "Can't Empty")
        @ApiModelProperty(name = "email", value = "邮箱地址", example = "admin@gmail.com")
        private String email;
    }

    @Data
    class UpdateSaveAddressReqDto {
        @Min(value = 0, message = "ID最小0")
        @ApiModelProperty(name = "id", value = "地址ID", example = "1")
        private Integer id;

        @Pattern(regexp = "^[\\u4e00-\\u9fa5A-Za-z\\d~'`!@#￥$%^&*()-+_=:,，。、|]{4,255}$", message = "4到255位中英文、字母、特殊符号组成")
        @ApiModelProperty(name = "address", value = "会员地址", example = "香港|XX|AA|#1202栋，8888房")
        private String address;
    }

    /**
     * 用户地址返回实体
     */
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    class UserAddressResDto {
        @ApiModelProperty(name = "id", value = "ID", example = "8888")
        private Integer id;

        @ApiModelProperty(name = "name", value = "收件人姓名", example = "张三")
        private String name;

        @ApiModelProperty(name = "areaCode", value = "区号", example = "086")
        private String areaCode;

        @ApiModelProperty(name = "mobile", value = "手机号码", example = "123123")
        private String mobile;

        @ApiModelProperty(name = "address", value = "会员地址", example = "香港|XX|AA|#1202栋，8888房")
        private String address;

        @ApiModelProperty(name = "status", value = "状态:1-默认地址(启用) 2-正常启用 3-删除", example = "1")
        private Integer status;

        @ApiModelProperty(name = "createdAt", value = "创建时间", example = "1587371198")
        private Integer createdAt;
    }

    /**
     * 忘记密码入参
     */
    @EqualsAndHashCode(callSuper = true)
    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    class ForgotPasswordReqDto extends SendSesCodeReqDto {
        @NotNull(message = "密码不能为空")
        @Pattern(regexp = "^[A-Za-z\\d~'`!@#￥$%^&*()-+_=:|]{4,16}$", message = "4到16位英文、字母、特殊符号组成")
        @ApiModelProperty(name = "password", value = "密码", example = "******")
        private String password;

        @NotNull(message = "验证码不能为空")
        @Min(value = 1000, message = "验证码格式不正确")
        @Max(value = 99999999, message = "验证码格式不正确")
        @ApiModelProperty(name = "verifyCode", value = "8888", example = "8888")
        private String verifyCode;
    }

    /**
     * 重置密码入参
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    class ResetPasswordReqDto {
        @NotNull(message = "密码不能为空")
        @Pattern(regexp = "^[A-Za-z\\d~'`!@#￥$%^&*()-+_=:|]{4,16}$", message = "4到16位英文、字母、特殊符号组成")
        @ApiModelProperty(name = "password", value = "密码", example = "******")
        private String password;

        @NotNull(message = "密码不能为空")
        @Pattern(regexp = "^[A-Za-z\\d~'`!@#￥$%^&*()-+_=:|]{4,16}$", message = "4到16位英文、字母、特殊符号组成")
        @ApiModelProperty(name = "oldPassword", value = "旧密码", example = "******")
        private String oldPassword;

    }

    /**
     * 重置密码入参
     */
    @EqualsAndHashCode(callSuper = true)
    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    class ResetEmailReqDto extends SendSesCodeReqDto {
        @NotNull(message = "验证码不能为空")
        @Min(value = 1000, message = "验证码格式不正确")
        @Max(value = 99999999, message = "验证码格式不正确")
        @ApiModelProperty(name = "verifyCode", value = "8888", example = "8888")
        private String verifyCode;
    }

    /**
     * 修改手机号入参
     */
    @EqualsAndHashCode(callSuper = true)
    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    class ResetMobileReqDto extends SendSmsCodeReqDto {
        @NotNull(message = "验证码不能为空")
        @Min(value = 1000, message = "验证码格式不正确")
        @Max(value = 99999999, message = "验证码格式不正确")
        @ApiModelProperty(name = "smsCode", value = "手机验证码", example = "8888", hidden = true)
        private Long smsCode;
    }

    /**
     * 密码验证是否正确
     */
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    class ValidateResDto {
        @ApiModelProperty(name = "success", value = "状态:1-成功 0-失败", example = "1")
        private Integer success;
    }


    /**
     * 用户绑定谷歌秘钥
     */
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    class BindGoogleCodeDto {
        @NotNull(message = "谷歌秘钥不能为空")
        @ApiModelProperty(value = "谷歌秘钥")
        private String secret;

        @NotNull(message = "谷歌口令不能为空")
        @ApiModelProperty(value = "谷歌口令")
        private Long googleCode;
    }

    /**
     * 用户绑定谷歌秘钥
     */
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    class OpenOrCloseGoogleCodeDto {
        @ApiModelProperty(value = "开关谷歌验证码 1-开启 0-关闭）")
        @NotNull(message = "开关谷歌验证码状态不能为空")
        @Min(value = 0, message = "开关谷歌验证码参数错误")
        @Max(value = 1, message = "开关谷歌验证码参数错误")
        private Integer status;
    }

    /**
     * 用户检验是否开启谷歌验证码
     */
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    class CheckOpenGoogleCodeDto {
        @NotNull(message = "用户名不能为空")
        @ApiModelProperty(name = "username", value = "用户名", example = "A2001")
        private String username;
    }


}
