package com.lt.win.apiend.io.dto.platform;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
        import java.math.BigDecimal;

/**
 * @author: David
 * @date: 17/04/2020
 * @description:
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoinTransferReqDto {
    @ApiModelProperty(name = "id", value = "平台ID", example = "1")
    private Integer id;

    @Max(value = 1, message = "Maximum 1")
    @Min(value = 0, message = "Minimum 0")
    @ApiModelProperty(name = "direction", value = "类型:0-上分 1-下分", example = "1")
    private Integer direction;

    @ApiModelProperty(name = "coin", value = "金额", example = "100.00")
    private BigDecimal coin;
    @ApiModelProperty(name = "name", value = "玩家姓名作为Redis锁KEY", example = "test")
    private String name;
}
