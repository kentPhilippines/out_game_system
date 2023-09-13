package com.lt.win.backend.io.dto.record;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * DG注单表
 * </p>
 *
 * @author David
 * @since 2020-08-10
 */
@Data
@ApiModel(value = "dg", description = "dg注单")
public class BetslipsDgDto {

    @ApiModelProperty(name = "id", value = "注单号", example = "10000")
    private Long id;

    @ApiModelProperty(name = "xb_uid", value = "用户id", example = "10000")
    private Integer xbUid;

    @ApiModelProperty(name = "xb_username", value = "对应user表username", example = "10000")
    private String xbUsername;

    @ApiModelProperty(name = "username", value = "会员账号", example = "10000")
    private String username;

    @ApiModelProperty(name = "lobby_id", value = "游戏大厅号", example = "1,2-旗舰厅 3,4-现场厅 5-联盟厅 7-国际厅")
    private Integer lobbyId;

    @ApiModelProperty(name = "table_id", value = "游戏桌号", example = "10000")
    private Integer tableId;

    @ApiModelProperty(name = "shoe_id", value = "游戏靴号", example = "10000")
    private Long shoeId;

    @ApiModelProperty(name = "play_id", value = "游戏局号", example = "10000")
    private Long playId;

    @ApiModelProperty(name = "game_type", value = "游戏类型", example = "10000")
    private Integer gameType;

    @ApiModelProperty(name = "game_id", value = "游戏Id", example = "10000")
    private Integer gameId;

    @ApiModelProperty(name = "member_id", value = "会员Id", example = "10000")
    private Long memberId;

    @ApiModelProperty(name = "bet_time", value = "游戏下注时间", example = "10000")
    private Date betTime;

    @ApiModelProperty(name = "cal_time", value = "游戏结算时间", example = "10000")
    private Date calTime;

    @ApiModelProperty(name = "win_or_Loss", value = "派彩金额", example = " (输赢应扣除下注金额),总派彩金额")
    private BigDecimal winOrLoss;

    @ApiModelProperty(name = "win_or_lossz", value = "好路追注派彩金额", example = "10000")
    private BigDecimal winOrLossz;

    @ApiModelProperty(name = "bet_points", value = "下注金额(总金额)", example = "10000")
    private BigDecimal betPoints;

    @ApiModelProperty(name = "bet_pointsz", value = "好路追注金额", example = "10000")
    private BigDecimal betPointsz;

    @ApiModelProperty(name = "available_bet", value = "有效下注金额", example = "10000")
    private BigDecimal availableBet;

    @ApiModelProperty(name = "result", value = "游戏结果", example = "10000")
    private String result;

    @ApiModelProperty(name = "bet_detail", value = "下注注单(总单)", example = "10000")
    private String betDetail;

    @ApiModelProperty(name = "bet_detailz", value = "好路追注注单", example = "10000")
    private String betDetailz;

    @ApiModelProperty(name = "ip", value = "下注时客户端IP", example = "10000")
    private String ip;

    @ApiModelProperty(name = "ext", value = "游戏唯一ID", example = "10000")
    private String ext;

    @ApiModelProperty(name = "is_revocation", value = "是否结算", example = "0-未结算 1-已结算 2-已撤销(该注单为对冲注单)")
    private Integer isRevocation;

    @ApiModelProperty(name = "balance_before", value = "余额", example = "10000")
    private BigDecimal balanceBefore;

    @ApiModelProperty(name = "parent_bet_id", value = "撤销的那比注单的ID(对冲注单才有)", example = "10000")
    private Long parentBetId;

    @ApiModelProperty(name = "currency_id", value = "货币ID", example = "10000")
    private Integer currencyId;

    @ApiModelProperty(name = "device_type", value = "下注时客户端类", example = "10000")
    private Integer deviceType;

    @ApiModelProperty(name = "plugin_id", value = "追注转账流水号(共享钱包API可用于对账,普通转账API可忽略)", example = "10000")
    private Long pluginId;

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
