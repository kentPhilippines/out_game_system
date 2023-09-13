package com.lt.win.backend.io.dto.record;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>
 * futures_lottery注单表
 * </p>
 *
 * @author David
 * @since 2020-09-29
 */
@Data
@ApiModel(value = "futures_lottery", description = "futures_lottery注单")
public class BetslipsFuturesLotteryDto {
    
    @ApiModelProperty(name = "id", value = "ID", example = "10000")
    private Long id;

    @ApiModelProperty(name = "plat_id", value = "平台ID", example = "10000")
    private Integer platId;

    @ApiModelProperty(name = "username", value = "用户名", example = "10000")
    private String username;

    @ApiModelProperty(name = "lottery_id", value = "彩种ID", example = "10000")
    private Integer lotteryId;

    @ApiModelProperty(name = "played_id", value = "玩法ID", example = "10000")
    private Integer playedId;

    @ApiModelProperty(name = "odds", value = "赔率", example = "10000")
    private BigDecimal odds;

    @ApiModelProperty(name = "odds_ext", value = "赔率(补充)", example = "10000")
    private BigDecimal oddsExt;

    @ApiModelProperty(name = "action_no", value = "投注期号", example = "10000")
    private Long actionNo;

    @ApiModelProperty(name = "serial_id", value = "序列串号(同组投注)", example = "10000")
    private Long serialId;

    @ApiModelProperty(name = "bet", value = "投注号码", example = "10000")
    private String bet;
   
    @ApiModelProperty(name = "coin", value = "投注金额", example = "10000")
    private BigDecimal coin;

    @ApiModelProperty(name = "coin_before", value = "即时金额", example = "10000")
    private BigDecimal coinBefore;

    @ApiModelProperty(name = "open_data", value = "开奖号码", example = "10000")
    private String openData;
   
    @ApiModelProperty(name = "open_data_info", value = "开奖号码补充信息", example = "10000")
    private String openDataInfo;
   
    @ApiModelProperty(name = "open_odds", value = "实际赔率", example = "10000")
    private BigDecimal openOdds;

    @ApiModelProperty(name = "status", value = " 开奖状态:WAIT-等待开奖 WIN-赢 LOST-输 TIE-和 CANCEL-撤单", example = "10000")
    private String status;

    @ApiModelProperty(name = "open_bonus", value = "中奖金额", example = "10000")
    private BigDecimal openBonus;

    @ApiModelProperty(name = "win_lose", value = "输赢金额", example = "10000")
    private BigDecimal winLose;
   
    @ApiModelProperty(name = "fl_created_at", value = "fl下注时间", example = "10000")
    private Integer flCreatedAt;
   
    @ApiModelProperty(name = "fl_updated_at", value = "fl更新时间", example = "10000")
    private Integer flUpdatedAt;
   
    @ApiModelProperty(name = "xb_uid", value = "对应user表id", example = "10000")
    private Integer xbUid;
   
    @ApiModelProperty(name = "xb_username", value = "对应user表username", example = "10000")
    private String xbUsername;

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
