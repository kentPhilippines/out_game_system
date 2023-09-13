package com.lt.win.apiend.io.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 * 意见反馈
 * </p>
 *
 * @author andy
 * @since 2020/6/1
 */
public interface Feedback {
    /**
     * ResponseBody 意见反馈-问题类型列表
     */
    @Data
    class ListCategory {
        @ApiModelProperty(name = "id", value = "ID", example = "1")
        private Integer id;
        @ApiModelProperty(name = "title", value = "标题", example = "充值问题")
        private String title;
    }

    /**
     * RequestBody 意见反馈-提交反馈
     */
    @Data
    class AddFeedbackReqBody {

        @NotNull(message = "类型不能为空")
        @ApiModelProperty(name = "category", value = "问题类型", example = "1", required = true)
        private Integer category;

        @NotEmpty(message = "内容不能为空")
        @ApiModelProperty(name = "content", value = "内容", required = true)
        private String content;

        @ApiModelProperty(name = "img", value = "附件:图片相对路径")
        private String img;
    }

    /**
     * RequestBody 意见反馈-删除
     */
    @Data
    class DelFeedbackReqBody {
        @NotNull(message = "ID不能为空")
        @ApiModelProperty(name = "id", value = "ID", example = "1")
        private Integer id;
    }

    /**
     * ResponseBody 问题反馈-列表
     */
    @Data
    class ListFeedBackResBody {
        @ApiModelProperty(name = "id", value = "ID", example = "1")
        private Integer id;

        @ApiModelProperty(name = "uid", value = "UID", example = "1")
        private Integer uid;

        @ApiModelProperty(name = "category", value = "问题类型")
        private Integer category;

        @ApiModelProperty(name = "imgList", value = "图片列表")
        private List<String> imgList;

        @ApiModelProperty(name = "status", value = "状态:1-已回复 0-未回复")
        private Integer status;

        @ApiModelProperty(name = "content", value = "内容", example = "充值问题无法到账...")
        private String content;
        @ApiModelProperty(name = "createdAt", value = "时间")
        private Integer createdAt;

        @ApiModelProperty(name = "replyCreatedAt", value = "已回复时间")
        private Integer replyCreatedAt;
        @ApiModelProperty(name = "replyContent", value = "回复内容", example = "回复内容....")
        private String replyContent;
    }
}
