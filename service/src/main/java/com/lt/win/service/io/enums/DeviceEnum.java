package com.lt.win.service.io.enums;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author david
 * @auther: David
 * date: 2022/8/27 19:30
 * description:
 */

@Getter
@NoArgsConstructor
public enum DeviceEnum {
    /**
     * 请求设备
     */
    // mobile - 手机
    M("m"),
    // desktop - PC
    D("d"),
    // 安卓
    ANDROID("android"),
    // IOS
    IOS("ios"),
    ;
    String value;

    DeviceEnum(String value) {
        this.value = value;

    }
}
