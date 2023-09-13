package com.lt.win.apiend.io.dto.wallet;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>
 * 第三方平台余额
 * </p>
 *
 * @author andy
 * @since 2020/4/8
 */
@Data
public class ThirdCoin {

    @ApiModelProperty(name = "icon", value = "logo")
    private String icon;

    @ApiModelProperty(name = "name", value = "名称", example = "泛亚电竞")
    private String name;

    @ApiModelProperty(name = "id", value = "gameList主键", example = "601")
    private Integer id;

    @ApiModelProperty(name = "groupId", value = "gameGroupId游戏种类:1-体育 2-电子 3-真人 4-捕鱼 5-棋牌 6-电竞 7-彩票", example = "6")
    private Integer groupId;

    @ApiModelProperty(name = "coin", value = "余额", example = "100.00")
    private BigDecimal coin;
}
