package com.lt.win.backend.io.bo.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 会员列表-分页查询-上级代理 请求body
 * </p>
 *
 * @author andy
 * @since 2020/4/10
 */
@Data
public class SupProxyListReqBody {

    @ApiModelProperty(name = "uid", value = "UID")
    private Integer uid;

    @ApiModelProperty(name = "username", value = "用户名")
    private String username;
}
