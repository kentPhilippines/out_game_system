package com.lt.win.backend.io.bo.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 会员列表-银行卡信息-添加或修改用户银行卡
 * </p>
 *
 * @author andy
 * @since 2020/4/7
 */
@Data
public class BankAddReqBody {
    @ApiModelProperty(name = "accountName", value = "持卡人", example = "王老五")
    private String accountName;

    @ApiModelProperty(name = "bankAccount", value = "银行卡号", example = "622513568889088888")
    private String bankAccount;

    @ApiModelProperty(name = "bankId", value = "银行Id", example = "5")
    private Integer bankId;

    @ApiModelProperty(name = "address", value = "开户行地址", example = "香港九龙湾支行")
    private String address;

    @NotNull(message = "uid不能为空")
    @ApiModelProperty(name = "uid", value = "uid", example = "49")
    private Integer uid;

    @ApiModelProperty(name = "id", value = "主键ID:ID为空时新增，否则修改", example = "1")
    private Integer id;

    @ApiModelProperty(name = "status", value = "状态:1-启用(默认) 2-启用 3-停用 4-删除", example = "3")
    private Integer status;
}
