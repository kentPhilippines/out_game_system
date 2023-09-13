package com.lt.win.apiend.io.dto.wallet;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>
 * 钱包-首页-资产余额 响应body
 * </p>
 *
 * @author andy
 * @since 2020/4/7
 */
@Data
public class IndexResBody {
    @ApiModelProperty(name = "coin", value = "资产余额", example = "123456.00")
    private BigDecimal coin;

}
