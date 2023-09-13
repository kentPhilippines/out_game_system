package com.lt.win.service.io.enums;

import lombok.Getter;

/**
 *
 */
@Getter
public enum NoticeEnum {
    /**
     * webSocket
     * 1.充值成功
     * 2.提款成功
     * 3.系统维护
     * 4.-系统维护
     * 5-站内信
     * 6-体育预告
     * 7-活动
     */
    DN_SUCCESS("DN_SUCCESS", "充值成功"),
    WN_SUCCESS("WN_SUCCESS", "提款成功"),
    MAINTAIN_INFO("MAINTAIN_INFO", "系统维护"),
    SYSTEM_NOTIFICATION("SYSTEM_NOTIFICATION", "系统公告"),
    STATION_LETTER("STATION_LETTER", "站内信"),
    SPORTS_TRAILER("SPORTS_TRAILER", "体育预告"),
    ACTIVITY_NOTIFICATION("ACTIVITY_NOTIFICATION", "活动");


    String code;
    String message;

    NoticeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
