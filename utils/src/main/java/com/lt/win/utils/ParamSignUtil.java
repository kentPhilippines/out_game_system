package com.lt.win.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;


public class ParamSignUtil {
    /**
     * 数据签名
     *
     * @param params
     * @param signKey
     * @return
     * @throws Exception
     */
    public static String sign(Map<String, Object> params, String signKey) throws Exception {
        List<String> paramNameList = new ArrayList<>();
        for (String key : params.keySet()) {
            if (!"sign".equals(key) && !"ext".equals(key)) {
                paramNameList.add(key);
            }
        }
        Collections.sort(paramNameList);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < paramNameList.size(); i++) {
            String name = paramNameList.get(i);
            stringBuilder.append(params.get(name));
        }
        //System.out.println("sort plain str :" + stringBuilder.toString() + signKey);
        try {
            return HMACSHA256(stringBuilder.toString() + signKey, signKey, true);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

    /**
     * HMACSHA256算法生成校验信息
     *
     * @param data
     * @param key
     * @param toUpperCase
     * @return
     * @throws Exception
     */
    public static String HMACSHA256(String data, String key, Boolean toUpperCase) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        byte[] array = sha256_HMAC.doFinal(data.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }
        if (toUpperCase) {
            return sb.toString().toUpperCase();
        } else {
            return sb.toString().toLowerCase();
        }
    }


    /**
     * 验证签名
     *
     * @param params
     * @return
     */
    public static boolean verifySign(Map<String, String> params, String signKey) {
        String sign = "" + params.get("sign"); // 签名
        List<String> paramNameList = new ArrayList<>();
        for (String key : params.keySet()) {
            if (!"sign".equals(key) && !"ext".equals(key)) {
                paramNameList.add(key);
            }
        }
        Collections.sort(paramNameList);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < paramNameList.size(); i++) {
            String name = paramNameList.get(i);
            stringBuilder.append(params.get(name));
        }
        try {
            String tSign = HMACSHA256(stringBuilder.toString() + signKey, signKey, true);
            return tSign.equals(sign);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}