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
public enum MessageDeviceEnum {
    D("D", "web端"),
    B("B", "后台");
    String code;
    String desc;

    MessageDeviceEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
