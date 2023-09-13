package com.lt.win.service.io.enums;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author david
 * @since 2022/8/27 19:30
 */

@Getter
@NoArgsConstructor
public enum CurrencyEm {
    /**
     * 货币国家码
     */
    USD("USD", "US"),
    BRL("BRL", "BR"),
    PHP("PHP", "PH"),
    ;
    String value;
    /**
     * 国家
     */
    String country;

    CurrencyEm(String value, String country) {
        this.value = value;
        this.country = country;
    }
}
