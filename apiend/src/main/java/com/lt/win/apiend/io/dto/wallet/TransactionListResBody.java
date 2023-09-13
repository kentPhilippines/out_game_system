package com.lt.win.apiend.io.dto.wallet;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>
 * 钱包-交易记录-列表查询 响应body
 * </p>
 *
 * @author andy
 * @since 2020/4/14
 */
@Data
public class TransactionListResBody {

    @ApiModelProperty(name = "category", value = "类型:1-存款 2-提款 3-上分 4-下分 6-佣金 7-活动", example = "1")
    private Integer category;

    @ApiModelProperty(name = "subCategory", value = "子类型:活动表-id", example = "0")
    private Integer subCategory;

    @ApiModelProperty(name = "coin", value = "金额", example = "100.00")
    private BigDecimal coin;

    @ApiModelProperty(name = "createdAt", value = "时间", example = "1586334785")
    private Integer createdAt;

    @ApiModelProperty(name = "status", value = "状态：0-申请中 1-成功 2-失败", example = "0")
    private Integer status;

    @ApiModelProperty(name = "name", value = "银行名称、提现银行、游戏名称、佣金日期、活动名称", example = "中国银行")
    private String name;

    @ApiModelProperty(name = "referId", value = "订单ID")
    private Long referId;

    @ApiModelProperty(name = "mark", value = "备注", example = "还需金额100")
    private String mark;

    /**
     * 以下特有
     */
    @ApiModelProperty(name = "bankAccount", value = "银行账号category=1,2时使用", example = "6225 ******** 8888")
    private String bankAccount;

    @ApiModelProperty(name = "payCoin", value = "到账金额category=1时使用", example = "100.00")
    private BigDecimal payCoin;

    @ApiModelProperty(name = "method", value = "充值方式:1-银行卡 2-微信 3-支付,category=1时使用", example = "3")
    private Integer method;


}
