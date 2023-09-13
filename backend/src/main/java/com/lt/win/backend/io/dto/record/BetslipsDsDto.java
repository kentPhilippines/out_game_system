package com.lt.win.backend.io.dto.record;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * DS注单表(棋牌)
 * </p>
 *
 * @author David
 * @since 2020-08-10
 */
@Data
@ApiModel(value = "ds", description = "ds注单")
public class BetslipsDsDto {


    @ApiModelProperty(name = "id", value = "下注編號", example = "10000")
    private String id;


    @ApiModelProperty(name = "xb_uid", value = "用户id", example = "10000")
    private Integer xbUid;


    @ApiModelProperty(name = "xb_username", value = "用户名", example = "10000")
    private String xbUsername;


    @ApiModelProperty(name = "bet_at", value = "下注時間", example = "10000")
    private Date betAt;


    @ApiModelProperty(name = "finish_at", value = "结算时间", example = "10000")
    private Date finishAt;


    @ApiModelProperty(name = "agent", value = "代理帳號", example = "10000")
    private String agent;


    @ApiModelProperty(name = "member", value = "玩家帳號", example = "10000")
    private String member;


    @ApiModelProperty(name = "game_id", value = "遊戲編號", example = "10000")
    private String gameId;

    @ApiModelProperty(name = "game_serial", value = "遊戲流水號", example = "10000")
    private String gameSerial;


    @ApiModelProperty(name = "game_type", value = "遊戲類型", example = "10000")
    private Integer gameType;


    @ApiModelProperty(name = "round_id", value = "遊戲回合id", example = "10000")
    private Integer roundId;


    @ApiModelProperty(name = "bet_amount", value = "下注金額", example = "10000")
    private BigDecimal betAmount;


    @ApiModelProperty(name = "payout_amount", value = "遊戲贏分(未扣除手續費)", example = "10000")
    private BigDecimal payoutAmount;


    @ApiModelProperty(name = "valid_amount", value = "有效金額", example = "10000")
    private BigDecimal validAmount;


    @ApiModelProperty(name = "status", value = "下注狀態", example = ":1-正常 2-退款 3-拒絕投注 4-注單作廢 5-取消")
    private Integer status;


    @ApiModelProperty(name = "fee_amount", value = "手續費", example = "10000")
    private BigDecimal feeAmount;


    @ApiModelProperty(name = "jp_amount", value = "彩金金額", example = "10000")
    private BigDecimal jpAmount;


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
