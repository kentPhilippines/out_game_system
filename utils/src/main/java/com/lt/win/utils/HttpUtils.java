package com.lt.win.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Http 请求统一工具类
 *
 * @author admin
 */
@Slf4j
public class HttpUtils {

    private static final AtomicInteger NEXT_ID = new AtomicInteger(0);
    private static final String CHARSET = "charset";
    private static final String CHARSET_UTF8 = "UTF-8";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String CONTENT_TYPE_JSON = "application/json";
    private static final String CONTENT_TYPE_URLENCODED = "application/x-www-form-urlencoded";
    private static final String CONTENT_ENCODING = "content-encoding";
    private static final String GZIP = "gzip";
    // 最大连接超时时间(单位-秒):3分钟
    private static final int MAX_CONNECT_TIMEOUT = 3 * 60;

    private static final ThreadPoolExecutor httpPool = new ThreadPoolExecutor(
            32,
            32,
            300L,
            TimeUnit.MINUTES,
            new LinkedBlockingQueue<>(500),
            r -> new Thread(r, "system_HttpUtil_http_" + NEXT_ID.getAndDecrement())
    );

    /**
     * 正常日志输出格式
     *
     * @return 正常格式
     */
    private static String formatInfo() {
        return "\n===================\t[ {} SEND 成功 ]\t========================================================="
                + "\nRequestIP----------\t{}"
                + "\nRequestMethod------\t{}"
                + "\nRequestURI---------\t{}"
                + "\nRequestBody--------\t{}"
                + "\nResponseBody-------\t{}"
                + "\nResponseStatusCode=\t{}"
                + "\n===================\t[ {} SEND 成功 ]\t=========================================================\n";
    }

    /**
     * 异常日志输出格式
     *
     * @return 异常格式
     */
    private static String formatError() {
        return "\n===================\t[ {} SEND 异常 ]\t========================================================="
                + "\nRequestIP----------\t{}"
                + "\nRequestMethod------\t{}"
                + "\nExceptionInfo------\t{}"
                + "\nRequestURI---------\t{}"
                + "\nRequestBody--------\t{}"
                + "\nResponseBody-------\t{}"
                + "\nResponseStatusCode=\t{}"
                + "\n===================\t[ {} SEND 异常 ]\t=========================================================\n";
    }

    /**
     * HTTP POST 请求
     * x-www-form-urlencoded: 自动转换 key=value&key1=value1
     * 其他格式: json 字符串
     *
     * @param url        请求URI
     * @param postData   请求体数据 JSON String
     * @param headerData 请求头数据
     * @param model      调用类
     */
    public static String doPost(String url, JSONObject postData, Map<String, String> headerData, String model) {
        String result = "";
        HttpRequest request = null;
        HttpResponse<byte[]> response = null;
        try {
            HttpClient.Builder builder = HttpClient.newBuilder();
            HttpClient client = builder
                    .version(HttpClient.Version.HTTP_2)
                    .connectTimeout(Duration.ofSeconds(MAX_CONNECT_TIMEOUT))
                    .executor(httpPool)
                    .build();

            // 类型: urlencoded: key=value&key1=value1 其他: json字符串
            String data = postData.toJSONString();
            // 构建HTTP 强求参数
            HttpRequest.Builder uri = HttpRequest.newBuilder()
                    .uri(URI.create(url));
            for (Map.Entry<String, String> entry : headerData.entrySet()) {
                // 设置请求头
                uri.setHeader(entry.getKey(), entry.getValue());
                if (entry.getKey().equals(CONTENT_TYPE) && entry.getValue().equals(CONTENT_TYPE_URLENCODED)) {
                    // Content-Type : application/x-www-form-urlencoded 拼接数据
                    StringBuilder stringBuilder = new StringBuilder();
                    postData.forEach((key, value) -> stringBuilder.append(key).append('=').append(value).append('&'));
                    String s = stringBuilder.toString();
                    // 去除末尾的& +特殊处理
                    data = s.substring(0, s.length() - 1).replace("+", "%2b");
                }
            }

            HttpRequest.BodyPublisher publisher = HttpRequest.BodyPublishers.ofString(data);
            request = uri.POST(publisher).build();

            // 发送HTTP强求
            response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());

            // 响应参数是GZIP 自动解压
            Optional<String> s = response.headers().firstValue(CONTENT_ENCODING);
            if (s.isPresent() && s.get().equals(GZIP)) {
                result = GZIPUtils.uncompressToString(response.body());
            } else {
                result = new String(response.body());
            }

            log.info(formatInfo(), model, IpUtil.getHostAddress(), request.method(), url, postData, result, response.statusCode(), model);
        } catch (Exception e) {
            log.error(formatError(), model, IpUtil.getHostAddress(), null != request ? request.method() : null, e, url, postData, result, null != response ? response.statusCode() : -1, model);
            return "";
        }

        return result;
    }

    /**
     * http异步调用:GET请求
     *
     * @param uri 请求地址
     * @return
     * @throws Exception
     */
    public static String sendAsyncGET(String uri) throws Exception {
        log.info("HttpRequest====" + uri);
        HttpClient.Builder builder = HttpClient.newBuilder();
        HttpClient client = builder
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(MAX_CONNECT_TIMEOUT))
                .executor(httpPool)
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .headers(CHARSET, CHARSET_UTF8, CONTENT_TYPE, CONTENT_TYPE_JSON)
                .GET()
                .build();
        CompletableFuture<String> stringCompletableFuture = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body);
        String result = null;
        try {
            result = stringCompletableFuture.get();
            log.info("HttpResponse====" + result);
        } catch (InterruptedException | ExecutionException e) {
            log.error(e.toString());
            throw e;
        }
        return result;
    }

    /**
     * GET请求
     *
     * @param reqURI URI
     * @param model  MODEL
     * @return 响应体
     */
    public static String doGet(String reqURI, String model) {
        String result = null;
        HttpRequest request = null;
        HttpResponse<String> response = null;
        URI uri = null;
        try {
            URL url = new URL(reqURI);
            // 过滤特殊字符
            uri = new URI(url.getProtocol(), null, url.getHost(), url.getPort(), url.getPath(), url.getQuery(), null);
            HttpClient.Builder builder = HttpClient.newBuilder();
            HttpClient client = builder
                    .version(HttpClient.Version.HTTP_2)
                    .connectTimeout(Duration.ofSeconds(MAX_CONNECT_TIMEOUT))
                    .executor(httpPool)
                    .build();
            // 请求
            request = HttpRequest.newBuilder()
                    .uri(uri)
                    .header(CONTENT_TYPE, CONTENT_TYPE_JSON)
                    .GET()
                    .build();
            // 发送
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            // 响应
            result = response.body();
            log.info(formatInfo(), model, IpUtil.getHostAddress(), request.method(), uri, null, result, response.statusCode(), model);
        } catch (Exception e) {
            log.error(formatError(), model, IpUtil.getHostAddress(), null != request ? request.method() : null, e, uri, null, result, null != response ? response.statusCode() : -1, model);
            return "";
        }
        return result;
    }

    /**
     * POS请求
     *
     * @param reqURI      请求URI
     * @param requestBody 请求体
     * @param model       MODEL
     * @return 响应体
     */
    public static String doPost(String reqURI, String requestBody, String model) throws Exception {
        String result = null;
        HttpRequest request = null;
        HttpResponse<String> response = null;
        try {
            HttpClient.Builder builder = HttpClient.newBuilder();
            HttpClient client = builder
                    .version(HttpClient.Version.HTTP_1_1)
                    .connectTimeout(Duration.ofSeconds(MAX_CONNECT_TIMEOUT))
                    .executor(httpPool)
                    .build();
            HttpRequest.BodyPublisher publisher = HttpRequest.BodyPublishers.ofString(requestBody);
            // 请求
            request = HttpRequest.newBuilder()
                    .uri(URI.create(reqURI))
                    .headers(CHARSET, CHARSET_UTF8, CONTENT_TYPE, CONTENT_TYPE_JSON)
                    .POST(publisher)
                    .build();
            // 发送
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            // 响应
            result = response.body();
            log.info(formatInfo(), model, IpUtil.getHostAddress(), request.method(), reqURI, requestBody, result, response.statusCode(), model);
        } catch (Exception e) {
            log.error(formatError(), model, IpUtil.getHostAddress(), null != request ? request.method() : null, e, reqURI, requestBody, result, null != response ? response.statusCode() : -1, model);
            throw e;
        }
        return result;
    }

    /**
     * 沙巴体育请求三方
     *
     * @param uri
     * @param params
     * @return
     */
    public static String postHttp(String uri, String params) {
        StringBuilder response = new StringBuilder();
        log.info("uii=" + uri);
        log.info("params=" + params);
        try {
            byte[] postDataBytes = params.getBytes(StandardCharsets.UTF_8);
            URL url = new URL(uri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty(CONTENT_TYPE, "application/x-www-form-urlencoded");
            conn.setRequestProperty("X-Requested-With", "X-Api-Client");
            conn.setRequestProperty("X-Api-Call", "X-Api-Client");
            conn.setRequestProperty("Accept", "*/*");
            conn.setRequestProperty("Cache-Control", "no-cache");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            conn.setRequestProperty("Connection", "keep-alive");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
            os.write(postDataBytes);
            os.flush();
            os.close();
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream is = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                log.info("response>>>" + conn.getResponseMessage());
                log.info("rsp code:" + conn.getResponseCode());
                log.info("rsp:" + response);
            } else {
                log.info("response>>>" + conn.getResponseMessage());
                log.info("rsp code:" + conn.getResponseCode());
            }
        } catch (Exception ex) {
            log.error("执行postHttp失败;" + ex.getMessage());
        }
        log.info("response -- " + response);
        return response.toString();
    }


    /**
     * 自定义Post 请求
     *
     * @param strUrl   请求地址
     * @param postData Post 数据
     * @param header   头部信息
     * @return 调用完成的结果值
     */
    public static String send(String strUrl, Map<String, String> postData, Map<String, String> header) throws InterruptedException, IOException, URISyntaxException {
        try {
            URL url = new URL(strUrl);

            HttpClient client = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_1_1)
                    .connectTimeout(Duration.ofSeconds(MAX_CONNECT_TIMEOUT))
                    .executor(httpPool)
                    .build();


            // 过滤特殊字符
            URI uri = new URI(url.getProtocol(), null, url.getHost(), url.getPort(), url.getPath(), url.getQuery(), null);
            log.info("HttpRequest====" + uri);
            // 请求
            HttpRequest.Builder builder = HttpRequest.newBuilder()
                    .uri(uri);
            // 设置HEADER 部分
            for (Map.Entry<String, String> m : header.entrySet()) {
                builder.header(m.getKey(), m.getValue());
            }
            builder.headers(CHARSET, CHARSET_UTF8, CONTENT_TYPE, CONTENT_TYPE_JSON);

            HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString(JSON.toJSONString(postData));
            HttpRequest request = builder.POST(bodyPublisher).build();

            // 同步发送
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            log.info("sendUrl====" + url);
            log.info("sendData====" + JSON.toJSONString(postData));
            log.info("sendRequest====" + request);
            // 响应体
            String result = response.body();
            log.info("HttpResponse====" + result);
            return result;
        } catch (Exception e) {
            log.error("=============================================");
            log.error("send异常: " + e + "\n");
            log.error("URL: " + strUrl + "\n");
            log.error("Data: " + JSON.toJSONString(postData) + "\n");
            throw e;
        }
    }

    public static HttpResponse<byte[]> doPostWithStatusCode(String url, JSONObject postData, Map<String, String> headerData, String model) throws Exception {
        String result = "";
        HttpRequest request = null;
        HttpResponse<byte[]> response = null;
        try {
            HttpClient.Builder builder = HttpClient.newBuilder();
            HttpClient client = builder
                    .version(HttpClient.Version.HTTP_2)
                    .connectTimeout(Duration.ofSeconds(MAX_CONNECT_TIMEOUT))
                    .executor(httpPool)
                    .build();

            // 类型: urlencoded: key=value&key1=value1 其他: json字符串
            String data = postData.toJSONString();
            // 构建HTTP 强求参数
            HttpRequest.Builder uri = HttpRequest.newBuilder()
                    .uri(URI.create(url));
            for (Map.Entry<String, String> entry : headerData.entrySet()) {
                // 设置请求头
                uri.setHeader(entry.getKey(), entry.getValue());
                if (entry.getKey().equals(CONTENT_TYPE) && entry.getValue().equals(CONTENT_TYPE_URLENCODED)) {
                    // Content-Type : application/x-www-form-urlencoded 拼接数据
                    StringBuilder stringBuilder = new StringBuilder();
                    postData.forEach((key, value) -> stringBuilder.append(key).append('=').append(value).append('&'));
                    String s = stringBuilder.toString();
                    // 去除末尾的& +特殊处理
                    data = s.substring(0, s.length() - 1).replace("+", "%2b");
                }
            }

            HttpRequest.BodyPublisher publisher = HttpRequest.BodyPublishers.ofString(data);
            request = uri.POST(publisher).build();

            // 发送HTTP强求
            response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());
            log.info(formatInfo(), model, IpUtil.getHostAddress(), request.method(), url, postData, result, response.statusCode(), model);
        } catch (Exception e) {
            log.error(formatError(), model, IpUtil.getHostAddress(), null != request ? request.method() : null, e, url, postData, result, null != response ? response.statusCode() : -1, model);
            throw e;
        }
        return response;
    }
}
