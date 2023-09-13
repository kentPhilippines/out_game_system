package com.lt.win.backend.io.bo.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 会员管理-会员旗管理-修改会员旗 -请求body
 * </p>
 *
 * @author andy
 * @since 2020/3/17
 */
@Data
public class UpdateFlagReqBody {

    @NotNull(message = "id不能为空")
    @ApiModelProperty(name = "id", value = "主键Id", example = "1", required = true)
    private Integer id;

    @NotBlank(message = "name不能为空")
    @ApiModelProperty(name = "name", value = "会员旗名称", example = "关注会员", required = true)
    private String name;

    @NotBlank(message = "icon不能为空")
    @ApiModelProperty(name = "icon", value = "图标", example = "icon-ordinary_vip-5", required = true)
    private String icon;

    @NotBlank(message = "iconColor不能为空")
    @ApiModelProperty(name = "iconColor", value = "图标颜色", example = "#A2BF8B", required = true)
    private String iconColor;

}
