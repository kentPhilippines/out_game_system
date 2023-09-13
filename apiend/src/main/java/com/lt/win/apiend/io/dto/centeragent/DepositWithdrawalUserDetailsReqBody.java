package com.lt.win.apiend.io.dto.centeragent;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 代理中心-我的报表-充值与提现详情-用户详情ReqBody
 * /p>
 *
 * @author andy
 * @since 2020/7/24
 */
@Data
public class DepositWithdrawalUserDetailsReqBody {

    @ApiModelProperty(name = "uid", value = "UID", example = "1")
    private Integer uid;
}
