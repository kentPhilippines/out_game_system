package com.lt.win.apiend.io.dto.wallet;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * <p>
 * 钱包-转账-上分/下分
 * 类型:0-上分(主->第三方) 1-下分(第三方->主)
 * </p>
 *
 * @author andy
 * @since 2020/4/8
 */
@Data
public class TransferAddReqBody {
    @NotNull(message = "金额不能为空")
    @ApiModelProperty(name = "coin", value = "转账金额", required = true, example = "100.00")
    private BigDecimal coin;

    @NotNull(message = "类型不能为空")
    @ApiModelProperty(name = "category", value = "类型:0-上分(主->第三方) 1-下分(第三方->主)", required = true, example = "1")
    private Integer category;

    @ApiModelProperty(name = "gameListId", value = "游戏类型Id", required = true, example = "1")
    private Integer gameListId;

}
