package com.lt.win.backend.io.dto.record;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * MG电子注单表
 * </p>
 *
 * @author David
 * @since 2020-08-10
 */
@Data
@ApiModel(value = "mg", description = "mg注单")
public class BetslipsMgDto {


    @ApiModelProperty(name = "id", value = "注单号", example = "")
    private String id;


    @ApiModelProperty(name = "xb_uid", value = "用户id", example = "")
    private Integer xbUid;


    @ApiModelProperty(name = "xb_username", value = "对应user表username", example = "")
    private String xbUsername;


    @ApiModelProperty(name = "created_dateUTC", value = "注单创建时间", example = "")
    private Date createdDateutc;


    @ApiModelProperty(name = "game_start_timeUTC", value = "游戏开始时间", example = "")
    private Date gameStartTimeutc;


    @ApiModelProperty(name = "game_end_timeUTC", value = "游戏结束时间", example = "")
    private Date gameEndTimeutc;


    @ApiModelProperty(name = "player_id", value = "用户名", example = "")
    private String playerId;


    @ApiModelProperty(name = "product_id", value = "厂商id", example = "")
    private String productId;


    @ApiModelProperty(name = "product_player_id", value = "产品id", example = "")
    private String productPlayerId;


    @ApiModelProperty(name = "platform", value = "平台", example = "")
    private String platform;


    @ApiModelProperty(name = "game_code", value = "游戏code", example = "")
    private String gameCode;


    @ApiModelProperty(name = "currency", value = "币种", example = "")
    private String currency;


    @ApiModelProperty(name = "bet_amount", value = "下注金额", example = "")
    private BigDecimal betAmount;


    @ApiModelProperty(name = "payout_amount", value = "赢取金额", example = "")
    private BigDecimal payoutAmount;


    @ApiModelProperty(name = "bet_status", value = "注单状态", example = "")
    private String betStatus;

    @ApiModelProperty(name = "pca", value = "", example = "")
    private String pca;


    @ApiModelProperty(name = "external_transaction_id", value = "转账id", example = "")
    private String externalTransactionId;


    @ApiModelProperty(name = "metadata", value = "主要数据", example = "")
    private String metadata;


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
