package com.lt.win.backend.io.dto.record;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>
 * PG电子注单表
 * </p>
 *
 * @author David
 * @since 2020-12-25
 */
@Data
public class BetslipsEbetDto {



    /**
     * ID -> billNo订单号
     */
    @ApiModelProperty(name = "id", value = "下注編號", example = "10000")
    private Long id;


    /**
     * 对应user表id
     */
    @ApiModelProperty(name = "xb_uid",value = "对应user表id")
    private Integer xbUid;

    /**
     * 对应user表username
     */
    @ApiModelProperty(name = "xbUsername",value = "游戏类型")
    private String xbUsername;
    @ApiModelProperty(name="gameType",value = "游戏类型")
    private Integer gameType;
    @ApiModelProperty(name="game_name",value = "游戏名称")
    private String gameName;
    @ApiModelProperty(name="bet",value = "总投注额")
    private BigDecimal bet;
    @ApiModelProperty(name="round_no",value = "牌局号码")
    private String roundNo;
    @ApiModelProperty(name="payout",value = "总派彩金额")
    private BigDecimal payout;
    @ApiModelProperty(name="payout_withoutholding",value = "纯派彩总额")
    private BigDecimal payoutWithoutholding;
    @ApiModelProperty(name="create_time",value = "下注时间")
    private Integer createTime;
    @ApiModelProperty(name="payout_time",value = "派彩时间")
    private Integer payoutTime;
    @ApiModelProperty(name="bet_history_id",value = "投注记录ID")
    private String betHistoryId;
    @ApiModelProperty(name="valid_bet",value = "有效投注")
    private BigDecimal validBet;
    @ApiModelProperty(name="balance",value = "盈余")
    private BigDecimal balance;
    @ApiModelProperty(name="username",value = "用户名")
    private String username;
    @ApiModelProperty(name="user_id",value = "用户ID")
    private Integer userId;
    @ApiModelProperty(name="platform",value = "游戏平台")
    private Integer platform;
    /**
     * 投注金额
     */
    @ApiModelProperty(name="xb_coin",value = "投注金额")
    private BigDecimal xbCoin;

    /**
     * 有效投注额
     */
    @ApiModelProperty(name="xb_valid_coin",value = "有效投注额")
    private BigDecimal xbValidCoin;

    /**
     * 盈亏金额
     */
    @ApiModelProperty(name="xb_profit",value = "盈亏金额")
    private BigDecimal xbProfit;

    /**
     * 注单状态
     */
    @ApiModelProperty(name="xb_status",value = "注单状态")
    private Integer xbStatus;

    @ApiModelProperty(name="created_at",value = "创建时间")
    private Integer createdAt;

    @ApiModelProperty(name="updated_at",value = "结算时间")
    private Integer updatedAt;



    @ApiModelProperty(name="player_result",value = "闲家点数")
    private Integer playerResult;
    @ApiModelProperty(name="banker_result",value = "庄家点数")
    private Integer bankerResult;
    @ApiModelProperty(name="dragon_card",value = "龙的开牌结果")
    private Integer dragonCard;
    @ApiModelProperty(name="tiger_card",value = "虎的开牌结果")
    private Integer tigerCard;
    @ApiModelProperty(name="number",value = "轮盘结果号码")
    private Integer number;







}
