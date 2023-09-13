package com.lt.win.service.exception;

import com.lt.win.utils.I18nUtils;
import com.lt.win.utils.components.response.CodeInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Digitain 快速游戏自定义异常
 *
 * @author fangzs
 * @date 2022/9/3 16:12
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DigitainException extends RuntimeException{

    private static final long serialVersionUID = -3360709586761576904L;
    protected final Integer code;
    protected final String message;
    protected final String jsonObject;

    public DigitainException(Integer code,String message,String jsonObject) {
        this.code = code;
        this.message = message;
        this.jsonObject = jsonObject;

    }

    public DigitainException(String msg, String jsonObject) {
        this.jsonObject = jsonObject;
        this.code = CodeInfo.STATUS_CODE_ERROR.getCode();
        this.message = msg;
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
    public static DigitainException buildException(@NotNull CodeInfo info,@NotNull String jsonObject, @NotNull Object... message) {
        String msg = I18nUtils.getLocaleMessage(info.getMsg());
        if (message.length > 0) {
            msg = String.format(msg, message);
        }
        return new DigitainException(info.getCode(), msg,jsonObject);
    }
}
