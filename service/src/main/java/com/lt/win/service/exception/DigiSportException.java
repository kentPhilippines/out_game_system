package com.lt.win.service.exception;

import com.lt.win.service.common.DigiSportErrorEnum;
import com.lt.win.utils.I18nUtils;
import com.lt.win.utils.components.response.CodeInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * 体育异常
 *
 * @author fangzs
 * @date 2022/11/1 14:47
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DigiSportException extends RuntimeException {

    private static final long serialVersionUID = 4580197082611520727L;
    protected final Integer code;
    protected final String message;
    protected final DigiSportErrorEnum errorEnum;

    public DigiSportException(DigiSportErrorEnum errorEnum) {
        this.code = errorEnum.getCode();
        this.message = errorEnum.getMessage();
        this.errorEnum = errorEnum;

    }


    /**
     * 构建自定义异常
     *
     * @param errorEnum errorEnum
     * @return businessException
     */
    @NotNull
    @Contract("_, _ -> new")
    public static DigiSportException buildException(@NotNull DigiSportErrorEnum errorEnum) {
        return new DigiSportException(errorEnum);
    }
}
