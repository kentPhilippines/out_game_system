package com.lt.win.backend.io.bo.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 会员管理-会员旗管理-列表查询 -响应body
 * </p>
 *
 * @author andy
 * @since 2020/3/17
 */
@Data
public class ListFlagResBody implements Serializable {

    @ApiModelProperty(name = "id", value = "主键Id", example = "1")
    private Integer id;

    @ApiModelProperty(name = "name", value = "会员旗名称", example = "关注会员")
    private String name;

    @ApiModelProperty(name = "icon", value = "图标", example = "icon-ordinary_vip-5")
    private String icon;

    @ApiModelProperty(name = "iconColor", value = "图标颜色", example = "#A2BF8B")
    private String iconColor;

    @ApiModelProperty(name = "status", value = "状态: 1-启用 0-禁用", example = "0")
    private Integer status;

    @ApiModelProperty(name = "userCount", value = "人数", example = "20")
    private Integer userCount;

    @ApiModelProperty(name = "bitCode", value = "位运算占位符值", example = "512")
    private Integer bitCode;
}
