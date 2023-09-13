package com.lt.win.backend.io.dto.record;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * Joker_Jackpot注单表
 * </p>
 *
 * @author David
 * @since 2020-11-09
 */
@Data
@ApiModel(value = "Sv388", description = "Sv388注单")
public class BetslipsSv388Dto {

    @ApiModelProperty(name = "id", value = "注单号", example = "")
    private String id;
    @ApiModelProperty(name = "xb_uid", value = "对应user表id", example = "")
    private Integer xbUid;

    @ApiModelProperty(name = "xb_username", value = "对应user表username", example = "")
    private String xbUsername;

    @ApiModelProperty(name = "ref_no", value = "供应商注单号", example = "")
    private String refNo;

    @ApiModelProperty(name = "site", value = "供应商代号", example = "")
    private String site;

    @ApiModelProperty(name = "product", value = "游戏类型代号", example = "")
    private String product;

    @ApiModelProperty(name = "member", value = "Username 玩家帐号", example = "")
    private String member;

    @ApiModelProperty(name = "game_id", value = "游戏代号", example = "")
    private String gameId;

    @ApiModelProperty(name = "start_time", value = "游戏开始时间", example = "")
    private Date startTime;

    @ApiModelProperty(name = "match_time", value = "游戏结束时间", example = "")
    private Date matchTime;

    @ApiModelProperty(name = "end_time", value = "游戏结束时间", example = "")
    private Date endTime;

    @ApiModelProperty(name = "bet_detail", value = "游戏编号名称", example = "")
    private String betDetail;

    @ApiModelProperty(name = "turnover", value = "有效投注金额", example = "")
    private BigDecimal turnover;

    @ApiModelProperty(name = "bet", value = "投注金额", example = "")
    private BigDecimal bet;

    @ApiModelProperty(name = "payout", value = "派彩金额", example = "")
    private BigDecimal payout;

    @ApiModelProperty(name = "commission", value = "佣金", example = "")
    private BigDecimal commission;

    @ApiModelProperty(name = "p_share", value = "彩池投注金额", example = "")
    private BigDecimal pShare;

    @ApiModelProperty(name = "p_win", value = "彩池派彩金额", example = "")
    private BigDecimal pWin;

    @ApiModelProperty(name = "status", value = "注单状态", example = "")
    private Integer status;

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