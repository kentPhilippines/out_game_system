package com.lt.win.service.exception;

import com.lt.win.service.io.dto.BaseParams;
import com.lt.win.utils.components.response.CodeInfo;
import com.lt.win.utils.components.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * @author: David
 * @date: 03/03/2020
 * @description:
 */
@Slf4j
@ControllerAdvice
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 处理自定义的业务异常
     *
     * @param e 异常
     * @return Result
     */
    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public Result<String> businessExceptionHandler(@NotNull BusinessException e) {
        return Result.error(e.getCode(), e.getMessage());
    }

    /**
     * 参数认证相关异常
     *
     * @param e 异常
     * @return Result
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public Result<String> validExceptionHandler(@NotNull MethodArgumentNotValidException e) {
        var bindingResult = e.getBindingResult();
        // 取第一条异常信息
        var fieldError = bindingResult.getFieldErrors().get(0);
        String message = "[" + fieldError.getField() + "]" + fieldError.getDefaultMessage();
        return Result.error(CodeInfo.setMsg(CodeInfo.PARAMETERS_INVALID.getCode(), message));
    }

    /**
     * HTTP 请求方式异常(对客户我们仅支持 application/json 方式)
     *
     * @param e 异常
     * @return Result
     */
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public Result<String> validHttpRequestMethodHandler(@NotNull HttpRequestMethodNotSupportedException e) {
        // 针对客户访问  我们仅支持 application/json 方式请求
        String message = "Try POST ['Content-Type':'application/json'] : " + e.getMessage();
        return Result.error(CodeInfo.setMsg(CodeInfo.STATUS_CODE_400.getCode(), message));
    }

    /**
     * HTTP 请求方式异常(对客户我们仅支持 application/json 方式)
     *
     * @param e 异常
     * @return Result
     */
    @ExceptionHandler(value = HttpMediaTypeNotSupportedException.class)
    @ResponseBody
    public Result<String> validMediaTypeMethodHandler(@NotNull HttpMediaTypeNotSupportedException e) {
        // 针对客户访问  我们仅支持 application/json 方式请求
        String message = "Try ['Content-Type':'application/json'] : " + e.getMessage();
        return Result.error(CodeInfo.setMsg(CodeInfo.STATUS_CODE_400.getCode(), message));
    }

    /**
     * 处理其他异常
     *
     * @param e 异常
     * @return Result
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result<String> exceptionHandler(@NotNull Exception e) {
        e.printStackTrace();
        Result<String> result;
        if (e instanceof MissingServletRequestParameterException) {
            result = Result.error(CodeInfo.PARAMETERS_INVALID);
        } else if (e instanceof HttpMessageNotReadableException || e instanceof BindException) {
            result = Result.error(CodeInfo.STATUS_CODE_400);
        } else if (e instanceof HttpClientErrorException) {
            result = Result.error(CodeInfo.STATUS_CODE_403);
        } else if (e instanceof NoHandlerFoundException) {
            result = Result.error(CodeInfo.STATUS_CODE_404);
        } else if (e instanceof HttpRequestMethodNotSupportedException) {
            result = Result.error(CodeInfo.STATUS_CODE_405);
        } else {
            result = Result.error(CodeInfo.STATUS_CODE_500);
        }
        return result;
    }

    /**
     * 处理其他异常
     *
     * @param e 异常
     * @return Result
     */
    @ExceptionHandler(value = DuplicateKeyException.class)
    @ResponseBody
    public Result<String> duplicateKeyHandler(@NotNull Exception e) {
        log.error(BaseParams.formatLineLog(), e.getMessage());
        return Result.error(CodeInfo.DUPLICATE_KEY_ERROR);
    }
}

