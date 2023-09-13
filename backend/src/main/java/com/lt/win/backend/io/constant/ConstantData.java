package com.lt.win.backend.io.constant;

import lombok.Data;

/**
 * @Description:
 * @Author: David
 * @Date: 03/06/2020
 */
@Data
public class ConstantData {
    /**
     * Token 过期时间
     */
    public static final int EXPIRE_SECONDS = 60 * 60 * 24 * 365;
    /**
     * Bearer token 真实token的索引
     */
    public static final int TOKEN_SEG_INDEX = 1;

    public static final int STATUS_VALID_CODE = 10;
}
