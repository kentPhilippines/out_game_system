package com.lt.win.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lt.win.utils.components.response.Result;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * Http 请求统一工具类
 *
 * @author admin
 */
@Slf4j
public class OkHttpUtils {
    public static final Integer SUCCESS_CODE = 0;
    public static final String NONE_LOG = "none log";
    public static final MediaType RAW_JSON = MediaType.Companion.parse("application/json;charset=utf-8");
    public static final MediaType RAW_TEXT = MediaType.Companion.parse("text/plain");
    public static final MediaType FORM_DATA = MediaType.Companion.parse("multipart/form-data");
    public static final MediaType X_WWW_FORM_URLENCODED = MediaType.parse("application/x-www-form-urlencoded");

    private static final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient.Builder()
            .connectionPool(new ConnectionPool(256, 10, TimeUnit.MINUTES))
            // 连接超时
            .connectTimeout(60, TimeUnit.SECONDS)
            // 写入超时
            .writeTimeout(60, TimeUnit.SECONDS)
            // 读取超时
            .readTimeout(60, TimeUnit.SECONDS)
            .build();


    private OkHttpUtils() {
        throw new IllegalStateException("Utility class");
    }


    /**
     * 正常日志输出格式
     *
     * @return 正常格式
     */
    @NotNull
    @Contract(pure = true)
    private static String formatInfo() {
        return "\n===================\t[ {} START SUCCESS ]\t========================================================="
                + "\nStartTime----------\t{}"
                + "\nRequestMethod------\t{}"
                + "\nRequestURI---------\t{}"
                + "\nRequestHeader------\t{}"
                + "\nRequestBody--------\t{}"
                + "\nResponseCode-------\t{}"
                + "\nResponseBody-------\t{}"
                + "\nEndTime------------\t{}"
                + "\n===================\t[ {}  END  SUCCESS ]\t=========================================================\n";
    }

    /**
     * 异常日志输出格式
     *
     * @return 异常格式
     */
    @NotNull
    @Contract(pure = true)
    private static String formatError() {
        return "\n===================\t[ {} START FAILURE ]\t========================================================="
                + "\nStartTime----------\t{}"
                + "\nRequestMethod------\t{}"
                + "\nExceptionInfo------\t{}"
                + "\nRequestURI---------\t{}"
                + "\nRequestHeader------\t{}"
                + "\nRequestBody--------\t{}"
                + "\nResponseCode-------\t{}"
                + "\nResponseBody-------\t{}"
                + "\nEndTime------------\t{}"
                + "\n===================\t[ {}  END  FAILURE ]\t=========================================================\n";
    }

    /**
     * HTTP 请求(无需设置 Header)
     *
     * @param url       请求URI
     * @param postData  请求体数据
     * @param mediaType 请求类型 JSON/FORM-DATA
     * @param model     提示信息
     * @return 响应参数
     */
    public static Result<String> doPost(String url, JSONObject postData, MediaType mediaType, String model) {
        return doPost(url, postData, null, mediaType, model);
    }

    /**
     * HTTP POST 请求
     *
     * @param url     请求URI
     * @param data    请求体数据
     * @param headers 请求头数据
     * @param model   提示参数
     */
    public static Result<String> doPost(String url, JSONObject data, Map<String, String> headers, MediaType mediaType, String model) {
        var execStartTime = DateUtils.yyyyMMddHHmmssSSS();
        var execEndTime = "";
        var execMethod = "";
        var resBody = "";
        var resCode = -1;
        Response execute;
        Request request;

        try {
            RequestBody requestBody;
            if (mediaType == FORM_DATA) {
                var builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                for (Map.Entry<String, Object> entry : data.entrySet()) {
                    builder.addFormDataPart(entry.getKey(), entry.getValue().toString());
                }
                requestBody = builder.build();
            } else if (mediaType == X_WWW_FORM_URLENCODED) {
                var tmp = new StringBuilder();
                for (Map.Entry<String, Object> entry : data.entrySet()) {
                    tmp.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
                }
                requestBody = RequestBody.create(tmp.substring(0, tmp.length() - 1), mediaType);
            } else {
                requestBody = RequestBody.Companion.create(data != null ? data.toString() : "", mediaType);
            }
            var builder = new Request.Builder();
            if (Optional.ofNullable(headers).isPresent()) {
                builder.headers(Headers.of(headers));
            }
            request = builder.url(url).post(requestBody).build();
            execMethod = request.method();
            execute = OK_HTTP_CLIENT.newCall(request).execute();

            // 构建日志参数
            resCode = execute.code();
            resBody = Objects.requireNonNull(execute.body()).string();
            execEndTime = DateUtils.yyyyMMddHHmmssSSS();
            if (execute.isSuccessful() && !NONE_LOG.equals(model)) {
                log.info(formatInfo(), model, execStartTime, execMethod, url, JSON.parseObject(JSON.toJSONString(headers)), data, resCode, resBody, execEndTime, model);
            } else if (!execute.isSuccessful()) {
                log.info(formatError(), model, execStartTime, execMethod, "", url, JSON.parseObject(JSON.toJSONString(headers)), data, resCode, resBody, execEndTime, model);
            }
        } catch (Exception e) {
            log.info(formatError(), model, execStartTime, execMethod, e, url, JSON.parseObject(JSON.toJSONString(headers)), data, resCode, resBody, execEndTime, model);
            return Result.error(resCode, resBody);
        }

        return Result.ok(resBody);
    }

    /**
     * HTTP POST 请求
     *
     * @param url     请求URI
     * @param data    请求体数据
     * @param headers 请求头数据
     * @param model   提示参数
     */
    public static Result<String> doGet(String url, JSONObject data, Map<String, String> headers, String model) {
        var execStartTime = DateUtils.yyyyMMddHHmmssSSS();
        var execEndTime = "";
        var execMethod = "";
        var resBody = "";
        var resCode = -1;
        Response execute;
        Request request;

        try {
            var builder = new Request.Builder();
            if (Optional.ofNullable(headers).isPresent()) {
                builder.headers(Headers.of(headers));
            }
            if (Optional.ofNullable(data).isPresent()) {
                var tmp = new StringBuilder();
                for (Map.Entry<String, Object> entry : data.entrySet()) {
                    tmp.append("&").append(entry.getKey()).append("=").append(entry.getValue());
                }
                url = url + tmp.toString().replaceFirst("&", "?");
            }
            request = builder.get().url(url).build();
            execMethod = request.method();
            execute = OK_HTTP_CLIENT.newCall(request).execute();

            // 构建日志参数
            resCode = execute.code();
            resBody = Objects.requireNonNull(execute.body()).string();
            execEndTime = DateUtils.yyyyMMddHHmmssSSS();
            if (execute.isSuccessful() && !NONE_LOG.equals(model)) {
                log.info(formatInfo(), model, execStartTime, execMethod, url, JSON.parseObject(JSON.toJSONString(headers)), data, resCode, resBody, execEndTime, model);
            } else if (!execute.isSuccessful()) {
                log.info(formatError(), model, execStartTime, execMethod, "", url, JSON.parseObject(JSON.toJSONString(headers)), data, resCode, resBody, execEndTime, model);
            }
        } catch (Exception e) {
            log.info(formatError(), model, execStartTime, execMethod, e, url, JSON.parseObject(JSON.toJSONString(headers)), data, resCode, resBody, execEndTime, model);
            return Result.error(resCode, resBody);
        }

        return Result.ok(resBody);
    }
}
