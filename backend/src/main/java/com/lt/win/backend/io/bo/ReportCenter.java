package com.lt.win.backend.io.bo;

import com.lt.win.service.io.bo.UserCacheBo;
import com.lt.win.utils.components.pagination.BasePage;
import com.lt.win.utils.components.pagination.ResPage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 报表中心
 * </p>
 *
 * @author andy
 * @since 2020/6/15
 */
public interface ReportCenter {
    /**
     * 主账户综合报表-请求body
     */
    @Data
    class ListMasterAccountReqBody extends StartEndTime {
        @ApiModelProperty(name = "uid", value = "UID", example = "84")
        private Integer uid;
        @ApiModelProperty(name = "username", value = "用户名", example = "2")
        private String username;
    }

    /**
     * 主账户综合报表-下级-请求body
     */
    @Data
    class MasterAccountListOfSubordinateReqBody extends BasePage {
        @NotNull(message = "uid不能为空")
        @ApiModelProperty(name = "uid", value = "UID", example = "84", required = true)
        private Integer uid;
    }

    /**
     * 各平台盈利排行版-请求body
     */
    @Data
    class PlatformLeaderBoardReqBody extends StartEndTime {
        @NotNull(message = "gameListId不能为空")
        @ApiModelProperty(name = "gameListId", value = "游戏ID:异步调用", example = "101", required = true)
        private Integer gameListId;
    }

    /**
     * 主账户综合报表-响应body
     */
    @Data
    @Builder
    class ListMasterAccountResBody {
        @ApiModelProperty(name = "list", value = "列表")
        private ResPage<MasterAccountList> list;
        @ApiModelProperty(name = "statistics", value = "统计")
        private MasterAccountStatistics statistics;

    }

    /**
     * 主账户综合报表-列表-实体
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    class MasterAccountList {
        @ApiModelProperty(name = "UID", value = "UID", example = "84")
        private Integer uid;
        @ApiModelProperty(name = "username", value = "用户名", example = "2")
        private String username;

        @ApiModelProperty(name = "coinDeposit", value = "总存款", example = "10000.00")
        private BigDecimal coinDeposit;
        @ApiModelProperty(name = "coinWithdrawal", value = "总提款", example = "10000.00")
        private BigDecimal coinWithdrawal;
        @ApiModelProperty(name = "coinUp", value = "总上方", example = "10000.00")
        private BigDecimal coinUp;
        @ApiModelProperty(name = "coinDown", value = "总下方", example = "10000.00")
        private BigDecimal coinDown;

        @ApiModelProperty(name = "userFlagList", value = "会员旗列表")
        private List<UserCacheBo.UserFlagInfo> userFlagList;
    }

    /**
     * 主账户综合报表-统计-实体
     */
    @Data
    @Builder
    class MasterAccountStatistics {
        @ApiModelProperty(name = "coinDeposit", value = "总存款", example = "10000.00")
        private BigDecimal coinDeposit;
        @ApiModelProperty(name = "coinWithdrawal", value = "总提款", example = "10000.00")
        private BigDecimal coinWithdrawal;
        @ApiModelProperty(name = "coinUp", value = "总上方", example = "10000.00")
        private BigDecimal coinUp;
        @ApiModelProperty(name = "coinDown", value = "总下方", example = "10000.00")
        private BigDecimal coinDown;
    }

    /**
     * 各平台盈利排行版-响应body
     */
    @Data
    class PlatformLeaderBoardResBody {
        @ApiModelProperty(name = "username", value = "用户名", example = "2")
        private String username;
        @ApiModelProperty(name = "uid", value = "UID")
        private Integer uid;
        @ApiModelProperty(name = "profit", value = "盈利", example = "10000.00")
        private BigDecimal profit;
    }

    /**
     * 每日报表-列表-响应body
     */
    @Data
    class DailyConversionReportListResBody {
        @ApiModelProperty(name = "id", value = "统计日期", example = "20200801")
        private Integer id;

        @ApiModelProperty(name = "registeredNum", value = "注册人数", example = "1289")
        private Integer registeredNum;

        @ApiModelProperty(name = "openedAccountNum", value = "开户人数", example = "33")
        private Integer openedAccountNum;
        /**
         * 转化率(开户人数/注册人数)*100
         */
        @ApiModelProperty(name = "conversionRates", value = "转化率", example = "2.56")
        private BigDecimal conversionRates;



        @ApiModelProperty(name = "firstDepositCoin", value = "首存金额", example = "10050.00")
        private BigDecimal firstDepositCoin;

        @ApiModelProperty(name = "firstDepositCount", value = "首存笔数", example = "33")
        private Integer firstDepositCount;

        @ApiModelProperty(name = "secondDepositCoin", value = "二存金额", example = "61997.00")
        private BigDecimal secondDepositCoin;

        @ApiModelProperty(name = "secondDepositCount", value = "二存笔数", example = "97")
        private Integer secondDepositCount;



        @ApiModelProperty(name = "depositCoin", value = "存款金额", example = "6858215.00")
        private BigDecimal depositCoin;

        @ApiModelProperty(name = "depositCount", value = "存款笔数", example = "5930")
        private Integer depositCount;

        @ApiModelProperty(name = "depositNum", value = "存款人数", example = "2527")
        private Integer depositNum;

        @ApiModelProperty(name = "withdrawalCoin", value = "提款金额", example = "7031001.00")
        private BigDecimal withdrawalCoin;

        @ApiModelProperty(name = "withdrawalCount", value = "提款笔数", example = "2764")
        private Integer withdrawalCount;

        @ApiModelProperty(name = "withdrawalNum", value = "提款人数", example = "1364")
        private Integer withdrawalNum;

        @ApiModelProperty(name = "profit", value = "盈亏金额(存款-提款)", example = "172786.00")
        private BigDecimal profit;

        @ApiModelProperty(name = "firstDepositUidList", hidden = true)
        private List<Integer> firstDepositUidList;
    }
    /**
     * 每日报表-统计-响应body
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    class DailyConversionReportStatisticsResBody {

        @ApiModelProperty(name = "registeredNum", value = "新增人数", example = "1289")
        private int registeredNum;

        @ApiModelProperty(name = "depositCoin", value = "新增存款金额", example = "6858215.00")
        private BigDecimal depositCoin;

        @ApiModelProperty(name = "withdrawalCoin", value = "新增提款金额", example = "7031001.00")
        private BigDecimal withdrawalCoin;

        @ApiModelProperty(name = "profit", value = "盈亏金额(存款-提款)", example = "172786.00")
        private BigDecimal profit;
    }

    @Data
    @ApiModel(value = "RevenueReqBody", description = "税收请求实体类")
    class RevenueReqBody {
        @ApiModelProperty(name = "startTime", value = "开始时间", example = "1600504515")
        private Integer startTime;
        @ApiModelProperty(name = "endTime", value = "结束时间", example = "1600504515")
        private Integer endTime;
    }

    @Data
    @ApiModel(value = "RevenueResBody", description = "税收响应实体类")
    class RevenueResBody {
        @ApiModelProperty(name = "gameName", value = "游戏名称", example = "GG捕鱼")
        private String gameName;
        @ApiModelProperty(name = "yearMonth", value = "年份-月份", example = "2020-01")
        private String yearMonth;
        @ApiModelProperty(name = "betCoin", value = "投注金额", example = "100.00")
        private BigDecimal betCoin;
        @ApiModelProperty(name = "validCoin", value = "有效投注金额", example = "100.00")
        private BigDecimal validCoin;
        @ApiModelProperty(name = "profitCoin", value = "输赢金额", example = "100.00")
        private BigDecimal profitCoin;
        @ApiModelProperty(name = "rate", value = "税收比例", example = "0.12")
        private String rate;
        @ApiModelProperty(name = "revenueCoin", value = "税收金额", example = "12.00")
        private BigDecimal revenueCoin;
    }


    /**
     * 每日报表->按平台统计:投注总额|输赢总额-响应body
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    class DailyReportStatisticsPlatCoinResBody {
        @ApiModelProperty(name = "groupId", value = "游戏组ID", example = "1")
        private Integer groupId;

        @ApiModelProperty(name = "groupName", value = "游戏组名称:体育|电子|真人|捕鱼|棋牌|电竞|彩票|动物", example = "体育")
        private String groupName;

        @ApiModelProperty(name = "betCoin", value = "投注金额", example = "100.00")
        private BigDecimal betCoin;
        @ApiModelProperty(name = "profitCoin", value = "盈亏金额", example = "100.00")
        private BigDecimal profitCoin;
    }

    /**
     * 每日报表->投注/充值/提现(笔数/人数)统计-响应body
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    class DailyReportStatisticsCountResBody {
        @ApiModelProperty(name = "betCount", value = "投注笔数", example = "10000")
        private long betCount;
        @ApiModelProperty(name = "betUserCount", value = "投注人数", example = "500")
        private long betUserCount;
        @ApiModelProperty(name = "depositCount", value = "充值笔数", example = "10000")
        private long depositCount;
        @ApiModelProperty(name = "depositUserCount", value = "充值人数", example = "500")
        private long depositUserCount;
        @ApiModelProperty(name = "withdrawalCount", value = "提现笔数", example = "10000")
        private long withdrawalCount;
        @ApiModelProperty(name = "withdrawalUserCount", value = "提现人数", example = "500")
        private long withdrawalUserCount;
    }

    /**
     * 每日报表->ReqBody
     */
    @Data
    class DailyReportStatisticsReqBody extends StartEndTime {
        @ApiModelProperty(name = "username", value = "用户名", example = "2")
        private String username;

        @ApiModelProperty(name = "type", value = "类型:1-搜索代理线 2-搜索指定用户", example = "1")
        private Integer type;
    }

    /**
     * 各平台盈亏报表->ReqBody
     */
    @Data
    class PlatformProfitListReqBody extends StartEndTime {
        @ApiModelProperty(name = "groupId", value = "游戏组ID", example = "1")
        private Integer groupId;
        @ApiModelProperty(name = "gameListId", value = "平台ID", example = "703")
        private Integer gameListId;
        @ApiModelProperty(name = "gameId", value = "游戏ID", example = "100")
        private String gameId;
        @ApiModelProperty(name = "levelId", value = "会员等级ID", example = "1")
        private Integer levelId;
        @ApiModelProperty(name = "username", value = "用户名", example = "2")
        private String username;
        @ApiModelProperty(name = "type", value = "类型:1-搜索代理线 2-搜索指定用户", example = "1")
        private Integer type;
    }

    /**
     * 各平台盈亏报表->ReqBody
     */
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    class PlatformProfitListResBody {
        @ApiModelProperty(name = "username", value = "用户名", example = "andy77777")
        private String username;
        @ApiModelProperty(name = "uid", value = "uid", example = "1")
        private Integer uid;
        @ApiModelProperty(name = "groupName", value = "游戏组名称", example = "彩票")
        private String groupName;
        @ApiModelProperty(name = "gameListName", value = "平台名称", example = "Futures")
        private String gameListName;

        @ApiModelProperty(name = "profit", value = "盈亏金额", example = "10000.00")
        private BigDecimal profit;

        @ApiModelProperty(name = "createdAt", value = "注册时间", example = "10000.00")
        private Integer createdAt;

        @ApiModelProperty(name = "levelText", value = "会员等级:vip1-乒乓球达人", example = "vip1-乒乓球达人")
        private String levelText;

        @ApiModelProperty(name = "typeName", value = "会员类型", example = "会员类型")
        private String typeName;
    }

    /**
     * 各平台盈亏报表->统计-响应body
     */
    @Data
    class PlatformProfitResBody {
        @ApiModelProperty(name = "totalProfit", value = "总盈亏", example = "10000.00")
        private BigDecimal totalProfit;
    }
}
