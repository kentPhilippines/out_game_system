package com.lt.win.backend.io.bo.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 会员旗管理-下拉列表 -响应body
 * </p>
 *
 * @author andy
 * @since 2020/3/17
 */
@Data
public class UserFlagDict implements Serializable {

    @ApiModelProperty(name = "name", value = "会员旗名称")
    private String name;

    @ApiModelProperty(name = "bitCode", value = "会员旗code")
    private Integer bitCode;
}
