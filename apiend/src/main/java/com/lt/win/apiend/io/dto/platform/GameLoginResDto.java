package com.lt.win.apiend.io.dto.platform;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: David
 * @date: 06/04/2020
 * @description:
 */
@Data
public class GameLoginResDto {
    @ApiModelProperty(name = "type", value = "链接类型:1-直接跳转", example = "1")
    private int type;

    @ApiModelProperty(name = "url", value = "登录链接", example = "https://www.baidu.com")
    private String url;
}
