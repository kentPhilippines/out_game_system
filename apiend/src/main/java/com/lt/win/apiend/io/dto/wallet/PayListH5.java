package com.lt.win.apiend.io.dto.wallet;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 支付列表
 * </p>
 *
 * @author andy
 * @since 2020/4/16
 */
@Data
public class PayListH5 {
    @ApiModelProperty(name = "payOffline", value = "线下充值列表")
    private Object payOffline;

    @ApiModelProperty(name = "payOnline", value = "线上充值列表")
    private Object payOnline;
}
