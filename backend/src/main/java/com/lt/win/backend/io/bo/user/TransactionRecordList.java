package com.lt.win.backend.io.bo.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;


/**
 * <p>
 * 交易记录-列表查询 请求body
 * </p>
 *
 * @author andy
 * @since 2020/3/19
 */
@Data
public class TransactionRecordList {

    @ApiModelProperty(name = "id", value = "ID")
    private Long id;

    @ApiModelProperty(name = "uid", value = "UID")
    private Integer uid;

    @ApiModelProperty(name = "category", value = "交易类型:1-上分 2-下分 3-投注 4-返水 5-奖励 6-佣金 7-结算")
    private Integer category;

    @ApiModelProperty(name = "referId", value = "关联ID")
    private Long referId;

    @ApiModelProperty(name = "coinBefore", value = "交易前金额")
    private BigDecimal coinBefore;

    @ApiModelProperty(name = "coin", value = "交易金额")
    private BigDecimal coin;

    @ApiModelProperty(name = "coinAfter", value = "交易后金额")
    private BigDecimal coinAfter;

    @ApiModelProperty(name = "time", value = "交易时间")
    private Integer time;

}
