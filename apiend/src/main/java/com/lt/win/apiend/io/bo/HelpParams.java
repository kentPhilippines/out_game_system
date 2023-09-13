package com.lt.win.apiend.io.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

/**
 * @author: piking
 */
public interface HelpParams {

    /**
     * 帮助类型分页
     */
//    @Data
//    @Builder
//    @AllArgsConstructor
//    @NoArgsConstructor
//    class HelpTypePageReqDto {
//
//    }

    /**
     * 帮助详情分页
     */
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    class HelpInfoPageReqDto {
        @ApiModelProperty(value = "帮助类型id")
        @NotNull(message = "帮助类型id不能为空")
        private String helpTypeId;
        @ApiModelProperty(value = "更新人")
        private String updateBy;
    }

    /**
     * 帮助详情查询
     */
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    class HelpInfoReqDto {
        @NotNull(message = "帮助详情id不能为空")
        @ApiModelProperty(value = "帮助详情id不能为空")
        private String id;

    }


    /**
     * 帮助类型返回
     */
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    class HelpTypeRespBody {
        @ApiModelProperty(value = "编号")
        private Integer id;
        @ApiModelProperty(value = "类型名称")
        private String typeName;
        @ApiModelProperty(value = "图片地址")
        private String imageUrl;
        @ApiModelProperty(value = "帮助详情列表")
        private List<HelpInfoRespBody> listHelpInfo;

    }

    /**
     * 帮助详情返回
     */
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    class HelpInfoRespBody {
        @ApiModelProperty(value = "主键编号")
        private Integer id;

        @ApiModelProperty(value = "帮助类型id")
        private Integer helpTypeId;

        @ApiModelProperty(value = "语言")
        private String language;

        @ApiModelProperty(value = "标题")
        private String title;

        @ApiModelProperty(value = "排序")
        private Integer sort;

        @ApiModelProperty(value = "状态(0开启，1关闭)")
        private Integer status;

        @ApiModelProperty(value = "内容")
        private String content;

        @ApiModelProperty(value = "创建者")
        private String createBy;

        @ApiModelProperty(value = "创建时间")
        private Integer createdAt;

        @ApiModelProperty(value = "更新人")
        private String updateBy;

        @ApiModelProperty(value = "更新时间")
        private Integer updatedAt;

        @ApiModelProperty(value = "帮助类型名称")
        private String helpTypeName;

    }




}
