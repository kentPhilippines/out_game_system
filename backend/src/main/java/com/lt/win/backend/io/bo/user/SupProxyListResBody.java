package com.lt.win.backend.io.bo.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 会员列表-上级代理 响应body
 * </p>
 *
 * @author andy
 * @since 2020/4/10
 */
@Data
public class SupProxyListResBody {
    @ApiModelProperty(name = "supProxyList", value = "上级代理列表")
    private List<String> supProxyList = new ArrayList<>();
}
