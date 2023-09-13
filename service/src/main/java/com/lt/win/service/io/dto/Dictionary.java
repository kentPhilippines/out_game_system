package com.lt.win.service.io.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 字典管理
 * </p>
 *
 * @author andy
 * @since 2020/6/8
 */
public interface Dictionary {

    /**
     * 请求体
     */
    @Data
    class ReqDto {
        @ApiModelProperty(name = "category", value = "字典组名称:不传-获取所有字典数据,传入字典名称-获取该组的字典列表", example = "dic_user_role")
        private String category;
    }

    /**
     * 响应体
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    class ResDto {
        @ApiModelProperty(name = "code", value = "字典码")
        private String code;
        @ApiModelProperty(name = "title", value = "字典名称")
        private String title;
    }
}
