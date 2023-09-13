package com.lt.win.backend.io.bo.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 会员旗管理-新增用户会员旗
 * </p>
 *
 * @author andy
 * @since 2020/3/25
 */
@Data
public class AddUserFlagReqBody {
    @ApiModelProperty(name = "uid", value = "UID", required = true)
    @NotNull(message = "uid不能为空")
    private Integer uid;

    @ApiModelProperty(name = "bitCode", value = "会员旗", required = true)
    @NotNull(message = "bitCode不能为空")
    private Integer bitCode;
}
