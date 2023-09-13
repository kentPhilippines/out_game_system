package com.lt.win.apiend.io.dto.wallet;

import com.lt.win.apiend.io.dto.StartEndTime;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 钱包-转账-转账记录 请求body
 * </p>
 *
 * @author andy
 * @since 2020/4/8
 */
@Data
public class TransferListReqBody extends StartEndTime {

    @NotNull(message = "类型不能为空")
    @ApiModelProperty(name = "category", value = "类型:0-转出(主->第三方) 1-转入(第三方->主)", required = true, example = "1")
    private Integer category;
}
