package com.lt.win.service.io.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author : Wells
 * @Date : 2020-12-15 12:53 上午
 * @Description : xx
 */
public interface AuditBaseParams {
    @Data
    @ApiModel(value = "AuditResDto", description = "稽核请求实体类")
    class AuditReqDto {
        @ApiModelProperty(name = "id", value = "提款订单号", example = "79681096069025792")
        private Long id;
        @ApiModelProperty(name = "uid", value = "uid", example = "1")
        private Integer uid;
    }
}
