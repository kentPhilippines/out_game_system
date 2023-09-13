package com.lt.win.apiend.io.dto.centeragent;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 代理中心-我的下级-统计 响应body
 * </p>
 *
 * @author andy
 * @since 2020/4/22
 */
@Data
public class SubordinateStatisticsResBody {

    @ApiModelProperty(name = "dlCount", value = "代理人数", example = "200")
    private Integer dlCount;

    @ApiModelProperty(name = "wjCount", value = "玩家人数", example = "100")
    private Integer wjCount;

    @ApiModelProperty(name = "ztCount", value = "直推人数", example = "300")
    private Integer ztCount;

    @ApiModelProperty(name = "tdDlCount", value = "团队:代理人数", example = "500")
    private Integer tdDlCount;

    @ApiModelProperty(name = "tdWjCount", value = "团队:玩家人数", example = "500")
    private Integer tdWjCount;

    @ApiModelProperty(name = "tdZtCount", value = "团队:直推人数", example = "1000")
    private Integer tdZtCount;

}
