package com.lt.win.apiend.io.dto.mapper;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: David
 * @date: 17/04/2020
 * @description:
 */
@Data
public class BannerResDto {
    @ApiModelProperty(name = "id", value = "ID", example = "11")
    private Integer id;

    @ApiModelProperty(name = "img", value = "图片地址", example = "https://static.xinbosports.com/group1/M03/00/04/wKh2AV-AYCeAAEHHAAnnHOXEpvE085.jpg")
    private String img;

    @ApiModelProperty(name = "href", value = "跳转链接", example = "https://www.taobao.com")
    private String href;
}
