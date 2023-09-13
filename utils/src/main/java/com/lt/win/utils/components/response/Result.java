package com.lt.win.utils.components.response;

import lombok.Data;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * @author: David
 * @date: 28/02/2020
 * @description: Api接口统一返回格式
 */
@Data
public class Result<T> {
    /**
     * 返回码
     */
    private Integer code;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 数据
     */
    private T data;

    public Result() {

    }

    public Result(Integer code, String message) {
        this.code = code;
        this.msg = message;
    }

    public Result(Integer code, String message, T data) {
        this.code = code;
        this.msg = message;
        this.data = data;
    }

    public Result(@NotNull CodeInfo codeInfo) {
        this.code = codeInfo.getCode();
        this.msg = codeInfo.getMsg();
        this.setData((T) "");
    }

    public Result(T data) {
        this.code = CodeInfo.STATUS_CODE_SUCCESS.getCode();
        this.msg = CodeInfo.STATUS_CODE_SUCCESS.getMsg();
        this.data = data;
    }

    /**
     * 正常指定数据返回
     *
     * @return result
     */
    @NotNull
    public static Result<String> ok() {
        return new Result<>(CodeInfo.STATUS_CODE_SUCCESS);
    }

    @NotNull
    @Contract("_ -> new")
    public static Result<String> ok(CodeInfo codeInfo) {
        return new Result<>(codeInfo);
    }

    /**
     * 正常指定数据返回
     *
     * @param data 数据
     * @param <K>  范型
     * @return 范型
     * @author David
     * @date 07/06/2020
     */
    @NotNull
    @Contract("_ -> new")
    public static <K> Result<K> ok(K data) {
        return new Result<>(CodeInfo.STATUS_CODE_SUCCESS.getCode(), CodeInfo.STATUS_CODE_SUCCESS.getMsg(), data);
    }

    /**
     * 异常返回
     *
     * @return result
     */
    @NotNull
    public static Result<String> error() {
        return new Result<>(CodeInfo.STATUS_CODE_ERROR);
    }

    @NotNull
    public static Result<String> error(CodeInfo codeInfo) {
        return new Result<>(codeInfo);
    }

    @NotNull
    @Contract(value = "_, _ -> new", pure = true)
    public static Result<String> error(Integer code, String message) {
        return new Result<>(code, message);
    }

}
