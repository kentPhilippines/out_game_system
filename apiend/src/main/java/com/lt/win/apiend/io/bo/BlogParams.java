package com.lt.win.apiend.io.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author: David
 */
public interface BlogParams {
    /**
     * 博客查询
     */
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    class ListReqDto {
        @ApiModelProperty(name = "category", value = "Blog分组类型")
        private String category;
    }

    /**
     * 博客查询
     */
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    class BlogRecommendsReqDto {
        @NotNull(message = "Can't empty")
        @ApiModelProperty(name = "nums", value = "Blog分组类型", required = true)
        private Integer nums;
    }

    /**
     * 博客查询
     */
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    class BlogSearchReqDto {
        @NotNull(message = "Can't empty")
        @ApiModelProperty(name = "title", value = "Blog名称", required = true)
        private String title;
    }
}
