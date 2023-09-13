package com.lt.win.backend.io.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author : Wells
 * @Date : 2020-11-19 4:16 下午
 * @Description : xx
 */
public interface AgentDto {
    @Data
    @ApiModel(value = "AgentListResDto", description = "代理列表响应参数")
    class AgentListResDto {
        @ApiModelProperty(name = "agentId", value = "代理ID", example = "1")
        private Integer agentId;
        @ApiModelProperty(name = "agentName", value = "代理名称", example = "test001")
        private String agentName;
    }


}


