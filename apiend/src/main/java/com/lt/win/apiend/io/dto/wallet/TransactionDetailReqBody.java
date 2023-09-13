package com.lt.win.apiend.io.dto.wallet;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 钱包-交易记录-交易详情 请求body
 * </p>
 *
 * @author andy
 * @since 2020/4/15
 */
@Data
public class TransactionDetailReqBody {
    @NotNull(message = "关联ID不能为空")
    @ApiModelProperty(name = "referId", value = "关联ID")
    private Long referId;

    @NotNull(message = "类型不能为空")
    @ApiModelProperty(name = "category", value = "类型:1-存款 2-提款 3-上分 4-下分 5-返水 6-佣金 7-活动", example = "1")
    private Integer category;

    @ApiModelProperty(name = "subCategory", value = "子类型[范围1,2,3..13]:当category=7时,subCategory不能为空", example = "0")
    private Integer subCategory;
}
