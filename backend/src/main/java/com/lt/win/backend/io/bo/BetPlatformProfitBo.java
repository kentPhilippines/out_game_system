package com.lt.win.backend.io.bo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Auther: wells
 * @Date: 2022/10/26 10:19
 * @Description:
 */
@Data
public class BetPlatformProfitBo {
    private Integer uid;

    private Integer gameGroupId;

    private Integer gamePlatId;

    private Integer gameId;

    private BigDecimal Profit;

}
