package com.lt.win.backend.io.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author: piking
 * @date: 2020/8/1
 */
public interface HelpInfoParams {


    /**
     * 新增
     */
    @Data
    class HelpInfoAddReqDto {
        @ApiModelProperty(value = "帮助类型id", required = true)
        @Min(value = 0, message = "帮助类型id不能为空")
        private Integer helpTypeId;

        @ApiModelProperty(value = "语言", required = true)
        @NotBlank(message = "语言不能为空")
        private String language;

        @ApiModelProperty(value = "标题", required = true)
        @NotBlank(message = "标题不能为空")
        private String title;

        @ApiModelProperty(value = "排序", required = true)
        @Min(value = 0, message = "排序不能为空")
        private Integer sort;

        @ApiModelProperty(value = "状态(0开启，1关闭)", example = "1")
        @Min(value = 0, message = "编号错误")
        @Max(value = 1, message = "编号错误")
        private Integer status;

        @ApiModelProperty(value = "内容", required = true)
        @NotBlank(message = "内容不能为空")
        private String content;

    }

    /**
     * 修改
     */
    @Data
    class HelpInfoEditReqDto {
        @ApiModelProperty(value = "编号", required = true)
        @Min(value = 1, message = "编号错误")
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
    }

    /**
     * 删除
     */
    @Data
    class HelpInfoDeleteReqDto {
        @ApiModelProperty(value = "表id")
        @Size(min = 1, message = "id数组不能为空")
        private List<Integer> listId;
    }


    /**
     * 查询
     */
    @Data
    class HelpInfoSelectReqDto {
        @ApiModelProperty(value = "语言")
        private String language;
        @ApiModelProperty(value = "帮助类型id")
        private String helpTypeId;
        @ApiModelProperty(value = "状态(0开启，1关闭)")
        private String status;
        @ApiModelProperty(value = "创建者")
        private String createBy;
        @ApiModelProperty(value = "更新者")
        private String updateBy;
        @ApiModelProperty(value = "开始更新时间")
        private Integer startTime;
        @ApiModelProperty(value = "结束更新时间")
        private Integer endTime;
    }


}
