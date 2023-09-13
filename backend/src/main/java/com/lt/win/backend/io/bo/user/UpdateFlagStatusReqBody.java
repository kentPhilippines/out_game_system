package com.lt.win.backend.io.bo.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 会员管理-会员旗管理-启用或禁用 -请求body
 * </p>
 *
 * @author andy
 * @since 2020/3/17
 */
@Data
public class UpdateFlagStatusReqBody {
    @NotNull(message = "id不能为空")
    @ApiModelProperty(required = true, name = "id", value = "主键Id", example = "1")
    private Integer id;

    @NotNull(message = "status不能为空")
    @ApiModelProperty(required = true, name = "status", value = "状态: 1-启用 0-禁用", example = "0")
    private Integer status;
}
