package com.lt.win.apiend.io.dto.wallet;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 钱包-转账-首页
 * </p>
 *
 * @author andy
 * @since 2020/4/8
 */
@Data
public class TransferIndexResBody {
    @ApiModelProperty(name = "coin", value = "主账户余额", example = "123456.00")
    private BigDecimal coin;

    @ApiModelProperty(name = "gameGroupList", value = "游戏种类列表（横向）")
    private List<GameGroup> gameGroupList;

    @ApiModelProperty(name = "thirdCoinList", value = "第三方平台余额")
    private List<ThirdCoin> thirdCoinList;
}
