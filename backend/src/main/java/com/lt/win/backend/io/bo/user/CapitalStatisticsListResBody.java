package com.lt.win.backend.io.bo.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>
 * 会员管理-资金统计 响应
 * </p>
 *
 * @author andy
 * @since 2020/3/20
 */
@Data
@Builder
public class CapitalStatisticsListResBody {
    @ApiModelProperty(name = "coinCZ", value = "充值金额", example = "99999.00")
    private BigDecimal coinCZ;
    @ApiModelProperty(name = "coinTX", value = "提现金额", example = "49999.00")
    private BigDecimal coinTX;
    @ApiModelProperty(name = "coinYXYK", value = "游戏盈亏", example = "999.00")
    private BigDecimal coinYXYK;
    @ApiModelProperty(name = "coinJLZC", value = "奖励支出", example = "500.00")
    private BigDecimal coinJLZC;
    @ApiModelProperty(name = "coinYJZC", value = "佣金支出", example = "500.00")
    private BigDecimal coinYJZC;
    @ApiModelProperty(name = "coinXTTZ", value = "系统调账", example = "600.00")
    private BigDecimal coinXTTZ;
    @ApiModelProperty(name = "coinPTYK", value = "平台盈亏", example = "50000.00")
    private BigDecimal coinPTYK;
    @ApiModelProperty(name = "coinBet", value = "投注总额", example = "50000.00")
    private BigDecimal coinBet;
}
