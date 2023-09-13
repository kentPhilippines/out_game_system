package com.lt.win.backend.io.bo.user;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 会员列表-银行卡信息-用户银行卡列表
 * </p>
 *
 * @author andy
 * @since 2020/4/8
 */
@Data
public class UserBankList {
    @ApiModelProperty(name = "id", value = "用户银行卡Id", example = "1")
    private Integer id;
    @ApiModelProperty(name = "status", value = "卡片状态:1-启用(默认) 2-启用 3-停用 4-删除", example = "1")
    private Integer status;
    @ApiModelProperty(name = "createdAt", value = "添加时间", example = "1")
    private Integer createdAt;

    @ApiModelProperty(name = "accountName", value = "持卡人", example = "王老五")
    private String accountName;

    @ApiModelProperty(name = "bankAccount", value = "银行卡号", example = "622513568889088888")
    private String bankAccount;

    @ApiModelProperty(name = "bankId", value = "银行Id", example = "5")
    private Integer bankId;

    @ApiModelProperty(name = "name", value = "银行名称", example = "中国银行")
    private String name;

    @ApiModelProperty(name = "address", value = "开户行地址", example = "香港九龙湾支行")
    @TableField("mark")
    private String address;

    @ApiModelProperty(name = "uid", value = "UID", example = "49")
    private Integer uid;

    @ApiModelProperty(name = "username", value = "用户名", example = "李欣生")
    private String username;
}
