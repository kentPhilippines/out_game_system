package com.lt.win.service.io.enums;

import lombok.Getter;

/**
 * @Auther: wells
 * @Date: 2022/11/14 14:21
 * @Description:
 */
@Getter
public enum CategoryCurrencyEnum {
    TRC(1, "TRC"),
    ERC(2, "ERC"),
    BANK(3, "BANK"),
    PIX(4, "PIX"),
    GCASH(5, "GCASH");

    CategoryCurrencyEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    private Integer code;
    private String name;

    public static String getName(Integer code) {
        CategoryCurrencyEnum[] values = CategoryCurrencyEnum.values();
        for (CategoryCurrencyEnum value : values) {
            if (value.getCode().equals(code)) {
                return value.getName();
            }
        }
        return null;
    }
}
