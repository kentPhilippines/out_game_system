package com.lt.win.backend.io.dto.record;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * AG注单表
 * </p>
 *
 * @author David
 * @since 2020-08-10
 */
@Data
@ApiModel(value = "ag", description = "ag注单")
public class BetslipsAgDto {

    @ApiModelProperty(name = "id", value = "订单号", example = "10000")
    private Long id;

    @ApiModelProperty(name = "xb_uid", value = "用户id", example = "10000")
    private Integer xbUid;

    @ApiModelProperty(name = "xb_username", value = "用户名", example = "10000")
    private String xbUsername;

    @ApiModelProperty(name = "play_name", value = "玩家名", example = "10000")
    private String playName;

    @ApiModelProperty(name = "game_code", value = "局号", example = "10000")
    private String gameCode;

    @ApiModelProperty(name = "net_amount", value = "派彩额度", example = "10000")
    private BigDecimal netAmount;

    @ApiModelProperty(name = "bet_time", value = "下注时间", example = "10000")
    private Date betTime;

    @ApiModelProperty(name = "game_type", value = "游戏类型", example = "10000")
    private String gameType;

    @ApiModelProperty(name = "bet_amount", value = "投注额度", example = "10000")
    private BigDecimal betAmount;

    @ApiModelProperty(name = "valid_bet_amount", value = "有效投注额", example = "10000")
    private BigDecimal validBetAmount;

    @ApiModelProperty(name = "flag", value = "订单状态", example = "订单状态:0异常(请联系客服) 1已派彩 -8取消指定局注单 -9取消指定注单")
    private Integer flag;

    @ApiModelProperty(name = "play_type", value = "玩法类型", example = "10000")
    private Integer playType;

    @ApiModelProperty(name = "currency", value = "投注币种", example = "10000")
    private String currency;

    @ApiModelProperty(name = "table_code", value = "桌台号", example = "桌台号 (此處為虛擬桌號，非實際桌號。)")
    private String tableCode;

    @ApiModelProperty(name = "recalcu_time", value = "派彩时间", example = "10000")
    private Date recalcuTime;

    @ApiModelProperty(name = "before_credit", value = "余额", example = "10000")
    private BigDecimal beforeCredit;

    @ApiModelProperty(name = "bet_ip", value = "投注IP", example = "10000")
    private String betIp;

    @ApiModelProperty(name = "platform_type", value = "平台类型", example = "平台类型为AGIN")
    private String platformType;

    @ApiModelProperty(name = "remark", value = "注释", example = "10000")
    private String remark;

    @ApiModelProperty(name = "round", value = "廳別代碼", example = "10000")
    private String round;

    @ApiModelProperty(name = "result", value = "遊戲結果", example = "10000")
    private String result;

    @ApiModelProperty(name = "rounders", value = "開牌結果", example = "10000")
    private String rounders;

    @ApiModelProperty(name = "device_type", value = "设备类型", example = "0-PC 大于等于1-手机")
    private Integer deviceType;

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
