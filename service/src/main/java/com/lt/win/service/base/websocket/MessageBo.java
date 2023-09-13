package com.lt.win.service.base.websocket;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: andy
 * @date: 2020/10/21
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageBo {

    @ApiModelProperty(name = "E", value = "E(设备) : D-web端   B-后台")
    private String E;

    @ApiModelProperty(name = "action", value = "action(标识) : DN-存款 WN-提款 ON-在线人数")
    private String action;

    @ApiModelProperty(name = "N", value = "N : 人数")
    private Integer N;
}
