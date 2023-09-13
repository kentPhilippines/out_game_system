package com.lt.win.apiend.io.dto.notice;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Auther: wells
 * @Date: 2022/8/13 16:48
 * @Description:
 */
public interface NoticeParams {

    @Data
    @ApiModel(value = "MessageListReq", description = "消息列表请求实体")
    class MessageListReq {
        @ApiModelProperty(name = "category", value = "通知种类:1-notice 2-bulletin 3-information", example = "1")
        @NotNull
        public Integer category;
    }

    @Data
    @ApiModel(value = "MessageCountRes", description = "消息数量响应实体")
    class MessageCountRes {
        @ApiModelProperty(name = "category", value = "通知种类:1-notice 2-bulletin 3-information", example = "1")
        @NotNull
        public Integer category;
        @ApiModelProperty(name = "count", value = "消息数量", example = "3")
        public Long count = 0L;
    }

    @Data
    @ApiModel(value = "MessageListRes", description = "消息列表响应实体")
    class MessageListRes {
        @ApiModelProperty(name = "id", value = "ID", example = "101")
        public Integer id;
        @ApiModelProperty(name = "title", value = "标题", example = "红包客服")
        public String title;
        @ApiModelProperty(name = "content", value = "内容", example = "欢迎红包在线24小时QQ客服: 3300123123")
        public String content;
        @ApiModelProperty(name = "isRead", value = "是否已读", example = "false")
        public boolean isRead;
        @ApiModelProperty(name = "category", value = "通知种类:1-notice 2-bulletin 3-information;字典:dic_notice_category", example = "1")
        public Integer category;
        @ApiModelProperty(name = "createAt", value = "创建时间", example = "1587224410")
        public Integer createdAt;
    }

    @Data
    @ApiModel(value = "UpdateMessageReq", description = "消息列表响应实体")
    class UpdateMessageReq {
        @ApiModelProperty(name = "category", value = "通知种类:1-notice 2-bulletin 3-information;字典:dic_notice_category", example = "1")
        public Integer category;
        @ApiModelProperty(name = "id", value = "活动ID", example = "101;id为空则设置全部已读")
        public Integer id;
    }

    @Data
    @ApiModel(value = "LampListRes", description = "跑马灯响应实体")
    class LampListRes {
        @ApiModelProperty(name = "title", value = "标题", example = "红包客服")
        public String title;
        @ApiModelProperty(name = "content", value = "内容", example = "欢迎红包在线24小时QQ客服: 3300123123")
        public String content;
    }

    class DeleteMessageStatusReq {
        @ApiModelProperty(name = "category", value = "通知种类:1-notice  3-information", example = "1")
        @NotNull
        public Integer category;
    }
}
