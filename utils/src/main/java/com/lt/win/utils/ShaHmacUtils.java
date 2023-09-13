package com.lt.win.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Map;
import java.util.TreeMap;

/**
 * SHA 加密共用类
 *
 * @author david
 */
@Slf4j
public class ShaHmacUtils {
    public static final String HMAC_SHA_1 = "HmacSHA1";
    public static final String HMAC_SHA_256 = "HmacSHA256";

    private ShaHmacUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 生成MD5 加密密文串
     *
     * @param json     json对象(Key-sign、Value为空 不参数签名)
     * @param keyValue 加密密钥
     * @return 加密后的字符串
     */
    @NotNull
    public static String genHmacSha1SecretByValue(@NotNull JSONObject json, String keyValue) {
        var jsonObject = new JSONObject(new TreeMap<>());
        jsonObject.putAll(json);

        var data = new StringBuilder();
        for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
            if (null == entry.getValue() || "".equals(entry.getValue()) || "sign".equals(entry.getKey())) {
                continue;
            }
            data.append(entry.getValue());
        }

        return Base64.getEncoder().encodeToString(doShaEncrypt(data.toString(), keyValue, HMAC_SHA_1));
    }

    /**
     * 生成Sha256 加密密文串
     *
     * @param data     加密字符串
     * @param keyValue 加密密钥
     * @return Base64 编码后的值
     */
    public static @NotNull String genHmacSha256Secret(@NotNull String data, String keyValue) {
        return Base64.getEncoder().encodeToString(doShaEncrypt(data, keyValue, HMAC_SHA_256));
    }

    /**
     * 字节数组转16进制
     *
     * @param b .
     * @return .
     */
    public static @NotNull String byteArrayToHexString(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b != null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1) {
                hs.append('0');
            }

            hs.append(stmp);
        }
        return hs.toString().toLowerCase();
    }

    /**
     * 加密
     *
     * @param plaintext 明文
     * @param signKey   密钥
     * @return 秘文
     */
    private static byte @Nullable [] doShaEncrypt(String plaintext, String signKey, String algorithm) {
        try {
            var signingKey = new SecretKeySpec(signKey.getBytes(), algorithm);
            var mac = Mac.getInstance(algorithm);
            mac.init(signingKey);
            return mac.doFinal(plaintext.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        var data = "{\"platformId\":0,\"operatorId\":10651001,\"token\":\"iOiJIUzI1NiJ9.eyJ3ZWJSb2xlIjoid2ViIiwiaWQiOiIxIiwidXNlcm5hbWUiOiJ0ZXN0OTk5OSIsImp0aSI6IjAyOThiOWYyMmQ0MDQwMzlhNjAxOWIyNzIxYTIzZGExIiwic3ViIjoiWEJTcG9ydHMiLCJpYXQiOjE2NjI3OTcyNzAsImV4cCI6MTY2NTM4OTI3MH0.kpo8xndqRBDaHXdEjpWTuuRpW5TmL_QPlcl-xtQSPZw_1_USD_launch\",\"timestamp\":1662798143473}";
        var secret = "b1d85bd3-4c08-4877-8026-06da128f6152";
        var encrypt = genHmacSha256Secret(data, secret);
        log.info("111111\n");
        log.info(encrypt);
    }
}

