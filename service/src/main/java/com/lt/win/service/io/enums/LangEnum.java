package com.lt.win.service.io.enums;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @auther: David
 * date: 2022/8/27 19:30
 * description:
 */

@Getter
@NoArgsConstructor
public enum LangEnum {
    /**             中文      英语       西班牙牙语   俄罗斯  葡萄牙
     * 请求语言参数  ZH("zh")，EN("en")，ES("es")，RU("ru")，PT("pt") ,   JA("ja")，KO("ko")
     */
    // 中文
    ZH("zh"),
    // 英语
    EN("en"),
    //西班牙
    ES("es"),
    // 俄罗斯
    RU("ru"),
    // 葡萄牙
    PT("pt") ,
    // 韩国
    KO("ko"),
    //日本
    JA("ja"),
    ;
    String value;

    LangEnum(String value) {
        this.value = value;
    }
}
