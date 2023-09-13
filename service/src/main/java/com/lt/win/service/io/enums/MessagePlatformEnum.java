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
public enum MessagePlatformEnum {
    SMSTH("253", "253"),
    SMSIN("SkyLine", "天一"),
    SMSNX("nxcloud", "nxcloud"),
    SMSNODE("node", "NODESMS");
    String code;
    String desc;

    MessagePlatformEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
