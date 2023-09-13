package com.lt.win.backend.io.bo.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>
 * 会员管理-提款消费明细
 * </p>
 *
 * @author andy
 * @since 2020/6/5
 */
@Data
public class ListCodeRecords {
    @ApiModelProperty(name = "uid", value = "UID", example = "1")
    private Integer uid;

    @ApiModelProperty(name = "username", value = "用户名", example = "李历史")
    private String username;

    @ApiModelProperty(name = "coin", value = "金额", example = "李历史")
    private BigDecimal coin;

    @ApiModelProperty(name = "codeRequire", value = "需求打码量", example = "36.00")
    private BigDecimal codeRequire;

    @ApiModelProperty(name = "codeSurplus", value = "剩余打码量", example = "36.00")
    private BigDecimal codeSurplus;

    @ApiModelProperty(name = "category", value = "类型:0-充值 1-提现 2-返水 3-活动", example = "1")
    private Integer category;

    @ApiModelProperty(name = "createdAt", value = "时间", example = "1590735421")
    private Integer createdAt;
}
