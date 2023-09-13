package com.lt.win.backend.io.dto.record;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author David
 * @since 2020-08-10
 */
@Data
@ApiModel(value = "ibc", description = "ibc注单")
public class BetslipsShabaSportsDto {


    @ApiModelProperty(name = "refNo", value = "注单号", example = "")
    private Long id;


    @ApiModelProperty(name = "xb_uid", value = "对应user表id", example = "")
    private Integer xbUid;


    @ApiModelProperty(name = "xb_username", value = "对应user表username", example = "")
    private String xbUsername;


    @ApiModelProperty(name = "vendor_member_id", value = "会员账号", example = "")
    private String vendorMemberId;


    @ApiModelProperty(name = "operator_id", value = "厂商 ID。此 ID 为厂商自行定义", example = "")
    private String operatorId;


    @ApiModelProperty(name = "league_id", value = "联盟编号", example = "")
    private Integer leagueId;


    @ApiModelProperty(name = "match_id", value = "赛事编号", example = "")
    private Integer matchId;


    @ApiModelProperty(name = "home_id", value = "主队编号", example = "")
    private Integer homeId;


    @ApiModelProperty(name = "away_id", value = "客队编号", example = "")
    private Integer awayId;


    @ApiModelProperty(name = "team_id", value = "队伍编号", example = "")
    private Integer teamId;


    @ApiModelProperty(name = "match_datetime", value = " 赛事开球时间", example = "")
    private Date matchDatetime;


    @ApiModelProperty(name = "sport_type", value = "体育种类。请参考附件〝体育种类表”", example = "")
    private Integer sportType;


    @ApiModelProperty(name = "bet_type", value = "下注类型。请参考附件〝下注类型表”", example = "")
    private Integer betType;


    @ApiModelProperty(name = "parlay_ref_no", value = "混合过关注单号码，使用此号码于", example = "")
    private Long parlayRefNo;


    @ApiModelProperty(name = "odds", value = "注单赔率", example = "")
    private BigDecimal odds;

    /**
     * 会员投注金额
     */
    @ApiModelProperty(name = "stake", value = "会员投注金额", example = "")
    private BigDecimal stake;


    @ApiModelProperty(name = "transaction_time", value = "投注交易时间", example = "")
    private Date transactionTime;


    @ApiModelProperty(name = "ticket_status", value = "注单状态", example = "")
    private String ticketStatus;


    @ApiModelProperty(name = "winlost_amount", value = "此注输或赢的金额", example = "")
    private BigDecimal winlostAmount;


    @ApiModelProperty(name = "after_amount", value = "下注后的余额", example = "")
    private BigDecimal afterAmount;


    @ApiModelProperty(name = "currency", value = "为此会员设置币别。请参考附件〝币别表”", example = "")
    private Integer currency;


    @ApiModelProperty(name = "winlost_datetime", value = "决胜时间", example = "")
    private Date winlostDatetime;


    @ApiModelProperty(name = "odds_type", value = "赔率类型", example = "")
    private Integer oddsType;


    @ApiModelProperty(name = "odds_Info", value = "当 bettype 为 468 或 469, 此字段则显示", example = "")
    private String oddsInfo;


    @ApiModelProperty(name = "bet_team", value = "下注对象", example = "")
    private String betTeam;


    @ApiModelProperty(name = "exculding", value = "当 bet_team=aos 时,才返回此字段,返回的值", example = "")
    private String exculding;


    @ApiModelProperty(name = "bet_tag", value = "X 与 Y 的值。", example = "请参考附件〝下注类型表” 例) bettype 145 - Set X Winner")
    private String betTag;


    @ApiModelProperty(name = "home_hdp", value = "主队让球", example = "")
    private BigDecimal homeHdp;


    @ApiModelProperty(name = "away_hdp", value = "客队让球", example = "")
    private BigDecimal awayHdp;


    @ApiModelProperty(name = "hdp", value = "让球", example = "")
    private BigDecimal hdp;


    @ApiModelProperty(name = "betfrom", value = " 下注平台表。请参考附件中〝下注平台表”", example = "")
    private String betfrom;


    @ApiModelProperty(name = "islive", value = "是否在滚球时下注", example = "")
    private String islive;

    /**
     * .
     */
    @ApiModelProperty(name = "home_score", value = "下注时主队得分", example = "")
    private Integer homeScore;


    @ApiModelProperty(name = "away_score", value = "下注时客队得分", example = "")
    private Integer awayScore;


    @ApiModelProperty(name = "settlement_time", value = "注单结算的时间", example = "")
    private Date settlementTime;


    @ApiModelProperty(name = "home_team_name", value = "主队信息", example = "")
    private String homeTeamName;

    /**
     * 联赛信息
     */
    @ApiModelProperty(name = "league_name", value = "联赛信息", example = "")
    private String leagueName;


    @ApiModelProperty(name = "away_team_name", value = "客队信息", example = "")
    private String awayTeamName;


    @ApiModelProperty(name = "customInfo1", value = "厂商备注", example = "")
    private String customInfo1;


    @ApiModelProperty(name = "customInfo2", value = "厂商备注", example = "")
    private String customInfo2;


    @ApiModelProperty(name = "customInfo3", value = "厂商备注", example = "")
    private String customInfo3;


    @ApiModelProperty(name = "customInfo4", value = " 厂商备注", example = "")
    private String customInfo4;


    @ApiModelProperty(name = "customInfo5", value = "厂商备注", example = "")
    private String customInfo5;


    @ApiModelProperty(name = "ba_status", value = "会员是否为 BA 状态", example = "")
    private String baStatus;


    @ApiModelProperty(name = "version_key", value = "版本号", example = "")
    private Long versionKey;


    @ApiModelProperty(name = "parlay_data", value = "混合过关信息", example = "")
    private String parlayData;


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


    @ApiModelProperty(name = "updated_at", value = "修改时间", example = "")
    private Integer updatedAt;


}
