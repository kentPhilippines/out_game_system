package com.lt.win.apiend.io.dto.wallet;

import com.lt.win.apiend.io.dto.StartEndTime;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * <p>
 * 钱包-交易记录-列表查询 请求body
 * </p>
 *
 * @author andy
 * @since 2020/4/14
 */
@Data
@Builder
public class TransactionListReqBody extends StartEndTime {

    @ApiModelProperty(name = "category", value = "类型:1-存款 2-提款 3-上分 4-下分 5-返水 6-佣金 7-活动", example = "1")
    private Integer category;

    @ApiModelProperty(hidden = true)
    private Integer uid;

}
