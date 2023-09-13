package com.lt.win.apiend.io.dto.centeragent;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 代理中心-我的报表-奖金与佣金详情-列表->活跃会员佣金->会员详情ReqBody
 * /p>
 *
 * @author andy
 * @since 2020/7/24
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RewardsCommissionDetailsActiveReqBody {

    @ApiModelProperty(name = "commissionId", value = "佣金记录Id", example = "91524020444467201")
    private Long commissionId;
}
