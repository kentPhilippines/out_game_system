package com.lt.win.apiend.io.dto.wallet;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 钱包-游戏列表-余额 请求body
 * </p>
 *
 * @author andy
 * @since 2020/4/16
 */
@Data
public class GameListCoinReqBody {
    @NotNull(message = "ID不能为空")
    @ApiModelProperty(name = "id", value = "游戏列表ID", example = "601")
    private Integer id;
}
