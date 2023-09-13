package com.lt.win.utils;

/**
 * <p>
 * 生成UUID
 * </p>
 *
 * @author andy
 * @since 2020/4/20
 */
public class UUID {

    /**
     * 生成32位字符串
     *
     * @return
     */
    public static String getUUID() {
        java.util.UUID uuid = java.util.UUID.randomUUID();
        String tmp = uuid.toString();
        tmp = tmp.replace("-", "");
        return tmp;
    }
}
