package com.lt.win.backend.io.dto.record;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * wm视讯注单表
 * </p>
 *
 * @author David
 * @since 2020-08-10
 */
@Data
@ApiModel(value = "wm", description = "wm注单")
public class BetslipsWmDto {


    @ApiModelProperty(name = "id", value = "注单号", example = "")
    private Long id;


    @ApiModelProperty(name = "xb_uid", value = "对应user表id", example = "")
    private Integer xbUid;


    @ApiModelProperty(name = "xb_username", value = "对应user表username", example = "")
    private String xbUsername;


    @ApiModelProperty(name = "user", value = "会员账号", example = "")
    private String user;


    @ApiModelProperty(name = "bet_time", value = "下注時間", example = "")
    private Date betTime;


    @ApiModelProperty(name = "before_cash", value = "下注前金额", example = "")
    private BigDecimal beforeCash;


    @ApiModelProperty(name = "bet", value = "下注金额", example = "")
    private BigDecimal bet;


    @ApiModelProperty(name = "valid_bet", value = "有效下注", example = "")
    private BigDecimal validBet;


    @ApiModelProperty(name = "water", value = "退水金额", example = "")
    private BigDecimal water;


    @ApiModelProperty(name = "result", value = "下注结果", example = "")
    private BigDecimal result;


    @ApiModelProperty(name = "bet_code", value = "下注代碼Code的部分", example = "")
    private String betCode;


    @ApiModelProperty(name = "bet_result", value = "下注内容", example = "")
    private String betResult;


    @ApiModelProperty(name = "water_bet", value = "下注退水金额", example = "")
    private String waterBet;


    @ApiModelProperty(name = "win_loss", value = "输赢金额", example = "")
    private BigDecimal winLoss;


    @ApiModelProperty(name = "ip", value = "IP", example = "")
    private String ip;


    @ApiModelProperty(name = "gid", value = "101-百家乐 102-龙虎 103-轮盘 104-骰宝 105-牛牛 106-三公 107-番摊 108-色碟 110-鱼虾蟹 111-炸金花 112-温州牌九 113-二八杠", example = "")
    private Integer gid;


    @ApiModelProperty(name = "event", value = "场次编号", example = "")
    private Long event;


    @ApiModelProperty(name = "event_child", value = "子场次编号", example = "")
    private Integer eventChild;


    @ApiModelProperty(name = "round", value = "场次编号", example = "")
    private Long round;


    @ApiModelProperty(name = "sub_round", value = "子场次编号", example = "")
    private Integer subRound;


    @ApiModelProperty(name = "table_id", value = "桌台编号", example = "")
    private Integer tableId;


    @ApiModelProperty(name = "game_result", value = "牌型ex:庄:♦3♦3 闲:♥9♣10", example = "")
    private String gameResult;


    @ApiModelProperty(name = "gname", value = "游戏名称ex:百家乐", example = "")
    private String gname;


    @ApiModelProperty(name = "commission", value = "0-一般 1-免佣", example = "")
    private Integer commission;


    @ApiModelProperty(name = "reset", value = "Y-有重对 N-非重对", example = "")
    private String reset;

    @ApiModelProperty(name = "set_time", value = "结算时间", example = "")
    private Date setTime;


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
