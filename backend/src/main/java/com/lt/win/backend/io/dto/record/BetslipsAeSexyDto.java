package com.lt.win.backend.io.dto.record;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>
 * AE Sexy注单表
 * </p>
 *
 * @author David
 * @since 2020-08-16
 */
@Data
@ApiModel(value = "aeSexy", description = "aeSexy注单")
public class BetslipsAeSexyDto {

    @ApiModelProperty(name = "id", value = "订单号", example = "platform_tx_id")
    private String id;

    @ApiModelProperty(name = "ae_id", value = "ID 流水号(非唯一)", example = "10000")
    private Integer aeId;

    @ApiModelProperty(name = "user_id", value = "平台账号", example = "10000")
    private String userId;

    @ApiModelProperty(name = "platform", value = "平台类型", example = "10000")
    private String platform;

    @ApiModelProperty(name = "game_code", value = "游戏编码", example = "10000")
    private String gameCode;

    @ApiModelProperty(name = "game_type", value = "游戏类型", example = "10000")
    private String gameType;

    @ApiModelProperty(name = "bet_type", value = "投注类型", example = "10000")
    private String betType;

    @ApiModelProperty(name = "tx_time", value = "订单时间", example = "10000")
    private String txTime;

    @ApiModelProperty(name = "bet_amount", value = "投注金额", example = "10000")
    private BigDecimal betAmount;

    @ApiModelProperty(name = "win_amount", value = "派彩金额", example = "10000")
    private BigDecimal winAmount;

    @ApiModelProperty(name = "turnover", value = "有效注额(同局号多笔下注记录在首笔)", example = "10000")
    private BigDecimal turnover;

    @ApiModelProperty(name = "tx_status", value = "订单状态", example = "10000")
    private Integer txStatus;

    @ApiModelProperty(name = "real_bet_amount", value = "真实投注金额", example = "10000")
    private BigDecimal realBetAmount;

    @ApiModelProperty(name = "real_win_amount", value = "实际赢取金额", example = "10000")
    private BigDecimal realWinAmount;

    @ApiModelProperty(name = "jackpot_bet_amount", value = "奖池投注金额", example = "10000")
    private BigDecimal jackpotBetAmount;

    @ApiModelProperty(name = "jackpot_win_amount", value = "奖池派彩金额", example = "10000")
    private BigDecimal jackpotWinAmount;

    @ApiModelProperty(name = "currency", value = "货币", example = "10000")
    private String currency;

    @ApiModelProperty(name = "update_time", value = "更新时间", example = "10000")
    private String updateTime;

    @ApiModelProperty(name = "round_id", value = "局号", example = "10000")
    private String roundId;


    @ApiModelProperty(name = "game_info", value = "游戏信息", example = "10000")
    private String gameInfo;

    @ApiModelProperty(name = "settle_status", value = "结算状态", example = "10000")
    private Integer settleStatus;

    @ApiModelProperty(name = "xb_uid", value = "对应user表id", example = "10000")
    private Integer xbUid;

    @ApiModelProperty(name = "xb_username", value = "对应user表username", example = "10000")
    private String xbUsername;

    @ApiModelProperty(name = "xb_coin", value = "投注金额", example = "10000")
    private BigDecimal xbCoin;

    @ApiModelProperty(name = "xb_valid_coin", value = "有效投注额", example = "10000")
    private BigDecimal xbValidCoin;

    @ApiModelProperty(name = "xb_profit", value = "盈亏金额", example = "10000")
    private BigDecimal xbProfit;

    @ApiModelProperty(name = "xb_status", value = "注单状态", example = "10000")
    private Integer xbStatus;

    @ApiModelProperty(name = "created_at", value = "创建时间", example = "10000")
    private Integer createdAt;

    @ApiModelProperty(name = "updated_at", value = "更新时间", example = "10000")
    private Integer updatedAt;

}
