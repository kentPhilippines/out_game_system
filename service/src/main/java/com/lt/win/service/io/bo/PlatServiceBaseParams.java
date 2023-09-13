package com.lt.win.service.io.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author: David
 * @date: 13/07/2020
 */
public interface PlatServiceBaseParams {
    /**
     * 插入上下分记录
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    class InsertCoinPlatRecordsReqDto {
        @ApiModelProperty(name = "uid", value = "会员ID", example = "10000")
        public Integer uid;
        @ApiModelProperty(name = "direction", value = "上、下分方向", example = "1")
        public Integer direction;
        @ApiModelProperty(name = "platId", value = "平台ID")
        public Integer platId;
        @ApiModelProperty(name = "coin", value = "金额", example = "100.00")
        private BigDecimal coin;
        @ApiModelProperty(name = "IsFullAmount", value = "金额:0-金额下分 1-全部下分", example = "0")
        private Integer isFullAmount;
    }

    /**
     * 插入上下分记录
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    class InsertCoinPlatRecordsResDto {
        @ApiModelProperty(name = "orderId", value = "订单号", example = "1")
        public Long orderId;
        @ApiModelProperty(name = "coin", value = "余额")
        public BigDecimal coin;
    }

    /**
     * 修改用户金额 入参
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    class UpdateCoinReqDto {
        @ApiModelProperty(name = "uid", value = "uid", example = "1")
        public Integer uid;
        @ApiModelProperty(name = "category", value = "uid", example = "1")
        public Integer category;
        @ApiModelProperty(name = "referId", value = "余额")
        public Long referId;
        @ApiModelProperty(name = "coin", value = "余额")
        public BigDecimal coin;
        @ApiModelProperty(name = "status", value = "余额")
        public Integer status;
        @ApiModelProperty(name = "outIn", value = "收支类型:0-支出 1-收入")
        public Integer outIn;
    }

    /**
     * 平台按日期查询投注金额、盈亏金额
     */
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    class PlatCoinStatisticsResDto {
        @ApiModelProperty(name = "coin", value = "投注金额", example = "200")
        private BigDecimal coin;
        @ApiModelProperty(name = "coinBet", value = "有效投注金额", example = "100")
        private BigDecimal coinBet;
        @ApiModelProperty(name = "coinProfit", value = "盈亏金额", example = "100.00")
        private BigDecimal coinProfit;
    }

    @Data
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    class PlatGameQueryDateDto {
        @ApiModelProperty(name = "startTime", value = "开始时间")
        @NotNull(message = "startTime不能为空")
        private Integer startTime;

        @ApiModelProperty(name = "endTime", value = "结束时间")
        @NotNull(message = "endTime不能为空")
        private Integer endTime;

        @ApiModelProperty(name = "uid", value = "用户ID", example = "1")
        private Integer uid;

        @ApiModelProperty(name = "uids", value = "用户集", example = "")
        private List<Integer> uidList;

        @ApiModelProperty(name = "gameId", value = "", example = "1")
        private Integer gameId;

        @ApiModelProperty(name = "platId", value = "", example = "1")
        private Integer platId;
    }
}
