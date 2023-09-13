package com.lt.win.backend.io.dto.record;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 哈巴注单表
 * </p>
 *
 * @author David
 * @since 2020-08-10
 */
@Data
@ApiModel(value = "hb", description = "hb注单")
public class BetslipsHbDto {


    @ApiModelProperty(name = "id", value = "注单号", example = "对应三方拉单GameInstanceId")
    private String id;


    @ApiModelProperty(name = "xb_uid", value = "对应user表id", example = "10000")
    private Integer xbUid;


    @ApiModelProperty(name = "xb_username", value = "对应user表username", example = "10000")
    private String xbUsername;


    @ApiModelProperty(name = "player_id", value = "玩家ID", example = "10000")
    private String playerId;


    @ApiModelProperty(name = "brand_id", value = "品牌ID", example = "10000")
    private String brandId;


    @ApiModelProperty(name = "username", value = "用户名", example = "10000")
    private String username;


    @ApiModelProperty(name = "brand_game_id", value = "品牌游戏ID", example = "10000")
    private String brandGameId;


    @ApiModelProperty(name = "game_key_name", value = "游戏名称", example = "10000")
    private String gameKeyName;


    @ApiModelProperty(name = "game_type_id", value = "游戏类型ID", example = "10000")
    private Integer gameTypeId;


    @ApiModelProperty(name = "dt_started", value = "游戏开始时间", example = "10000")
    private Date dtStarted;


    @ApiModelProperty(name = "dt_completed", value = "游戏结束时间", example = "10000")
    private Date dtCompleted;


    @ApiModelProperty(name = "friendly_game_instance_id", value = "游戏编号ID", example = "10000")
    private Long friendlyGameInstanceId;


    @ApiModelProperty(name = "game_instance_id", value = "游戏编号名称", example = "10000")
    private String gameInstanceId;


    @ApiModelProperty(name = "stake", value = "投注", example = "10000")
    private BigDecimal stake;


    @ApiModelProperty(name = "payout", value = "派彩", example = "10000")
    private BigDecimal payout;


    @ApiModelProperty(name = "jackpot_win", value = "奖池奖金", example = "10000")
    private BigDecimal jackpotWin;


    @ApiModelProperty(name = "balance_after", value = "投注后的余额", example = "10000")
    private BigDecimal balanceAfter;


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
