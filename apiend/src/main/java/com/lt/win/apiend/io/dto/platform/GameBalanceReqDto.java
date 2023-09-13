package com.lt.win.apiend.io.dto.platform;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author: David
 * @date: 13/04/2020
 * @description:
 */
@Data
public class GameBalanceReqDto {
    @NotNull
    @ApiModelProperty(name = "id", value = "游戏ID", example = "1")
    private Integer id;
}
