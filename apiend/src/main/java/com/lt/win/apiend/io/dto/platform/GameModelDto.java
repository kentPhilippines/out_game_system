package com.lt.win.apiend.io.dto.platform;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: David
 * @date: 06/04/2020
 * @description:
 */
@Data
public class GameModelDto  {
    @ApiModelProperty(name = "gameModel", value = "游戏Model", example = "SBOSports")
    private String gameModel;

    @ApiModelProperty(name = "platModel", value = "平台Model", example = "SBO")
    private String platModel;

    @ApiModelProperty(name = "gameStatus", value = "游戏状态:1-开启 0-关闭", example = "1")
    private Integer gameStatus;

    @ApiModelProperty(name = "platStatus", value = "平台状态:1-开启 0-关闭", example = "1")
    private Integer platStatus;

    @ApiModelProperty(name = "groupId", value = "游戏组ID", example = "1")
    private Integer groupId;

    @ApiModelProperty(name = "config", value = "平台配置信息", example = "1")
    private String config;


}
