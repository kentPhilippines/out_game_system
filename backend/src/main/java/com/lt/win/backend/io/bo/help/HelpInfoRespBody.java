package com.lt.win.backend.io.bo.help;

import java.util.Date;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
*
* @author pk
* @title  帮助详情     
* @date   2022-08-08 13:44:19 
* @since <版本号>
*/
@ApiModel(value = "帮助详情返回")
@Data
public class HelpInfoRespBody{
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
    @ApiModelProperty(value = "类型图片")
    private String imageUrl;
}
