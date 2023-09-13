package com.lt.win.apiend.io.dto.promotions;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author : Wells
 * @Date : 2021-01-13 12:08 上午
 * @Description : 领取红包
 */
@Data
public class ReceiveEnvelopeResDto {
    @ApiModelProperty(name = "times", value = "次数", example = "1")
    private Integer times;
    @ApiModelProperty(name = "coin", value = "金额", example = "1.2")
    private BigDecimal coin;
}
