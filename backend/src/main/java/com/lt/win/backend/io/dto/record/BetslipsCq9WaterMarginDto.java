package com.lt.win.backend.io.dto.record;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * CQ9注单表
 * </p>
 *
 * @author David
 * @since 2020-11-09
 */
@Data
@ApiModel(value = "cq9WaterMargin", description = "cq9WaterMargin注单")
public class BetslipsCq9WaterMarginDto   {


    @ApiModelProperty(name ="id", value = "round订单号", example = "10000")
    private String id;


    @ApiModelProperty(name ="xb_uid", value = "对应user表id", example = "10000")
    private Integer xbUid;


    @ApiModelProperty(name ="xb_username", value = "对应user表username", example = "10000")
    private String xbUsername;


    @ApiModelProperty(name ="account", value = "玩家帐号", example = "10000")
    private String account;


    @ApiModelProperty(name ="balance", value = "遊戲後餘額", example = "10000")
    private BigDecimal balance;


    @ApiModelProperty(name ="game_hall", value = "游戏商", example = "10000")
    private String gameHall;


    @ApiModelProperty(name ="game_type", value = "遊戲種類", example = "10000")
    private String gameType;


    @ApiModelProperty(name ="game_plat",value = "遊戲平台")
    private String gamePlat;


    @ApiModelProperty(name ="game_code", value = "游戏商", example = "10000")
    private String gameCode;


    @ApiModelProperty(name ="bet", value = "投注额度", example = "10000")
    private BigDecimal bet;


    @ApiModelProperty(name ="valid_bet", value = "有效投注额度", example = "10000")
    private BigDecimal validBet;


    @ApiModelProperty(name ="jackpot", value = "彩池獎金", example = "10000")
    private BigDecimal jackpot;


    @ApiModelProperty(name ="jackpot_contribution", value = "彩池獎金貢獻值※從小彩池到大彩池依序排序", example = "10000")
    private String jackpotContribution;


    @ApiModelProperty(name ="jackpot_type", value = "彩池獎金類別※此欄位值為空字串時，表示未獲得彩池獎金", example = "10000")
    private String jackpotType;


    @ApiModelProperty(name ="status", value = "注單狀態 [complete]-complete:完成", example = "10000")
    private String status;

    @ApiModelProperty(name ="end_round_time", value = "遊戲結束時間", example = "10000")
    private Date endRoundTime;


    @ApiModelProperty(name ="create_time", value = "當筆資料建立時間，格式為 RFC3339n※系統結算時間, 注單結算時間及報表結算時間都是createtime", example = "10000")
    private Date createTime;

    /**
     * ，格式為 RFC3339
     */
    @ApiModelProperty(name ="bet_time", value = "下注時間", example = "10000")
    private Date betTime;

    @ApiModelProperty(name ="detail", value = "* 回傳 free game / bonus game / luckydraw / item / reward 資訊" +
            "※slot 會回傳 free game / bonus game / luckydraw 資訊" +
            "※fish 會回傳 item / reward 資訊", example = "10000")
    private String detail;


    @ApiModelProperty(name ="single_row_bet", value = "[true|false]是否為再旋轉形成的注單", example = "10000")
    private Integer singleRowBet;


    @ApiModelProperty(name ="game_role", value = "庄(banker) or 閒(player)※此欄位為牌桌遊戲使用，非牌桌遊戲此欄位值為空字串", example = "10000")
    private String gameRole;

    @ApiModelProperty(name ="banke_type", value = "     * 對戰玩家是否有真人[pc|human]\\npc：對戰玩家沒有真人\\nhuman：對戰玩家有真人\\n※此欄位為牌桌遊戲使用，非牌桌遊戲此欄位值為空字串※如果玩家不支持上庄，只存在與系统對玩。則bankertype 為 PC\n", example = "10000")
    private String bankeType;

    @ApiModelProperty(name ="rake", value = "抽水金額", example = "10000")
    private BigDecimal rake;

    @ApiModelProperty(name ="roomfee", value = "開房費用", example = "10000")
    private BigDecimal roomfee;


    @ApiModelProperty(name ="bet_type", value = "下注玩法", example = "10000")
    private String betType;

    @ApiModelProperty(name ="game_result", value = "遊戲結果", example = "10000")
    private String gameResult;

    @ApiModelProperty(name ="table_type", value = "真人注單參數說明名稱", example = "10000")
    private String tableType;


    @ApiModelProperty(name ="table_id", value = "桌號", example = "10000")
    private String tableId;


    @ApiModelProperty(name ="round_number", value = "局號", example = "10000")
    private String roundNumber;

    @ApiModelProperty(name ="xb_coin", value = "投注金额", example = "10000")
    private BigDecimal xbCoin;


    @ApiModelProperty(name ="xb_valid_coin", value = "有效投注额", example = "10000")
    private BigDecimal xbValidCoin;


    @ApiModelProperty(name ="xb_profit", value = "盈亏金额", example = "10000")
    private BigDecimal xbProfit;


    @ApiModelProperty(name ="xb_status", value = "注单状态", example = "10000")
    private Integer xbStatus;

    @ApiModelProperty(name ="created_at", value = "创建时间", example = "10000")
    private Integer createdAt;

    @ApiModelProperty(name ="updated_at", value = "更新时间", example = "10000")
    private Integer updatedAt;



}