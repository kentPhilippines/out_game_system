package com.lt.win.backend.io.bo.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>
 * 交易记录-列表查询 统计总金额 响应body
 * </p>
 *
 * @author andy
 * @since 2020/3/20
 */
@Data
public class TransactionRecordTotal {
    @ApiModelProperty(name = "totalCoinSF", value = "总转入金额")
    private BigDecimal totalCoinSF;

    @ApiModelProperty(name = "totalCoinXF", value = "总转出金额")
    private BigDecimal totalCoinXF;

    @ApiModelProperty(name = "totalCoinTZ", value = "总投注金额")
    private BigDecimal totalCoinTZ;

    @ApiModelProperty(name = "totalCoinFS", value = "总返水金额")
    private BigDecimal totalCoinFS;

    @ApiModelProperty(name = "totalCoinJL", value = "总奖励金额")
    private BigDecimal totalCoinJL;

    @ApiModelProperty(name = "totalCoinYJ", value = "总佣金金额")
    private BigDecimal totalCoinYJ;

    @ApiModelProperty(name = "totalCoinJS", value = "总中奖金额")
    private BigDecimal totalCoinJS;
    @ApiModelProperty(name = "totalCoinCD", value = "总撤单金额")
    private BigDecimal totalCoinCD;
//    @ApiModelProperty(name = "totalCoinCJS", value = "总重结算金额")
//    private BigDecimal totalCoinCJS;
}
