package com.lt.win.backend.io.bo.notice;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 公告管理-列表
 * </p>
 *
 * @author andy
 * @since 2020/5/6
 */
@Data
public class ListNoticeResBody {
    @ApiModelProperty(name = "id", value = "ID")
    private Integer id;

    @ApiModelProperty(name = "title", value = "标题")
    private String title;

    @ApiModelProperty(name = "content", value = "内容")
    private String content;

    @ApiModelProperty(name = "category", value = "类型:1-系统公告 2-站内信 3-体育预告 4-活动")
    private Integer category;

    @ApiModelProperty(name = "status", value = "状态: 1-启用 0-不启用")
    private Integer status;

    @ApiModelProperty(name = "createdAt", value = "时间")
    private Integer createdAt;

    @ApiModelProperty(name = "uids", value = "2-站内信字段")
    private String uids;
}
