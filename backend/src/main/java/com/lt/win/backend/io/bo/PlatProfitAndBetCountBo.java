package com.lt.win.backend.io.bo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Auther: wells
 * @Date: 2022/10/28 10:52
 * @Description:
 */
@Data
public class PlatProfitAndBetCountBo {
    private String createTimeStr;

    private BigDecimal profit;

    private Integer betCount;

    private Integer betUserCount;

}
