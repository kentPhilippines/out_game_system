package com.lt.win.utils.components.sms.changlan;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;


/**
 * @author admin
 */
@Slf4j
public class Sms253Util {

    @Nullable
    public static String sendSmsByPost(String path, String postContent) {
        URL url;
        try {
            url = new URL(path);
            HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
            // 提交模式
            httpUrlConnection.setRequestMethod("POST");
            //连接超时 单位毫秒
            httpUrlConnection.setConnectTimeout(10000);
            //读取超时 单位毫秒
            httpUrlConnection.setReadTimeout(10000);
            // 发送POST请求必须设置如下两行
            httpUrlConnection.setDoOutput(true);
            httpUrlConnection.setDoInput(true);
            httpUrlConnection.setRequestProperty("Charset", "UTF-8");
            httpUrlConnection.setRequestProperty("Content-Type", "application/json");

            httpUrlConnection.connect();
            OutputStream os = httpUrlConnection.getOutputStream();
            os.write(postContent.getBytes(StandardCharsets.UTF_8));
            os.flush();

            StringBuilder sb = new StringBuilder();
            int httpRspCode = httpUrlConnection.getResponseCode();
            if (httpRspCode == HttpURLConnection.HTTP_OK) {
                // 开始获取数据
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(httpUrlConnection.getInputStream(), StandardCharsets.UTF_8));
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                br.close();
                return sb.toString();

            }
        } catch (Exception e) {
            log.error(e.toString());
        }
        return null;
    }
}
