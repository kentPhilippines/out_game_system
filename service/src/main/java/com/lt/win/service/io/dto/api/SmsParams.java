package com.lt.win.service.io.dto.api;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: David
 * @date: 07/07/2020
 */
public interface SmsParams {
    /**
     * Header 信息
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    class SmsSendDto {
        @ApiModelProperty(name = "areaCode", value = "区号")
        public String areaCode;
        @ApiModelProperty(name = "mobile", value = "手机号码")
        public String mobile;
        @ApiModelProperty(name = "code", value = "发送码", example = "9999")
        public Long code;
        @ApiModelProperty(name = "谷歌验证码", value = "谷歌验证码", example = "******")
        private Long googleCode;
        @ApiModelProperty(name = "createAt", value = "创建时间")
        public Long createAt;
    }
}
