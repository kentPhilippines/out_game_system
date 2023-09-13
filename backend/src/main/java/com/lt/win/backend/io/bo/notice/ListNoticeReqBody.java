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
public class ListNoticeReqBody {
    @ApiModelProperty(name = "id", value = "ID", example = "1")
    private Integer id;

    @ApiModelProperty(name = "title", value = "标题", example = "标题1")
    private String title;

    @ApiModelProperty(name = "category", value = "类型:1-系统公告 2-站内信 3-体育预告 4-活动", example = "1")
    private Integer category;

    @ApiModelProperty(name = "status", value = "状态: 1-启用 0-不启用", example = "1")
    private Integer status;
}
