package com.lt.win.service.exception;


import com.lt.win.utils.I18nUtils;
import com.lt.win.utils.components.response.CodeInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * @author: David
 * @date: 03/03/2020
 * @description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessException extends RuntimeException {

    protected final Integer code;
    protected final String message;
    protected final CodeInfo codeInfo;

    public BusinessException(@NotNull CodeInfo codeInfo) {
        this.code = codeInfo.getCode();
        this.message = codeInfo.getMsg();
        this.codeInfo = codeInfo;
    }

    public BusinessException(String msg) {
        this.code = CodeInfo.STATUS_CODE_ERROR.getCode();
        this.message = msg;
        this.codeInfo = CodeInfo.STATUS_CODE_ERROR;
    }

    /**
     * 构建自定义异常
     *
     * @param info    CodeInfo
     * @param message 自定义异常信息
     * @return businessException
     */
    @NotNull
    @Contract("_, _ -> new")
    public static BusinessException buildException(@NotNull CodeInfo info, @NotNull Object... message) {
        String msg = I18nUtils.getLocaleMessage(info.getMsg());
        if (message.length > 0) {
            msg = String.format(msg, message);
        }
        return new BusinessException(CodeInfo.setMsg(info.getCode(), msg));
    }

}
