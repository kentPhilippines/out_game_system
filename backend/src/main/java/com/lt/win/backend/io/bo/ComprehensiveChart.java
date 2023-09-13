package com.lt.win.backend.io.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 报表中心-综合走势
 * </p>
 *
 * @author andy
 * @since 2020/6/16
 */
public interface ComprehensiveChart {
    /**
     * 综合走势图-新增会员数 响应body
     */
    @Data
    @Builder
    class TotalUserCountResBody {
        @ApiModelProperty(name = "count", value = "新增会员数", example = "10000")
        private Integer count;
    }

    /**
     * 综合走势图-充值|提现|盈亏  响应body
     */
    @Data
    @Builder
    class TotalPlatProfitResBody {
        @ApiModelProperty(name = "coinDeposit", value = "存款总额", example = "10000.00")
        private BigDecimal coinDeposit;
        @ApiModelProperty(name = "coinWithdrawal", value = "提款总额", example = "5000.00")
        private BigDecimal coinWithdrawal;
        @ApiModelProperty(name = "profit", value = "盈亏金额", example = "5000.00")
        private BigDecimal profit;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    class ListChart {
        @ApiModelProperty(name = "name", value = "月份")
        private String name;
        @ApiModelProperty(name = "count", value = "数量")
        private Integer count;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    class ListCoinChart {
        @ApiModelProperty(name = "name", value = "名称")
        private String name;
        @ApiModelProperty(name = "coin", value = "金额")
        private BigDecimal coin;
    }

    @Data
    class DepositAndWithdrawalChart {
        @ApiModelProperty(name = "deposit", value = "充值")
        private List<ListCoinChart> deposit;
        @ApiModelProperty(name = "deposit", value = "提现")
        private List<ListCoinChart> withdrawal;
    }

    /**
     * 综合走势图-游戏盈亏与投注注数 请求body
     */
    @Data
    class PlatProfitAndBetCountChartReqBody extends StartEndTime {
        @ApiModelProperty(name = "gameListId", value = "游戏ID", example = "101")
        private Integer gameListId;
    }

    /**
     * 综合走势图-游戏盈亏与投注注数 响应body
     */
    @Data
    @Builder
    class PlatProfitAndBetCountChartResBody {
        @ApiModelProperty(name = "betCountList", value = "投注注数列表")
        private List<ListChart> betCountList;
        @ApiModelProperty(name = "betUserCountList", value = "投注人数列表")
        private List<ListChart> betUserCountList;
        @ApiModelProperty(name = "profitList", value = "游戏盈亏列表")
        private List<ListCoinChart> profitList;
    }

    @Data
    @Builder
    class PlatBetCoinChartResBody {
        @ApiModelProperty(name = "totalBetCoin", value = "总投注额", example = "1000.00")
        private BigDecimal totalBetCoin;

        @ApiModelProperty(name = "betCoinList", value = "投注注列表")
        private List<ListCoinChart> betCoinList;
    }

    @Data
    @Builder
    class PlatBetCoinChartReqBody extends StartEndTime {
        @ApiModelProperty(name = "uid", value = "UID", example = "1")
        private Integer uid;
    }

    /**
     * 平台投注、盈亏金额BO
     */
    @Data
    @Builder
    class PlatCoinBO {
        private BigDecimal game101;
        private BigDecimal game102;
        private BigDecimal game104;

        private BigDecimal game201;
        private BigDecimal game202;

        private BigDecimal game301;
        private BigDecimal game302;
        private BigDecimal game303;

        private BigDecimal game401;
        private BigDecimal game402;
        private BigDecimal game403;

        private BigDecimal game504;
        private BigDecimal game701;
    }

    /**
     * 平台投注数BO
     */
    @Data
    @Builder
    class PlatCountBO {
        private Integer game101;
        private Integer game102;
        private Integer game104;

        private Integer game201;
        private Integer game202;

        private Integer game301;
        private Integer game302;
        private Integer game303;

        private Integer game401;
        private Integer game402;
        private Integer game403;

        private Integer game504;
        private Integer game701;
    }
}
