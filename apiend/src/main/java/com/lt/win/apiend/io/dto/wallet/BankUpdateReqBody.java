package com.lt.win.apiend.io.dto.wallet;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 钱包-卡片管理-修改默认的银行卡 请求body
 * </p>
 *
 * @author andy
 * @since 2020/4/7
 */
@Data
public class BankUpdateReqBody {

    @NotNull(message = "ID不能为空")
    @ApiModelProperty(name = "id", value = "用户银行卡Id", example = "1")
    private Integer id;

    @NotNull(message = "状态不能为空")
    @ApiModelProperty(name = "status", value = "状态:1-启用(默认) 2-启用 3-停用 4-删除", required = true)
    private Integer status;
}
