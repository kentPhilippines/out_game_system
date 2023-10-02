package com.lt.win.backend.io.bo.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;


/**
 * <p>
 * 会员管理-修改会员-请求body
 * </p>
 *
 * @author andy
 * @since 2020/3/12
 */
@Data
public class UpdateUserReqBody {
    @NotNull(message = "uid不能为空")
    @ApiModelProperty(name = "uid", value = "UID", example = "6", required = true)
    private Integer uid;

    @ApiModelProperty(name = "levelId", value = "用户等级", example = "1")
    private Integer levelId;

    @ApiModelProperty(name = "role", value = "用户类型:0-会员 1-代理", example = "0")
    private Integer role;

    @ApiModelProperty(name = "status", value = "账号状态:10-正常 9-冻结 8-永久冻结", example = "10")
    private Integer status;

    @ApiModelProperty(name = "mobile", value = "手机号", example = "19912345678")
    private String mobile;

    @ApiModelProperty(name = "sex", value = "性别:1-男 0-女 2-未知", example = "1")
    private Integer sex;

    @ApiModelProperty(name = "birthday", value = "出生年月", example = "2000-05-05")
    private String birthday;

    @ApiModelProperty(name = "realName", value = "真实姓名", example = "立夏")
    private String realName;

    @ApiModelProperty(name = "passwordHash", value = "登录密码", example = "1234556678")
    private String passwordHash;

    @ApiModelProperty(name = "passwordCoin", value = "资金密码", example = "1234556678")
    private String passwordCoin;

    @ApiModelProperty(name = "areaCode", value = "区号", example = "86")
    private String areaCode;

    @ApiModelProperty(name = "mark", value = "备注", example = "无效用户")
    private String mark;
}
