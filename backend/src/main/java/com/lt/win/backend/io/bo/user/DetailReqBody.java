package com.lt.win.backend.io.bo.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 会员详情-请求
 * </p>
 *
 * @author andy
 * @since 2020/3/13
 */
@Data
public class DetailReqBody {
    @NotNull(message = "uid不能为空")
    @ApiModelProperty(required = true, name = "id", value = "id", example = "1")
    private Integer id;
}
