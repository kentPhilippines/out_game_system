package com.lt.win.apiend.io.dto.wallet;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 用户银行卡列表
 * </p>
 *
 * @author andy
 * @since 2020/4/8
 */
@Data
public class UserBankList {
    @ApiModelProperty(name = "icon", value = "银行logo")
    private String icon;
    @ApiModelProperty(name = "name", value = "银行名称(中文)", example = "中国银行")
    private String name;
    @ApiModelProperty(name = "bankId", value = "银行Id", example = "2")
    private Integer bankId;
    @ApiModelProperty(name = "bankAccount", value = "银行卡号", example = "6225 ******** 8888")
    private String bankAccount;
    @ApiModelProperty(name = "accountName", value = "持卡人姓名", example = "张三")
    private String accountName;
    @ApiModelProperty(name = "id", value = "用户银行卡Id", example = "1")
    private Integer id;
    @ApiModelProperty(name = "status", value = "状态:1-启用(默认) 2-启用 3-停用 4-删除", example = "1")
    private Integer status;
}
