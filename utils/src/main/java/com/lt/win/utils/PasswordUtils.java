package com.lt.win.utils;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.jetbrains.annotations.NotNull;

/**
 * @author: David
 * @date: 06/04/2020
 * @description: Hash类型密码 生成/验证
 */
public class PasswordUtils {
    /**
     * 生成Hash类型密码
     *
     * @param password 密码明文
     * @return Hash类型密码密文
     */
    @NotNull
    public static String generatePasswordHash(@NotNull String password) {
        return BCrypt.with(BCrypt.Version.VERSION_2Y).hashToString(13, password.toCharArray());
    }

    /**
     * 验证密文是否是由明文加密而来
     *
     * @param password     密码明文
     * @param passwordHash 密码密文
     * @return true:验证通过 false:验证失败
     */
    public static Boolean validatePasswordHash(@NotNull String password, String passwordHash) {
        BCrypt.Result res = BCrypt.verifyer().verify(password.toCharArray(), passwordHash);
        return res.verified;
    }
}
