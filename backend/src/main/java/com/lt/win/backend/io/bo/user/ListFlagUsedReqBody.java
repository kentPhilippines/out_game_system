package com.lt.win.backend.io.bo.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 会员旗管理-已被使用的会员旗列表-请求body
 * </p>
 *
 * @author andy
 * @since 2020/3/25
 */
@Data
public class ListFlagUsedReqBody {

    @ApiModelProperty(name = "uid", value = "UID")
    private Integer uid;

    @ApiModelProperty(name = "username", value = "用户名")
    private String username;

    @ApiModelProperty(name = "bitCode", value = "会员旗", example = "512")
    private Integer bitCode;
}
