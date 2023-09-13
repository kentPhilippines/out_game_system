package com.lt.win.apiend.io.dto.centeragent;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 代理中心-我的下级
 * </p>
 *
 * @author andy
 * @since 2020/4/22
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubordinateListReqBody {

    @ApiModelProperty(name = "username", value = "用户名称", example = "andy")
    private String username;

    @ApiModelProperty(name = "role", value = "用户类型:0-会员 1-代理", example = "0")
    private Integer role;

    @ApiModelProperty(hidden = true)
    private Integer uid;

    @ApiModelProperty(name = "status", value = "在线状态:0-离线 1-在线", example = "0")
    private String status;

    @ApiModelProperty(hidden = true)
    private Integer startTime;

    @ApiModelProperty(hidden = true)
    private Integer endTime;
}
