package com.lt.win.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.RandomStringUtils;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Random;

/**
 * @author: David
 * @date: 06/04/2020
 * @description:
 */
public class TextUtils {
    /**
     * 生成订单号
     */
    public static String generateTransferOrderId() {
        return DateUtils.yyyyMMddHHmmssSSS() + generateRandomString(2);
    }

    /**
     * 生成随机字符串
     */
    @NotNull
    public static String generateRandomString(Integer length) {
        var chars = new char[]{'1', '2', '3', '4', '5', '6', '7', '8', '9', '0',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
                'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
                'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        return RandomStringUtils.random(length, chars);
    }


    /**
     * 生成随机数字
     */
    public static Integer generatePromoCode() {
        // 100000 ～ 999999 随机字符串
        Random rand = new Random();
        return rand.nextInt(990000) + 10000;
    }

    /**
     * @desc: 生成ApiToken
     * @params: []
     * @return: java.lang.String
     * @author: David
     * @date: 06/04/2020
     */
    public static String generateApiToken() {
        return TextUtils.generateRandomString(32) + "_" + DateNewUtils.now();
    }

    /**
     * 拼接表单
     *
     * @param url
     * @param paramsMap
     * @return
     */
    public static String renderForm(String url, HashMap<String, Object> paramsMap) {
        var stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("<form action='%s' method='post'>", url));
        paramsMap.forEach((k, v) ->
                stringBuilder.append(String.format("<input type='hidden' name='%s' value='%s' />", k, v)));
        stringBuilder.append("</form>loading......");
        return stringBuilder.toString();
    }

    /**
     * 把Query类型字符转Java Bean
     * test=aaa&name=david ...
     *
     * @param query QueryString
     * @return Bean 对象
     */
    public static <T> T convertQueryString2Bean(String query, Class<T> clazz) {
        var data = new JSONObject();
        String[] split = query.split("&");
        for (String param : split) {
            String[] subSplit = param.split("=");
            data.put(subSplit[0], subSplit[1]);
        }

        return JSON.parseObject(JSON.toJSONString(data), clazz);
    }

    /**
     * 构建 二级KEY
     *
     * @param firstKey  firstKey
     * @param secondKey secondKey
     * @return 二级KEY  firstKey-secondKey
     */
    @Contract(pure = true)
    public static @NotNull String buildSubKey(@NotNull String firstKey, String secondKey) {
        return firstKey + '-' + secondKey;
    }
}
