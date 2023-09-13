package com.lt.win.apiend.io.dto.wallet;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 钱包-转账-首页-游戏种类
 * </p>
 *
 * @author andy
 * @since 2020/4/8
 */
@Data
public class GameGroup {
    @ApiModelProperty(name = "id", value = "gameGroupID", example = "1")
    private Integer id;

    @ApiModelProperty(name = "nameAbbr", value = "名称缩写", example = "体育")
    private String nameAbbr;
}
