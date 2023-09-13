package com.lt.win.backend.io.dto.record;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * Joker_Jackpot注单表
 * </p>
 *
 * @author David
 * @since 2020-11-09
 */
@Data
@ApiModel(value = "Sa", description = "Sa注单")
public class BetslipsSaDto {

    @ApiModelProperty(name = "id", value = "注单号", example = "")
    private String id;
    @ApiModelProperty(name = "xb_uid", value = "对应user表id", example = "")
    private Integer xbUid;

    @ApiModelProperty(name = "xb_username", value = "对应user表username", example = "")
    private String xbUsername;

    @ApiModelProperty(name = "username", value = "用户名", example = "")
    private String username;

    @ApiModelProperty(name = "bet_time", value = "下注时间", example = "")
    private Date betTime;

    @ApiModelProperty(name = "payout_time", value = "结算时间", example = "")
    private Date payoutTime;

    @ApiModelProperty(name = "host_id", value = "桌台ID", example = "")
    private String hostId;

    @ApiModelProperty(name = "game_id", value = "游戏代号", example = "")
    private String gameId;

    @ApiModelProperty(name = "round", value = "局号", example = "")
    private String round;

    @ApiModelProperty(name = "shoe_id", value = "靴", example = "")
    private String set;

    @ApiModelProperty(name = "bet_amount", value = "投注金额", example = "")
    private BigDecimal betAmount;

    @ApiModelProperty(name = "rolling", value = "有效投注额/洗碼量", example = "")
    private BigDecimal rolling;

    @ApiModelProperty(name = "balance", value = "投注後的馀额", example = "")
    private BigDecimal balance;

    @ApiModelProperty(name = "result_amount", value = "输赢金额", example = "")
    private BigDecimal resultAmount;

    @ApiModelProperty(name = "game_type", value = "游戏类型", example = "")
    private String gameType;

    @ApiModelProperty(name = "bet_type", value = "真人游戏: 不同的投注类型", example = "")
    private Integer betType;

    @ApiModelProperty(name = "bet_source", value = "投注资源设备", example = "")
    private Integer betSource;

    @ApiModelProperty(name = "transaction_id", value = "彩池派彩金额", example = "")
    private Integer transactionId;

    @ApiModelProperty(name = "game_result", value = "游戏结果", example = "")
    private String gameResult;

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