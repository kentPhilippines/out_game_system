package com.lt.win.apiend.io.dto.platform;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author: David
 * @date: 06/04/2020
 * @description:
 */
@Data
@Builder
public class GameLoginReqDto {
    @NotNull
    @ApiModelProperty(name = "id", value = "游戏ID", example = "101")
    private Integer id;

    @ApiModelProperty(name = "slotId", value = "老虎机游戏ID(电子类填写)", example = "af4e5644-c016-4a3f-8623-4fef7c58938a")
    private String slotId;
}
