package com.lt.win.apiend.io.dto.platform;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author: David
 * @date: 06/04/2020
 * @description:
 */
@Data
public class SlotGameFavoriteReqDto {
    @NotNull
    @ApiModelProperty(name = "gameId", value = "游戏类型ID", example = "101")
    private Integer gameId;

    @NotNull
    @ApiModelProperty(name = "gameSlotId", value = "游戏ID", example = "af4e5644-c016-4a3f-8623-4fef7c58938a")
    private String gameSlotId;

    @NotNull
    @ApiModelProperty(name = "direction", value = "类型:0-取消收藏 1-添加收藏", example = "1")
    private Integer direction;
}
