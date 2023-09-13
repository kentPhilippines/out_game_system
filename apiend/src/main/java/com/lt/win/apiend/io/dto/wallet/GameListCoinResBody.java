package com.lt.win.apiend.io.dto.wallet;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>
 * 钱包-游戏列表-余额 响应body
 * </p>
 *
 * @author andy
 * @since 2020/4/16
 */
@Data
public class GameListCoinResBody {
    @ApiModelProperty(name = "coin", value = "余额", example = "100.00")
    private BigDecimal coin;
    @ApiModelProperty(name = "id", value = "游戏列表ID", example = "601")
    private Integer id;
}
