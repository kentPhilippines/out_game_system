package com.lt.win.apiend.io.dto.platform;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: David
 * @date: 13/04/2020
 * @description:
 */
@Data
public class GameListDto {
    @ApiModelProperty(name = "id", value = "游戏ID", example = "1")
    private Integer id;

    @ApiModelProperty(name = "name", value = "游戏名称", example = "SBO体育")
    private String name;

    @ApiModelProperty(name = "status", value = "游戏状态", example = "1-启动，2-维护")
    private Integer status;

    @ApiModelProperty(name = "maintenance", value = "配置:维护信息:info-信息 start-开始时间 end-结束时间", example = "{\"info\":\"\",\"start\":\"1594118396\",\"end\":\"1594118396\"}")
    private JSONObject maintenance;

    @ApiModelProperty(name = "supportIframe", value = "", example = "是否支持Iframe")
    private Boolean supportIframe;
}
