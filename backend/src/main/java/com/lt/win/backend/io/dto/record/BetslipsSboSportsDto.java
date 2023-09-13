package com.lt.win.backend.io.dto.record;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * SBO注单表(体育)
 * </p>
 *
 * @author David
 * @since 2020-08-10
 */
@Data
@ApiModel(value = "sbo", description = "sbo注单")
public class BetslipsSboSportsDto {

    @ApiModelProperty(name = "refNo", value = "注单号", example = "")
    private String id;

    @ApiModelProperty(name = "xb_uid", value = "对应user表id", example = "")
    private Integer xbUid;

    @ApiModelProperty(name = "xb_username", value = "对应user表username", example = "")
    private String xbUsername;

    @ApiModelProperty(name = "username", value = "用户名", example = "")
    private String username;

    @ApiModelProperty(name = "sports_type", value = "投注的体育类型", example = "")
    private String sportsType;

    @ApiModelProperty(name = "order_time", value = "投注时间", example = "")
    @JSONField(name = "order_time", format = "yyyy-MM-dd'T'HH:mm:sss")
    private Date orderTime;

    @ApiModelProperty(name = "win_lost_date", value = "结算时间", example = "")
    private String winLostDate;

    @ApiModelProperty(name = "modify_date", value = "最后修改时间", example = "")
    @JSONField(name = "modify_date", format = "yyyy-MM-dd'T'HH:mm:sss")
    private Date modifyDate;

    @ApiModelProperty(name = "odds", value = "赔率", example = "")
    private BigDecimal odds;

    @ApiModelProperty(name = "odds_style", value = "赔率类型", example = "M-Malay odds,H-HongKong odds,E-Euro odds,I-Indonesia odds")
    private String oddsStyle;

    @ApiModelProperty(name = "stake", value = "投注金额", example = "")
    private BigDecimal stake;

    @ApiModelProperty(name = "actual_stake", value = "实际投注金额(只有在賠率為負時actual stake與stake會不一樣)", example = "")
    private BigDecimal actualStake;

    @ApiModelProperty(name = "currency", value = "币种", example = "")
    private String currency;

    @ApiModelProperty(name = "status", value = "开奖结果", example = "")
    private String status;

    @ApiModelProperty(name = "win_lost", value = "输赢", example = "")
    private BigDecimal winLost;

    @ApiModelProperty(name = "turnover", value = "", example = "")
    private BigDecimal turnover;

    @ApiModelProperty(name = "is_half_won_lose", value = "上半场输赢结果", example = "")
    private Integer isHalfWonLose;

    @ApiModelProperty(name = "is_live", value = "是否现场比赛", example = "")
    private Integer isLive;

    @ApiModelProperty(name = "max_win_without_actual_stake", value = "拥有实际赌注的玩家的最大获胜额", example = "")
    private BigDecimal maxWinWithoutActualStake;

    @ApiModelProperty(name = "ip", value = "IP", example = "")
    private String ip;

    @ApiModelProperty(name = "sub_bet", value = "投注信息", example = "")
    private String subBet;

    @ApiModelProperty(name = "game_id", value = "gameId(虚拟体育)", example = "")
    private Integer gameId;

    @ApiModelProperty(name = "product_type", value = "产品类型(虚拟体育)", example = "")
    private String productType;

    @ApiModelProperty(name = "xb_sports_category", value = "体育类型", example = "1-真实体育 2-虚拟体育")
    private Integer xbSportsCategory;

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
