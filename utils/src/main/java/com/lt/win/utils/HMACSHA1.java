package com.lt.win.utils;

import lombok.SneakyThrows;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 1. hmac_sha1编码结果需要转换成hex格式
 * 2. java中base64的实现和php不一致,其中java并不会在字符串末尾填补=号以把字节数补充为8的整数
 * 3. hmac_sha1并非sha1, hmac_sha1是需要共享密钥的
 *
 * @author fangzs
 * @date 2022/7/4 15:15
 */
public class HMACSHA1 {


    private static final String HMAC_SHA1 = "HmacSHA1";


    /**
     * 游戏接口签名
     * @param params 加签参数
     * @param secretKey 秘钥
     * @return 签名
     */
    @SneakyThrows
    public static String sign(Map<String, Object> params, String secretKey) {
        String signData = mapToString(params);
//        String signData =kSort(params);
        System.out.println(signData);
        return getSignature(signData,secretKey);
    }

    /**
     * map转为get请求字符串
     * @param params map
     * @return 字符串
     */
    public static String mapToString(Map<String, Object> params){
        return params.entrySet().stream().filter(e -> !Objects.isNull(e.getValue())).map(e -> e.getKey().concat("=").concat(String.valueOf(e.getValue()))).collect(Collectors.joining("&"));
    }

    private static String kSort(Map<String,Object> map){
        StringBuffer sb = new StringBuffer();
        String[] keys = new String[map.size()];
        int i = 0;
        for (String k: map.keySet()){
            keys[i] = k;
            i++;
        }
        Arrays.sort(keys);
        for (String s : keys){
            sb.append(s).append("=").append(map.get(s)).append("&");
        }
        String s = sb.toString();
        s = s.substring(0,s.length()-1);
        try{
            s = URLEncoder.encode(s,"UTF-8");
        }catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        s = s.replace("%3D","=").replace("%26","&");
        return s;
    }
    /**
     * 生成签名数据_HmacSHA1加密
     *
     * @param data 待加密的数据
     * @param key  加密使用的key
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     */
    public static String getSignature(String data, String key) throws Exception {

        byte[] keyBytes = key.getBytes();
        // 根据给定的字节数组构造一个密钥。
        SecretKeySpec signingKey = new SecretKeySpec(keyBytes, HMAC_SHA1);
        Mac mac = Mac.getInstance(HMAC_SHA1);
        mac.init(signingKey);

        byte[] rawHmac = mac.doFinal(data.getBytes());

        String hexBytes = byte2hex(rawHmac);
        return hexBytes;
    }

    private static String byte2hex(final byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            // 以十六进制（基数 16）无符号整数形式返回一个整数参数的字符串表示形式。
            stmp = (Integer.toHexString(b[n] & 0xFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs;
    }




}

