package com.lt.win.backend.io.dto.record;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * Joker注单表(棋牌, value = "round订单号", example = "10000")
 * </p>
 *
 * @author David
 * @since 2020-11-09
 */
@Data
@ApiModel(value = "jokerGame", description = "jokerGame注单")
public class BetslipsJokerGameDto {
    @ApiModelProperty(name = "id", value = "id", example = "10000")
    private String id;

    @ApiModelProperty(name = "xb_uid", value = "对应user表id", example = "10000")
    private Integer xbUid;


    @ApiModelProperty(name = "xb_username", value = "对应user表username", example = "10000")
    private String xbUsername;


    @ApiModelProperty(name = "username", value = "用户名", example = "10000")
    private String username;


    @ApiModelProperty(name = "game_code", value = "游戏代码", example = "10000")
    private String gameCode;

    @ApiModelProperty(name = "description", value = "描述", example = "10000")
    private String description;

    @ApiModelProperty(name = "round_id", value = "遊戲回合id", example = "10000")
    private String roundId;


    @ApiModelProperty(name = "amount", value = "金额", example = "10000")
    private BigDecimal amount;

    @ApiModelProperty(name = "free_amount", value = "round订单号", example = "10000")
    private BigDecimal freeAmount;

    @ApiModelProperty(name = "result", value = "下注结果", example = "10000")
    private BigDecimal result;

    @ApiModelProperty(name = "time", value = "下注时间", example = "10000")
    private Date time;


    @ApiModelProperty(name = "details", value = "详情", example = "10000")
    private String details;


    @ApiModelProperty(name = "app_id", value = "应用id", example = "10000")
    private String appId;


    @ApiModelProperty(name = "currency_code", value = "货币", example = "10000")
    private String currencyCode;

    @ApiModelProperty(name = "type", value = "游戏类型", example = "10000")
    private String type;

    @ApiModelProperty(name = "transaction_o_code", value = "转账交易订单号", example = "10000")
    private String transactionOCode;

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
