package com.lt.win.backend.io.bo.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 会员旗管理-获取用户会员旗 -请求body
 * </p>
 *
 * @author andy
 * @since 2020/4/14
 */
@Data
public class ListUserFlagReqBody {
    @NotNull(message = "uid不能为空")
    @ApiModelProperty(name = "uid", value = "UID", example = "6")
    private Integer uid;
}
