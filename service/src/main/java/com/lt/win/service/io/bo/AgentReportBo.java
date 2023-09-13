package com.lt.win.service.io.bo;

import com.lt.win.service.io.dto.BaseParams;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 代理下级列表
 * </p>
 *
 * @author david
 * @since 2020/7/24
 */
public interface AgentReportBo {
    /**
     * 代理下级列表缓存信息
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    class SubordinateList {
        @ApiModelProperty(name = "level1", value = "等级1", example = "100")
        private List<Integer> level1;
        @ApiModelProperty(name = "level2", value = "等级2", example = "100")
        private List<Integer> level2;
        @ApiModelProperty(name = "level3", value = "等级3", example = "100")
        private List<Integer> level3;
        @ApiModelProperty(name = "level4", value = "等级4", example = "100")
        private List<Integer> level4;
        @ApiModelProperty(name = "level5", value = "等级5", example = "100")
        private List<Integer> level5;
        @ApiModelProperty(name = "level6", value = "等级6", example = "100")
        private List<Integer> level6;
        @ApiModelProperty(name = "team", value = "团队(总)", example = "100")
        private List<Integer> team;
        @ApiModelProperty(name = "other", value = "其他(直属、二级、三级以外)", example = "100")
        private List<Integer> other;
    }

    /**
     * 代理盈亏报表返回实体
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    class TeamBenefitsResDto {
        @ApiModelProperty(name = "todayIncreaseAgentNum", value = "今日新增代理", example = "100")
        private Integer todayIncreaseAgentNum;
        @ApiModelProperty(name = "username", value = "总代理人数", example = "100")
        private Integer totalAgentNum;
        @ApiModelProperty(name = "totalCommission", value = "总佣金", example = "100")
        private BigDecimal totalCommission;
        @ApiModelProperty(name = "allowWithdrawal", value = "佣金可提现金额", example = "100")
        private BigDecimal allowWithdrawal;
    }

    /**
     * 代理中心下六级佣金明细
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    class SubordinateDetailsResDto {
        @ApiModelProperty(name = "level1", value = "下1级")
        SubordinateDetailsResSubDto level1;
        @ApiModelProperty(name = "level2", value = "下2级")
        SubordinateDetailsResSubDto level2;
        @ApiModelProperty(name = "level3", value = "下3级")
        SubordinateDetailsResSubDto level3;
        @ApiModelProperty(name = "level4", value = "下4级")
        SubordinateDetailsResSubDto level4;
        @ApiModelProperty(name = "level5", value = "下5级")
        SubordinateDetailsResSubDto level5;
        @ApiModelProperty(name = "level6", value = "下6级")
        SubordinateDetailsResSubDto level6;
        @ApiModelProperty(name = "commission", value = "佣金金额", example = "100")
        private BigDecimal commission;
        @ApiModelProperty(name = "bettingTurnOver", value = "投注流水", example = "100")
        private BigDecimal bettingTurnOver;
    }

    /**
     * 代理中心下六级佣金明细
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    class SubordinateDetailsResSubDto {
        @ApiModelProperty(name = "nums", value = "代理人数", example = "100")
        private Integer nums;
        @ApiModelProperty(name = "commission", value = "佣金金额", example = "100")
        private BigDecimal commission;
        @ApiModelProperty(name = "bettingTurnOver", value = "投注流水", example = "100")
        private BigDecimal bettingTurnOver;
    }

    /**
     * 代理盈亏报表返回实体
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    class CommissionRecordsResDto {
        @ApiModelProperty(name = "username", value = "用户名", example = "100")
        private String username;
        @ApiModelProperty(name = "agentLevel", value = "代理等级", example = "100")
        private Integer agentLevel;
        @ApiModelProperty(name = "bettingTurnOver", value = "投注金额", example = "100")
        private BigDecimal bettingTurnOver;
        @ApiModelProperty(name = "commissionUsd", value = "佣金金额", example = "100")
        private BigDecimal commissionUsd;
        @ApiModelProperty(name = "currency", value = "货币", example = "100")
        private String currency;
        @ApiModelProperty(name = "createdAt", value = "创建时间", example = "100")
        private Integer createdAt;
        @ApiModelProperty(name = "updatedAt", value = "更新时间", example = "100")
        private Integer updatedAt;
    }

    /**
     * 佣金转账
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    class CoinTransfer {
        @ApiModelProperty(name = "coin", value = "转账金额", example = "100")
        private BigDecimal coin;
    }

    /**
     * 报表管理 -> 代理报表请求出参
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    class AgentPropDto {
        @ApiModelProperty(name = "id", value = "ID")
        private Integer id;
        @ApiModelProperty(name = "username", value = "用户名")
        private String username;
        @ApiModelProperty(name = "supUsername1", value = "上级代理")
        private String supUsername1;
        @ApiModelProperty(name = "status", value = "状态:10-正常 9-冻结")
        private Integer status;
        @ApiModelProperty(name = "createdAt", value = "注册时间")
        private Integer createdAt;
    }


    /**
     * 报表管理 -> 代理报表请求入参
     */
    @EqualsAndHashCode(callSuper = true)
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @SuperBuilder
    class AgentReqDto extends BaseParams.TimeParams {
        @ApiModelProperty(name = "username", value = "用户名")
        private String username;
        @ApiModelProperty(name = "supUsername1", value = "上级代理")
        private String supUsername1;
        @ApiModelProperty(name = "status", value = "状态:10-正常 9-冻结")
        private Integer status;
        @ApiModelProperty(name = "ids", value = "ids", hidden = true)
        private List<Integer> ids;
    }

    /**
     * 报表管理 -> 代理报表请求出参
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    class AgentResDto {
        @ApiModelProperty(name = "id", value = "ID")
        private Integer id;
        @ApiModelProperty(name = "username", value = "用户名")
        private String username;
        @ApiModelProperty(name = "supUsername1", value = "上级代理")
        private String supUsername1;
        @ApiModelProperty(name = "coinUsd", value = "USD金额")
        private BigDecimal coinUsd;
        @ApiModelProperty(name = "coinBrl", value = "Brl金额")
        private BigDecimal coinBrl;
        @ApiModelProperty(name = "status", value = "状态:10-正常 9-冻结")
        private Integer status;
        @ApiModelProperty(name = "teamNums", value = "团队人数")
        private Integer teamNums;
        @ApiModelProperty(name = "directNums", value = "直属人数")
        private Integer directNums;
        @ApiModelProperty(name = "coinCommission", value = "返佣金额")
        private BigDecimal coinCommission = BigDecimal.ZERO;
        @ApiModelProperty(name = "coinBalanceUsd", value = "团队总余额USD")
        private BigDecimal coinBalanceUsd = BigDecimal.ZERO;
        @ApiModelProperty(name = "coinBalanceBrl", value = "团队总余额BRL")
        private BigDecimal coinBalanceBrl = BigDecimal.ZERO;
        @ApiModelProperty(name = "coinDepositUsd", value = "团队总充值USD")
        private BigDecimal coinDepositUsd = BigDecimal.ZERO;
        @ApiModelProperty(name = "coinDepositBrl", value = "团队总充值BRL")
        private BigDecimal coinDepositBrl = BigDecimal.ZERO;
        @ApiModelProperty(name = "coinWithdrawalUsd", value = "团队总提现USD")
        private BigDecimal coinWithdrawalUsd = BigDecimal.ZERO;
        @ApiModelProperty(name = "coinWithdrawalBrl", value = "团队总提现BRL")
        private BigDecimal coinWithdrawalBrl = BigDecimal.ZERO;
        @ApiModelProperty(name = "coinWithdrawalReallyUsd", value = "团队实际到账")
        private BigDecimal coinWithdrawalReallyUsd = BigDecimal.ZERO;
        @ApiModelProperty(name = "coinWithdrawalReallyBrl", value = "团队实际到账")
        private BigDecimal coinWithdrawalReallyBrl = BigDecimal.ZERO;
        @ApiModelProperty(name = "coinAdjustUsd", value = "团队总调账")
        private BigDecimal coinAdjustUsd = BigDecimal.ZERO;
        @ApiModelProperty(name = "coinAdjustBrl", value = "团队总调账")
        private BigDecimal coinAdjustBrl = BigDecimal.ZERO;
        @ApiModelProperty(name = "coinBetUsd", value = "团队总投注")
        private BigDecimal coinBetUsd = BigDecimal.ZERO;
        @ApiModelProperty(name = "coinBetBrl", value = "团队总投注")
        private BigDecimal coinBetBrl = BigDecimal.ZERO;
        @ApiModelProperty(name = "coinBetBonusUsd", value = "团队总派彩")
        private BigDecimal coinBetBonusUsd = BigDecimal.ZERO;
        @ApiModelProperty(name = "coinBetBonusBrl", value = "团队总派彩")
        private BigDecimal coinBetBonusBrl = BigDecimal.ZERO;
        @ApiModelProperty(name = "coinProfitUsd", value = "总负盈利")
        private BigDecimal coinProfitUsd = BigDecimal.ZERO;
        @ApiModelProperty(name = "coinProfitBrl", value = "总负盈利")
        private BigDecimal coinProfitBrl = BigDecimal.ZERO;
        @ApiModelProperty(name = "createdAt", value = "注册时间")
        private Integer createdAt;
    }

    /**
     * 报表管理 -> 代理报表 -> 团队详情 请求入参
     */
    @EqualsAndHashCode(callSuper = true)
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @SuperBuilder
    class AgentTeamReqDto extends BaseParams.TimeParams {
        @NotNull("Can't Empty")
        @ApiModelProperty(name = "agentId", value = "代理ID(代理报表带进来)", required = true)
        private Integer agentId;
        @ApiModelProperty(name = "username", value = "用户名")
        private String username;
        @ApiModelProperty(name = "agentLevel", value = "代理层级")
        @Min(1)
        @Max(3)
        private Integer agentLevel;
        @ApiModelProperty(name = "status", value = "账号状态")
        private Integer status;
        @ApiModelProperty(name = "ids", value = "ID", hidden = true)
        private List<Integer> ids;
    }

    /**
     * 报表管理 -> 代理报表 -> 团队详情 -> 列表 请求出参
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    class AgentTeamResDto {
        @ApiModelProperty(name = "id", value = "ID")
        private Integer id;
        @ApiModelProperty(name = "username", value = "用户名")
        private String username;
        @ApiModelProperty(name = "agentLevel", value = "下级层级")
        private Integer agentLevel;
        @ApiModelProperty(name = "coin", value = "金额")
        private BigDecimal coin;
        @ApiModelProperty(name = "status", value = "账号状态")
        private Integer status;
        @ApiModelProperty(name = "createdAt", value = "注册时间")
        private Integer createdAt;
        @ApiModelProperty(name = "coinDeposit", value = "团队总充值")
        private BigDecimal coinDeposit = BigDecimal.ZERO;
        @ApiModelProperty(name = "coinWithdrawal", value = "团队总提现")
        private BigDecimal coinWithdrawal = BigDecimal.ZERO;
        @ApiModelProperty(name = "coinWithdrawalReally", value = "团队实际到账")
        private BigDecimal coinWithdrawalReally = BigDecimal.ZERO;
        @ApiModelProperty(name = "coinAdjust", value = "团队总调账")
        private BigDecimal coinAdjust = BigDecimal.ZERO;
        @ApiModelProperty(name = "coinBet", value = "团队总投注")
        private BigDecimal coinBet = BigDecimal.ZERO;
        @ApiModelProperty(name = "coinBetBonus", value = "团队总派彩")
        private BigDecimal coinBetBonus = BigDecimal.ZERO;
        @ApiModelProperty(name = "coinProfit", value = "总负盈利")
        private BigDecimal coinProfit = BigDecimal.ZERO;
    }

    /**
     * 报表管理 -> 代理报表->代理转移入参
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    class AgentTransferReqDto {
        @ApiModelProperty(name = "from", value = "被转移用户名")
        @NotNull("不能为空")
        private String from;
        @ApiModelProperty(name = "to", value = "转移至用户名")
        @NotNull("不能为空")
        private String to;
    }

    /**
     * 报表管理 -> 会员报表/代理报表(详情)-表头 请求出参
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    class MemberOrAgentTeamSummaryResDto {
        @ApiModelProperty(name = "coinDeposit", value = "团队总充值USD")
        private BigDecimal coinDeposit = BigDecimal.ZERO;
        @ApiModelProperty(name = "coinWithdrawal", value = "团队总提现USD")
        private BigDecimal coinWithdrawal = BigDecimal.ZERO;
        @ApiModelProperty(name = "coinAdjust", value = "团队总调账")
        private BigDecimal coinAdjust = BigDecimal.ZERO;
        @ApiModelProperty(name = "coinWithdrawalReally", value = "团队实际到账")
        private BigDecimal coinWithdrawalReally = BigDecimal.ZERO;
        @ApiModelProperty(name = "coinBet", value = "团队总投注")
        private BigDecimal coinBet = BigDecimal.ZERO;
        @ApiModelProperty(name = "coinBetBonus", value = "团队总派彩")
        private BigDecimal coinBetBonus = BigDecimal.ZERO;
        @ApiModelProperty(name = "coinProfit", value = "总负盈利")
        private BigDecimal coinProfit = BigDecimal.ZERO;
    }

    /**
     * 帐变按类型货币分类
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    class CoinCategoryGroupDto {
        @ApiModelProperty(name = "coin", value = "金额", example = "100")
        private BigDecimal coin = BigDecimal.ZERO;
        @ApiModelProperty(name = "category", value = "帐变类型", example = "1")
        private Integer category = 0;
        @ApiModelProperty(name = "currency", value = "币种", example = "#A2BF8B")
        private String currency;
    }

    /**
     * 交易记录-统计-实体
     */
    @Data
    class CoinLogStatistics {
        @ApiModelProperty(name = "coinDeposit", value = "存款-1", example = "10000.00")
        private BigDecimal coinDeposit = BigDecimal.ZERO;
        @ApiModelProperty(name = "coinWithdrawal", value = "提款-2", example = "10000.00")
        private BigDecimal coinWithdrawal = BigDecimal.ZERO;
        @ApiModelProperty(name = "coinBet", value = "投注-3", example = "10000.00")
        private BigDecimal coinBet = BigDecimal.ZERO;
        @ApiModelProperty(name = "coinPayOut", value = "派彩-4", example = "10000.00")
        private BigDecimal coinPayOut = BigDecimal.ZERO;
        @ApiModelProperty(name = "coinRebate", value = "返水5", example = "10000.00")
        private BigDecimal coinRebate = BigDecimal.ZERO;
        @ApiModelProperty(name = "coinCommission", value = "佣金-6", example = "10000.00")
        private BigDecimal coinCommission = BigDecimal.ZERO;
        @ApiModelProperty(name = "coinRewards", value = "活动-7", example = "10000.00")
        private BigDecimal coinRewards = BigDecimal.ZERO;
        @ApiModelProperty(name = "coinReconciliation", value = "调账-8", example = "10000.00")
        private BigDecimal coinReconciliation = BigDecimal.ZERO;
        @ApiModelProperty(name = "coinRefund", value = "退款-9", example = "10000.00")
        private BigDecimal coinRefund = BigDecimal.ZERO;
        @ApiModelProperty(name = "coinWithdrawalRefund", value = "提款退款-11", example = "10000.00")
        private BigDecimal coinWithdrawalRefund = BigDecimal.ZERO;
    }

    /**
     * 佣金统计数据发放
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    class CommissionPayoutDto {
        @ApiModelProperty(name = "coin", value = "有效投注总额", example = "100")
        private BigDecimal coin;
        @ApiModelProperty(name = "uid", value = "用户Uid", example = "100")
        private Integer uid;
    }
}
