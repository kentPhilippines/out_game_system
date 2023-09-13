package com.lt.win.backend.io.dto.record;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * BBIN视讯注单表
 * </p>
 *
 * @author David
 * @since 2020-08-10
 */
@Data
@ApiModel(value = "bbinlive", description = "bbinlive注单")
public class BetslipsBbinLiveDto {


    @ApiModelProperty(name = "id", value = "注单号", example = "10000")
    private Long id;

    @ApiModelProperty(name = "xb_uid", value = "用户ID", example = "10000")
    private Integer xbUid;

    @ApiModelProperty(name = "xb_username", value = "用户名", example = "10000")
    private String xbUsername;


    @ApiModelProperty(name = "username", value = "用户名", example = "10000")
    private String username;


    @ApiModelProperty(name = "wagers_date", value = "下注时间", example = "10000")
    private Date wagersDate;


    @ApiModelProperty(name = "serial_id", value = "局号", example = "10000")
    private Integer serialId;


    @ApiModelProperty(name = "round_no", value = "场次", example = "10000")
    private String roundNo;


    @ApiModelProperty(name = "game_type", value = "游戏种类", example = "10000")
    private Integer gameType;


    @ApiModelProperty(name = "wager_detail", value = "玩法", example = "10000")
    private String wagerDetail;


    @ApiModelProperty(name = "game_code", value = "桌号", example = "10000")
    private String gameCode;


    @ApiModelProperty(name = "result", value = "开牌结果", example = "10000")
    private String result;

    @ApiModelProperty(name = "result_type", value = "注单结果", example = "1-注销 0-未结开牌结果")
    private String resultType;


    @ApiModelProperty(name = "card", value = "结果牌", example = "10000")
    private String card;


    @ApiModelProperty(name = "bet_amount", value = "下注金额", example = "10000")
    private BigDecimal betAmount;


    @ApiModelProperty(name = "pay_off", value = "派彩金额(不包含本金)", example = "10000")
    private BigDecimal payOff;


    @ApiModelProperty(name = "currency", value = "币别", example = "10000")
    private String currency;


    @ApiModelProperty(name = "exchange_rate", value = "与人民币的汇率", example = "10000")
    private BigDecimal exchangeRate;


    @ApiModelProperty(name = "commissionable", value = "有效投注额", example = "10000")
    private BigDecimal commissionable;


    @ApiModelProperty(name = "origin", value = "设备", example = "设备:MI1-ios手机 MI2-ios平板 MA1-Android手机 MA2-Android平板 P-电脑 M-移动端")
    private String origin;


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
