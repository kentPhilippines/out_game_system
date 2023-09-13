package com.lt.win.apiend.io.dto.platform;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: David
 * @date: 06/04/2020
 * @description:
 */
@Data
public class GameIndexReqDto {
    @ApiModelProperty(name = "id", value = "游戏ID", example = "A2001")
    private Integer id;
}
