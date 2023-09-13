package com.lt.win.backend.io.bo.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 会员旗管理-已被使用的会员旗列表-会员旗信息
 * </p>
 *
 * @author andy
 * @since 2020/4/16
 */
@Data
public class ListFlagUsedPO {
    @ApiModelProperty(name = "uid", value = "UID")
    private Integer uid;
    @ApiModelProperty(name = "username", value = "用户名")
    private String username;

    @ApiModelProperty(name = "icon", value = "图标", example = "icon-ordinary_vip-5")
    private String icon;

    @ApiModelProperty(name = "iconColor", value = "图标颜色", example = "#A2BF8B")
    private String iconColor;

    @ApiModelProperty(name = "bitCode", value = "会员旗", example = "512")
    private String bitCode;

    @ApiModelProperty(name = "name", value = "会员旗名称", example = "VIP会员")
    private String name;
}
