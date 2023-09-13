package com.lt.win.service.io.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: David
 * @date: 06/04/2020
 * @description:
 */
@Data
public class UserInfo {
    @ApiModelProperty(name = "id", value = "UID", example = "1")
    private Integer id;
    @ApiModelProperty(name = "username", value = "用户名")
    private String username;
    @ApiModelProperty(name = "avatar", value = "头像")
    private String avatar;
    @ApiModelProperty(name = "mainCurrency", value = "主货币")
    private String mainCurrency;
    @ApiModelProperty(name = "coin", value = "余额")
    private BigDecimal coin;
    @ApiModelProperty(name = "fcoin", value = "冻结资金")
    private BigDecimal fcoin;
    @ApiModelProperty(name = "coinCommission", value = "佣金可提现金额")
    private BigDecimal coinCommission;
    @ApiModelProperty(name = "role", value = "用户角色")
    private Integer role;
    @ApiModelProperty(name = "levelId", value = "等级ID")
    private Integer levelId;
    @ApiModelProperty(name = "realName", value = "真实姓名")
    private String realName;
    @ApiModelProperty(name = "signature", value = "个性签名")
    private String signature;
    @ApiModelProperty(name = "birthday", value = "生日")
    private Date birthday;
    @ApiModelProperty(name = "status", value = "状态")
    private Integer status;
    @ApiModelProperty(name = "areaCode", value = "区号")
    private String areaCode;
    @ApiModelProperty(name = "mobile", value = "手机号码")
    private String mobile;
    @ApiModelProperty(name = "email", value = "邮箱")
    private String email;
    @ApiModelProperty(name = "sex", value = "性别:1-男 0-女 2-未知")
    private Integer sex;
    @ApiModelProperty(name = "bindBank", value = "是否绑定银行卡:1-已绑定 0-未绑定")
    private Integer bindBank;
    @ApiModelProperty(name = "score", value = "积分")
    private Integer score;
    @ApiModelProperty(name = "promo_code", value = "推广码")
    private Integer promoCode;
    @ApiModelProperty(name = "api_token", value = "ApiToken")
    private String apiToken;
    @ApiModelProperty(name = "passwordHash", value = "Hash")
    private String passwordHash;
    @ApiModelProperty(name = "passwordCoin", value = "Hash")
    private String passwordCoin;
    @ApiModelProperty(name = "created_at", value = "创建时间")
    private Integer createdAt;
}
