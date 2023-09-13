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
public interface BlogParams {


    /**
     * 新增
     */
    @Data
    class BlogAddReqDto {

        @ApiModelProperty(value = "语言" ,required = true)
        @NotBlank(message = "语言不能为空")
        private String language;

        @ApiModelProperty(value = "资讯类型" ,required = true)
        @NotBlank(message = "资讯类型不能为空")
        private String typeKey;

        @ApiModelProperty(value = "图片地址" ,required = true)
        @NotBlank(message = "图片地址不能为空")
        private String imageUrl;

        @ApiModelProperty(value = "主标题" ,required = true)
        @NotBlank(message = "主标题不能为空")
        private String titleMain;

        @ApiModelProperty(value = "副标题" ,required = true)
        @NotBlank(message = "副标题不能为空")
        private String titleSub;

        @ApiModelProperty(value = "内容" ,required = true)
        @NotBlank(message = "内容不能为空")
        private String content;

        @ApiModelProperty(value = "状态(0开启，1关闭)",example = "1")
        @Min(value =0,message ="状态参数错误")
        @Max(value =1,message ="状态参数错误")
        private Integer status;

        @ApiModelProperty(value = "推荐(0是，1否)",example = "1")
        @Min(value =0,message ="推荐参数错误")
        @Max(value =1,message ="推荐参数错误")
        private Integer recommend;

        @ApiModelProperty(value = "排序" ,required = true)
        @Min(value =0,message ="排序不能为空")
        private Integer sort;
    }

    /**
     * 修改
     */
    @Data
    class BlogEditReqDto {
        @ApiModelProperty(value = "编号",required = true)
        @Min(value =1,message ="编号错误")
        private Integer id;

        @ApiModelProperty(value = "语言")
        private String language;

        @ApiModelProperty(value = "资讯类型")
        private String typeKey;

        @ApiModelProperty(value = "图片地址")
        private String imageUrl;

        @ApiModelProperty(value = "主标题")
        private String titleMain;

        @ApiModelProperty(value = "副标题")
        private String titleSub;

        @ApiModelProperty(value = "内容")
        private String content;

        @ApiModelProperty(value = "状态(0开启，1关闭)")
        private Integer status;

        @ApiModelProperty(value = "推荐(0是，1否)")
        private Integer recommend;

        @ApiModelProperty(value = "排序")
        private Integer sort;
    }

    /**
     * 删除
     */
    @Data
    class BlogDeleteReqDto {
        @ApiModelProperty(value = "表id")
        @Size(min = 1,message = "id数组不能为空")
        private List<Integer> listId;
    }


    /**
     * 查询
     */
    @Data
    class BlogSelectReqDto{
        @ApiModelProperty(value = "语言")
        private String language;

        @ApiModelProperty(value = "资讯类型")
        private String typeKey;

        @ApiModelProperty(value = "状态(0开启，1关闭)")
        private Integer status;

        @ApiModelProperty(value = "推荐(0是，1否)")
        private Integer recommend;

        @ApiModelProperty(value = "创建者")
        private String createBy;

        @ApiModelProperty(value = "开始更新时间")
        private String startUpdateTime;

        @ApiModelProperty(value = "结束更新时间")
        private String endUpdateTime;
    }






}
