package com.lt.win.utils;

import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;


/**
 * @Author william
 * @Date 2020/4/17 17:00
 * @Version 1.0
 **/
public class MD5 {
    public static void main(String[] args) {
//        System.out.println(MD5.encryption("12345678"));
    }

    /**
     * 将摘要信息转换为相应的编码
     *
     * @param code    编码类型
     * @param message 摘要信息
     * @return 相应的编码字符串
     */
    private static String Encode(String code, String message) {
        MessageDigest md;
        String encode = null;
        try {
            md = MessageDigest.getInstance(code);
            byte[] hash = md.digest(message.getBytes("UTF-8"));
//            encode = Hex.encodeHexString(hash);
            encode = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encode;
    }

    /**
     * 将摘要信息转换成SHA-256编码
     *
     * @param message 摘要信息
     * @return SHA-256编码之后的字符串
     */
    public static String sha256Encode(String message) {
        return Encode("SHA-256", message);
    }


    /**
     * @param plainText 明文
     * @return 32位密文
     */
    public static String encryption(String plainText) {
        String re_md5 = new String();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }

            re_md5 = buf.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return re_md5;
    }

    /**
     * md5加密转大写32喂
     * 生成签名
     *
     * @param paramsMap
     * @param key
     * @return
     */
    public static String signForInspiry(SortedMap<String, String> paramsMap, String key) {
        StringBuilder sbkey = new StringBuilder();
        Set<Map.Entry<String, String>> es = paramsMap.entrySet();
        Iterator<Map.Entry<String, String>> it = es.iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            String k = entry.getKey();
            Object v = entry.getValue();
            //空值不传递，不参与签名组串
            if (null != v && !"".equals(v)) sbkey.append(k + "=" + v + "&");
        }
        sbkey = sbkey.append("key=" + key);
        //MD5加密,结果转换为大写字符
        return DigestUtils.md5DigestAsHex(sbkey.toString().getBytes()).toUpperCase();
    }


    /**
     * 2X MD5验签
     * @param input
     * @return
     */
    public static String CalculateMD5Hash(String input) {

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(input.getBytes(StandardCharsets.UTF_8)); //StandardCharsets.US_ASCII
            byte hash[] = md.digest();

            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02X",  b)) ;
            }
            return sb.toString().toLowerCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }catch (Exception e ){

        }

        return null;
    }



}

