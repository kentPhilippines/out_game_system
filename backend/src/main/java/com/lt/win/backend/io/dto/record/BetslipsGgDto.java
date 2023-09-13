package com.lt.win.backend.io.dto.record;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * GG注单表
 * </p>
 *
 * @author David
 * @since 2020-08-10
 */
@Data
@ApiModel(value = "gg", description = "gg注单")
public class BetslipsGgDto {


    @ApiModelProperty(name = "id", value = "订单唯一单号", example = "10000")
    private String id;


    @ApiModelProperty(name = "bet_id", value = "订单唯一单号", example = "10000")
    private String betId;


    @ApiModelProperty(name = "xb_uid", value = "对应user表id", example = "10000")
    private Integer xbUid;


    @ApiModelProperty(name = "xb_username", value = "对应user表username", example = "10000")
    private String xbUsername;


    @ApiModelProperty(name = "game_id", value = "游戏编码", example = "10000")
    private String gameId;


    @ApiModelProperty(name = "bet", value = "投注金额", example = "10000")
    private BigDecimal bet;


    @ApiModelProperty(name = "currency", value = "货币", example = "10000")
    private String currency;


    @ApiModelProperty(name = "link_id", value = "局号", example = "10000")
    private String linkId;


    @ApiModelProperty(name = "account_no", value = "用户名", example = "10000")
    private String accountNo;


    @ApiModelProperty(name = "auto_id", value = "自增长ID", example = "10000")
    private Long autoId;

    @ApiModelProperty(name = "closed", value = "标识", example = "1-已结算 0-未结算")
    private Integer closed;


    @ApiModelProperty(name = "bettime_str", value = "投注时间", example = "10000")
    private Date bettimeStr;


    @ApiModelProperty(name = "paytime_str", value = "结算时间", example = "10000")
    private Date paytimeStr;


    @ApiModelProperty(name = "profit", value = "输赢", example = "10000")
    private BigDecimal profit;


    @ApiModelProperty(name = "origin", value = "来源", example = ":0-PCWeb 1-Android 2-iOS 3-AndroidWeb 4-iOSWEB)")
    private Integer origin;


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
