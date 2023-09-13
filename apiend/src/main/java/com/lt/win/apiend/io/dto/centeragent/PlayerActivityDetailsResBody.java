package com.lt.win.apiend.io.dto.centeragent;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * <p>
 * 代理中心-我的报表-玩家活跃度详情ResBody
 * </p>
 *
 * @author andy
 * @since 2020/7/24
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlayerActivityDetailsResBody {
    @ApiModelProperty(name = "username", value = "用户名称", example = "andy")
    private String username;

    @ApiModelProperty(name = "coin", value = "金额", example = "2000")
    private BigDecimal coin;

    @ApiModelProperty(name = "createdAt", value = "时间", example = "1587226981")
    private Integer createdAt;

    @ApiModelProperty(name = "uid", value = "UID", example = "1")
    private Integer uid;
}
