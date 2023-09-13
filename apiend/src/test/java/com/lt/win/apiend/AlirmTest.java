package com.lt.win.apiend;


import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.util.*;

/**
 * @Author : Wells
 * @Date : 2020/9/21 10:24 下午
 * @Description : XX
 **/
public class AlirmTest {
    public static void main(String[] args) {
        String host = "http://alirm-com.konpn.com";
        String path = "/query/com";
        String method = "GET";
        String appcode = "41bc3157f724427087a253f64b9e6843";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("symbol", "SZ000001");


        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            List<String> list = new ArrayList<>();
            for (int i = 0; i < 60; i++) {
                HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
                // System.out.println(response.toString());
                //获取response的body
                list.add(EntityUtils.toString(response.getEntity()));
            }
            System.out.println("list=" + Arrays.toString(list.toArray()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


