package com.lt.win.apiend.io.dto.promotions;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author : Wells
 * @Date : 2021-01-14 10:39 下午
 * @Description : 领取红包
 */
@Data
public class ReceiveResDto {
    @ApiModelProperty(name = "isReceive", value = "是否领取；0-不领取，1-领取", example = "0")
    private Integer isReceive;

}
