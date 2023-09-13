package com.lt.win.backend.io.bo.notice;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 公告管理-添加公告
 * </p>
 *
 * @author andy
 * @since 2020/5/6
 */
@Data
public class AddNoticeReqBody {

    @NotNull(message = "category不能为空")
    @ApiModelProperty(name = "category", value = "类型:1-系统公告 2-站内信 3-体育预告 4-活动", example = "1", required = true)
    private Integer category;

    @NotNull(message = "status不能为空")
    @ApiModelProperty(name = "status", value = "状态: 1-启用 0-不启用", example = "1", required = true)
    private Integer status;

    @NotEmpty(message = "title不能为空")
    @ApiModelProperty(name = "title", value = "标题", example = "标题1", required = true)
    private String title;

    @NotEmpty(message = "content不能为空")
    @ApiModelProperty(name = "content", value = "内容", example = "内容1", required = true)
    private String content;

    @ApiModelProperty(name = "uids", value = "UID:当category为2时,uids不能为空", example = "1,2,3,4")
    private String uids;
}
