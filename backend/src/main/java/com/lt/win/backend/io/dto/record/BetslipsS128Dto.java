package com.lt.win.backend.io.dto.record;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * S128斗鸡注单表
 * </p>
 *
 * @author David
 * @since 2020-08-10
 */
@Data
@ApiModel(value = "s128", description = "s128注单")
public class BetslipsS128Dto {

    @ApiModelProperty(name = "ticket_id", value = "注单号", example = "")
    private Long id;


    @ApiModelProperty(name = "xb_uid", value = "对应user表id", example = "")
    private Integer xbUid;


    @ApiModelProperty(name = "xb_username", value = "对应user表username", example = "")
    private String xbUsername;


    @ApiModelProperty(name = "login_id", value = "登录帐号", example = "")
    private String loginId;


    @ApiModelProperty(name = "arena_code", value = "赛场编号", example = "")
    private String arenaCode;


    @ApiModelProperty(name = "arena_name_cn", value = "赛场名中文名字", example = "")
    private String arenaNameCn;


    @ApiModelProperty(name = "match_no", value = "赛事编号", example = "")
    private String matchNo;


    @ApiModelProperty(name = "match_type", value = "赛事类型", example = "")
    private String matchType;


    @ApiModelProperty(name = "match_date", value = "赛事日期", example = "")
    private Date matchDate;


    @ApiModelProperty(name = "fight_no", value = "日场次", example = "")
    private Integer fightNo;


    @ApiModelProperty(name = "fight_datetime", value = "赛事时间", example = "")
    private Date fightDatetime;


    @ApiModelProperty(name = "meron_cock", value = "龍斗鸡", example = "")
    private String meronCock;


    @ApiModelProperty(name = "meron_cock_cn", value = "龍斗鸡中文名字", example = "")
    private String meronCockCn;


    @ApiModelProperty(name = "wala_cock", value = "鳳斗鸡", example = "")
    private String walaCock;


    @ApiModelProperty(name = "wala_cock_cn", value = "鳳斗鸡中文名字", example = "")
    private String walaCockCn;

    @ApiModelProperty(name = "bet_on", value = "投注", example = "MERON/WALA/BDD/FTD")
    private String betOn;


    @ApiModelProperty(name = "odds_type", value = "赔率类型", example = "")
    private String oddsType;


    @ApiModelProperty(name = "odds_asked", value = "要求赔率", example = "")
    private BigDecimal oddsAsked;


    @ApiModelProperty(name = "odds_given", value = "给出赔率", example = "")
    private BigDecimal oddsGiven;


    @ApiModelProperty(name = "stake", value = "投注金额", example = "")
    private Integer stake;


    @ApiModelProperty(name = "stake_money", value = "有效投注额", example = "")
    private BigDecimal stakeMoney;


    @ApiModelProperty(name = "balance_open", value = "转账前余额", example = "")
    private BigDecimal balanceOpen;


    @ApiModelProperty(name = "balance_close", value = "转账后余额", example = "")
    private BigDecimal balanceClose;


    @ApiModelProperty(name = "created_datetime", value = "创建时间", example = "")
    private Date createdDatetime;


    @ApiModelProperty(name = "fight_result", value = "赛事结果", example = "MERON/WALA/BDD/FTD")
    private String fightResult;


    @ApiModelProperty(name = "status", value = "状态", example = "WIN/LOSE/REFUND/CANCEL/VOID")
    private String status;


    @ApiModelProperty(name = "winloss", value = "输赢", example = "")
    private BigDecimal winloss;


    @ApiModelProperty(name = "comm_earned", value = "所得佣金", example = "")
    private BigDecimal commEarned;


    @ApiModelProperty(name = "payout", value = "派彩", example = "")
    private BigDecimal payout;


    @ApiModelProperty(name = "balance_open1", value = "转账前余额", example = "")
    private BigDecimal balanceOpen1;


    @ApiModelProperty(name = "balance_close1", value = "转账后余额", example = "")
    private BigDecimal balanceClose1;


    @ApiModelProperty(name = "processed_datetime", value = "处理时间", example = "")
    private Date processedDatetime;


    @ApiModelProperty(name = "xb_coin", value = "投注金额", example = "")
    private BigDecimal xbCoin;


    @ApiModelProperty(name = "xb_profit", value = "盈亏金额", example = "")
    private BigDecimal xbProfit;


    @ApiModelProperty(name = "xb_valid_coin", value = "有效投注额", example = "")
    private BigDecimal xbValidCoin;


    @ApiModelProperty(name = "xb_status", value = "注单状态", example = "")
    private Integer xbStatus;

    @ApiModelProperty(name = "created_at", value = "创建时间", example = "")
    private Integer createdAt;

    @ApiModelProperty(name = "updated_at", value = "更新时间", example = "")
    private Integer updatedAt;


}
