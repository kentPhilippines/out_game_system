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
@ApiModel(value = "Bti", description = "Bti注单")
public class BetslipsBtiDto {

    @ApiModelProperty(name = "id", value = "注单号", example = "")
    private String id;
    @ApiModelProperty(name = "xb_uid", value = "对应user表id", example = "")
    private Integer xbUid;

    @ApiModelProperty(name = "xb_username", value = "对应user表username", example = "")
    private String xbUsername;

    @ApiModelProperty(name = "pl", value = "输赢金额")
    private BigDecimal pl;

    @ApiModelProperty(name = "non_cash_out_amount", value = "游戏代码")
    private BigDecimal nonCashOutAmount;

    @ApiModelProperty(name = "combo_bonus_amount", value = "投注时间")
    private BigDecimal comboBonusAmount;

    @ApiModelProperty(name = "bet_settled_date", value = "结算时间")
    private String betSettledDate;

    @ApiModelProperty(name = "update_date", value = "更新时间")
    private Date updateDate;

    @ApiModelProperty(name = "odds", value = "赔率")
    private Integer odds;

    @ApiModelProperty(name = "odds_in_user_style", value = "盘口类型：Odds in user style (American, Decimal, Fractional)")
    private String oddsInUserStyle;

    @ApiModelProperty(name = "odds_style_of_user", value = "用户盘口类型")
    private String oddsStyleOfUser;

    @ApiModelProperty(name = "live_score1", value = "主场分数-live")
    private String liveScore1;

    @ApiModelProperty(name = "total_stake")
    private BigDecimal totalStake;

    @ApiModelProperty(name = "odds_dec", value = "玩法id")
    private String oddsDec;

    @ApiModelProperty(name = "valid_stake", value = "有效金额")
    private BigDecimal validStake;

    @ApiModelProperty(name = "platform", value = "Platform name: Web/Mobile")
    private String platform;

    @ApiModelProperty(name = "return_amount", value = "产品代码")
    private BigDecimal returnAmount;

    @ApiModelProperty(name = "domain_id", value = "单式注单（true,false）")
    private Integer domainId;

    @ApiModelProperty(name = "bet_status", value = "投注状态：half won/lost: Won, Lost, Half won, Half lost, Canceled, Draw, Open")
    private String betStatus;

    @ApiModelProperty(name = "brand", value = "品牌名称")
    private String brand;

    @ApiModelProperty(name = "user_name", value = "用户名")
    private String userName;

    @ApiModelProperty(name = "bet_type_name", value = "投注类型名称")
    private String betTypeName;

    @ApiModelProperty(name = "bet_type_id", value = "投注类型id")
    private Integer betTypeId;

    @ApiModelProperty(name = "creation_date", value = "创建时间")
    private Date creationDate;

    @ApiModelProperty(name = "status", value = "输赢状态：Won, Lost, Canceled, Draw, Open")
    private String status;

    @ApiModelProperty(name = "customer_id", value = "客户id")
    private Integer customerId;

    @ApiModelProperty(name = "merchant_customer_id", value = "商户id")
    private String merchantCustomerId;

    @ApiModelProperty(name = "currency", value = "货币代码")
    private String currency;

    @ApiModelProperty(name = "player_level_id", value = "用户等级id")
    private Integer playerLevelId;

    @ApiModelProperty(name = "player_level_name", value = "用户等级名称")
    private String playerLevelName;
    @ApiModelProperty(name = "selections", value = "投注选项")
    private String selections;

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