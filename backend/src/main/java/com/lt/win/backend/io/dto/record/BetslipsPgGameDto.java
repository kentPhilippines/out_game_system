package com.lt.win.backend.io.dto.record;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>
 * PG棋牌注单表
 * </p>
 *
 * @author David
 * @since 2020-12-24
 */
@Data
@ApiModel(value = "pg_game", description = "pg电子注单")
public class BetslipsPgGameDto {

    @ApiModelProperty(name = "id", value = "betId订单号", example = "")
    private String id;

    @ApiModelProperty(name = "xb_uid", value = "对应user表id", example = "")
    private Integer xbUid;

    @ApiModelProperty(name = "xb_username", value = "对应user表username", example = "")
    private String xbUsername;

    @ApiModelProperty(name = "bet_id", value = "主注单id", example = "")
    private String betId;

    @ApiModelProperty(name = "player_name", value = "玩家姓名", example = "")
    private String playerName;

    @ApiModelProperty(name = "game_id", value = "游戏id", example = "")
    private Integer gameId;

    @ApiModelProperty(name = "bet_type", value = "下注类型-1真钱", example = "")
    private Integer betType;

    @ApiModelProperty(name = "transaction_type", value = "交易类型 1: Cash,2: Bonus,3: Free game", example = "")
    private Integer transactionType;

    @ApiModelProperty(name = "platform", value = "平台", example = "")
    private Integer platform;

    @ApiModelProperty(name = "currency", value = "货币类型", example = "")
    private String currency;

    @ApiModelProperty(name = "bet_amount", value = "下注金额", example = "")
    private BigDecimal betAmount;

    @ApiModelProperty(name = "win_amount", value = "派彩金额", example = "")
    private BigDecimal winAmount;

    @ApiModelProperty(name = "jackpot_contribution_amount", value = "奖池贡献金额", example = "")
    private BigDecimal jackpotContributionAmount;

    @ApiModelProperty(name = "jackpot_win_amount", value = "奖池赢取金额", example = "")
    private BigDecimal jackpotWinAmount;

    @ApiModelProperty(name = "balance_before", value = "下注前金额", example = "")
    private BigDecimal balanceBefore;


    @ApiModelProperty(name = "balance_after", value = "下注后金额", example = "")
    private BigDecimal balanceAfter;

    @ApiModelProperty(name = "hands_status", value = "1: Non-last hand 2: Last hand 3: Adjusted", example = "")
    private Integer handsStatus;

    @ApiModelProperty(name = "row_version", value = "数据更新时间", example = "")
    private String rowVersion;

    @ApiModelProperty(name = "betTime", value = "下注时间", example = "")
    private String betTime;

    @ApiModelProperty(name = "betEndTime", value = "结算时间", example = "")
    private String betEndTime;

    @ApiModelProperty(name = "xb_coin", value = "投注金额", example = "")
    private BigDecimal xbCoin;

    @ApiModelProperty(name = "xb_valid_coin", value = "有效投注额", example = "")
    private BigDecimal xbValidCoin;

    @ApiModelProperty(name = "xb_profit", value = "盈亏金额", example = "")
    private BigDecimal xbProfit;

    @ApiModelProperty(name = "xb_status", value = "注单状态", example = "")
    private Integer xbStatus;

    @ApiModelProperty(name = "created_at", value = "创建时间", example = "")
    private Integer createdAt;

    @ApiModelProperty(name = "updated_at", value = "更新时间", example = "")
    private Integer updatedAt;


}
