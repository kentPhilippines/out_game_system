package com.lt.win.apiend.io.dto.platform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * 游戏启动参数
 *
 * @author fangzs
 * @date 2022/8/12 19:52
 */
@Data
@ApiModel("游戏启动参数")
public class GameStartBo {

    @NotNull(message ="id not null")
    @ApiModelProperty(name = "id", value = "游戏id", example = "1041",required = true)
    private String id;

    @NotNull(message ="platId not null")
    @ApiModelProperty(name = "platId", value = "游戏平台id", example = "25",required = true)
    private Integer platId;

    @NotNull(message ="playMode not null")
    @Range(min = 1,max = 2,message = "playMode can only be 1 and 2")
    @ApiModelProperty(name = "playMode", value = "游戏模式 Mode of the game: 1) Real 2) Demo", example = "1",required = true)
    private Integer playMode;
}
