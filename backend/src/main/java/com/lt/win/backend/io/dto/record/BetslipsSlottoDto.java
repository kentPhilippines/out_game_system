package com.lt.win.backend.io.dto.record;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * slotto彩票注单表
 * </p>
 *
 * @author David
 * @since 2020-08-10
 */
@Data
@ApiModel(value = "slotto", description = "slotto注单")
public class BetslipsSlottoDto {


    @ApiModelProperty(name = "refNo", value = "注单号", example = "")
    private Long id;


    @ApiModelProperty(name = "xb_uid", value = "对应user表id", example = "")
    private Integer xbUid;


    @ApiModelProperty(name = "xb_username", value = "对应user表username", example = "")
    private String xbUsername;


    @ApiModelProperty(name = "row_num", value = "条号", example = "")
    private Integer rowNum;


    @ApiModelProperty(name = "order_id", value = "收据编号", example = "")
    private String orderId;


    @ApiModelProperty(name = "login_name", value = "用户名", example = "")
    private String loginName;


    @ApiModelProperty(name = "draw_date", value = "结算日期", example = "")
    private Date drawDate;


    @ApiModelProperty(name = "date_bet", value = "下注日期", example = "")
    private Date dateBet;


    @ApiModelProperty(name = "bet_type", value = "下注日期", example = "")
    private Integer betType;


    @ApiModelProperty(name = "bet_position", value = "下注位置", example = "")
    private String betPosition;


    @ApiModelProperty(name = "bet_number", value = "下注数字", example = "")
    private String betNumber;


    @ApiModelProperty(name = "confirmed", value = "下注金额", example = "")
    private BigDecimal confirmed;


    @ApiModelProperty(name = "strikePL", value = "赢取金额", example = "")
    private BigDecimal strikePL;


    @ApiModelProperty(name = "commPL", value = "佣金金额", example = "")
    private BigDecimal commPL;


    @ApiModelProperty(name = "Status", value = "注单状态", example = "")
    private String Status;


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
