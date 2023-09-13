package com.lt.win.service.io.bo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 用户余额
 *
 * @author fangzs
 * @date 2022/8/11 22:02
 */
@Data
public class MemberBalanceBo implements Serializable {
    private static final long serialVersionUID = 2631391613103613940L;
    /**
     * 国家
     */
    private String country;
    /**
     * 币种
     */
    private String currency;
    /**
     * 余额
     */
    private BigDecimal balance = BigDecimal.ZERO;
    /**
     * 奖金余额
     */
    private BigDecimal bonusAmount = BigDecimal.ZERO;

    private Integer version = 0;
    private Long timestamp;
}
