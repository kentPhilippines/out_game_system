package com.lt.win.backend.io.bo.blog;

import java.util.Date;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
*
* @author pk
* @title  博客     
* @date   2022-08-01
* @since <版本号>
*/
@ApiModel(value = "博客返回")
@Data
public class BlogRespBody{
	

    @ApiModelProperty(value = "主键编号")
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

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新人")
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "资讯类型名称")
    private String typeName;
	
    
}
