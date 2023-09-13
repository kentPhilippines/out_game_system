package com.lt.win.backend.io.dto.record;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * TCG彩票注单表
 * </p>
 *
 * @author David
 * @since 2020-08-10
 */
@Data
@ApiModel(value = "tcg", description = "tcg注单")
public class BetslipsTcgDto {

    @ApiModelProperty(name = "bet_order_no", value = "注单号", example = "")
    private Long id;


    @ApiModelProperty(name = "xb_uid", value = "对应user表id", example = "")
    private Integer xbUid;


    @ApiModelProperty(name = "xb_username", value = "对应user表username", example = "")
    private String xbUsername;


    @ApiModelProperty(name = "bet_amount", value = "投注金额", example = "")
    private BigDecimal betAmount;


    @ApiModelProperty(name = "game_code", value = "游戏代码", example = "")
    private String gameCode;


    @ApiModelProperty(name = "bet_time", value = "投注时间", example = "")
    private Date betTime;


    @ApiModelProperty(name = "trans_time", value = "交易时间", example = "")
    private Date transTime;


    @ApiModelProperty(name = "bet_content_id", value = "投注内容", example = "")
    private String betContentId;


    @ApiModelProperty(name = "play_code", value = "玩法代码", example = "")
    private String playCode;

    @ApiModelProperty(name = "order_num", value = "更新订单号（后台查询", example = "")
    private String orderNum;


    @ApiModelProperty(name = "chase", value = "追号（true,false）", example = "")
    private String chase;


    @ApiModelProperty(name = "numero", value = "期号", example = "")
    private String numero;


    @ApiModelProperty(name = "betting_content", value = "投注实际内容", example = "")
    private String bettingContent;


    @ApiModelProperty(name = "play_id", value = "玩法id", example = "")
    private Integer playId;


    @ApiModelProperty(name = "freeze_time", value = "冻结时间", example = "")
    private Date freezeTime;


    @ApiModelProperty(name = "multiple", value = "下注倍数", example = "")
    private Integer multiple;


    @ApiModelProperty(name = "username", value = "用户名", example = "")
    private String username;


    @ApiModelProperty(name = "product_type", value = "产品代码", example = "")
    private String productType;

    @ApiModelProperty(name = "single", value = "单式注单（true,false", example = "")
    private String single;

    @ApiModelProperty(name = "merchant_code", value = "商户代码", example = "")
    private String merchantCode;


    @ApiModelProperty(name = "win_amount", value = "中奖金额", example = "")
    private BigDecimal winAmount;


    @ApiModelProperty(name = "settlement_time", value = "结算时间", example = "")
    private Date settlementTime;


    @ApiModelProperty(name = "netPNL", value = "净输赢", example = "")
    private BigDecimal netPNL;


    @ApiModelProperty(name = "bet_status", value = "订单状态", example = "(1:WIN | 2:LOSE | 3:CANCELLED | 4:TIE )(1:已中奖｜2:未中奖 ｜3:取消 | 4:和)")
    private Integer betStatus;


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
