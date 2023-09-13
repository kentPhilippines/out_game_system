package com.lt.win.service.io.enums;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author david
 * date: 2022/8/27 19:30
 * description:
 */

@Getter
@NoArgsConstructor
public enum MsgActionEnum {
    /**
     * 消息行为
     */
    CONNECT(1, "初始化连接"),
    CHAT(2, "聊天消息"),
    KEEP_ALIVE(3, "心跳检测"),
    PUSH_NOTICE(4, "公告推送"),
    PUSH_ENVELOP(5, "红包雨推送"),
    PUSH_DN(6, "推送存款笔数"),
    PUSH_WN(7, "推送提款笔数"),
    PUSH_ON(8, "推送在线人数"),
    ;

    Integer type;
    String content;

    MsgActionEnum(Integer type, String content) {
        this.type = type;
        this.content = content;
    }
}
