package com.lt.win.backend.io.dto.record;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Auther: wells
 * @Date: 2022/8/30 23:23
 * @Description: 交易管理参数
 */
public interface TradingManageParams {
    @Data
    @ApiModel(value = "CoinLogListReq", description = "帐变记录请求实体")
    class CoinLogListReq {
        @ApiModelProperty(name = "username", value = "用户名", example = "2")
        private String username;
        @ApiModelProperty(name = "UID", value = "UID", example = "84")
        private Integer uid;
        @ApiModelProperty(name = "ID", value = "ID或订单号", example = "59749182176628736")
        private String id;
        @ApiModelProperty(name = "status", value = "状态:0-处理中 1-成功 2-失败", example = "1")
        private Integer status;
        @ApiModelProperty(name = "category", value = "类型:1-存款 2-提款 3-投注 4-派彩 5-返水 6-佣金 7-活动(奖励) 8-系统调账;字典:dic_coin_log_category", example = "1")
        private Integer category;
        @ApiModelProperty(name = "startTime", value = "开始时间", example = "1600448057")
        private Integer startTime;
        @ApiModelProperty(name = "endTime", value = "结束时间", example = "1600559067")
        private Integer endTime;
    }

    @Data
    @ApiModel(value = "CoinLogListRes", description = "帐变记录响应实体")
    class CoinLogListRes {
        @ApiModelProperty(name = "uid", value = "UID", example = "84")
        private Integer uid;
        @ApiModelProperty(name = "username", value = "用户名", example = "wells")
        private String username;
        @ApiModelProperty(name = "id", value = "ID", example = "59749182176628736")
        private Long id;
        @ApiModelProperty(name = "referId", value = "订单号", example = "59749182176628736")
        private Long referId;
        @ApiModelProperty(name = "outIn", value = "收支类型:0-支出 1-收入;字典:dic_coin_log_out_in", example = "0")
        private Integer outIn;
        @ApiModelProperty(name = "category", value = "类型:1-存款 2-提款 3-投注 4-派彩 5-返水 6-佣金 7-活动(奖励) 8-系统调账;字典:dic_coin_log_category", example = "1")
        private Integer category;
        @ApiModelProperty(name = "coin", value = "帐变金额", example = "10.00")
        private BigDecimal coin;
        @ApiModelProperty(name = "coinBefore", value = "帐变前金额", example = "89.99")
        private BigDecimal coinBefore;
        @ApiModelProperty(name = "coinAfter", value = "帐变后金额", example = "99.99")
        private BigDecimal coinAfter;
        @ApiModelProperty(name = "createdAt", value = "创建时间", example = "1600448057")
        private Integer createdAt;
    }


    /**
     * 交易记录-统计-实体
     */
    @Data
    class StatisticsTrading {
        @ApiModelProperty(name = "coinDeposit", value = "总存款", example = "10000.00")
        private BigDecimal coinDeposit = BigDecimal.ZERO;
        @ApiModelProperty(name = "coinWithdrawal", value = "总提款", example = "10000.00")
        private BigDecimal coinWithdrawal = BigDecimal.ZERO;
        @ApiModelProperty(name = "coinBet", value = "总投注", example = "10000.00")
        private BigDecimal coinBet = BigDecimal.ZERO;
        @ApiModelProperty(name = "coinPayOut", value = "总派彩", example = "10000.00")
        private BigDecimal coinPayOut = BigDecimal.ZERO;
        @ApiModelProperty(name = "coinRebate", value = "总返水", example = "10000.00")
        private BigDecimal coinRebate = BigDecimal.ZERO;
        @ApiModelProperty(name = "coinCommission", value = "总佣金", example = "10000.00")
        private BigDecimal coinCommission = BigDecimal.ZERO;
        @ApiModelProperty(name = "coinRewards", value = "总活动", example = "10000.00")
        private BigDecimal coinRewards = BigDecimal.ZERO;
        @ApiModelProperty(name = "coinReconciliation", value = "总调账", example = "10000.00")
        private BigDecimal coinReconciliation = BigDecimal.ZERO;
    }
}
