package com.lt.win.backend.io.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 范围查询：开始结束时间
 * </p>
 *
 * @author andy
 * @since 2020/3/20
 */
@Data
public class StartEndTime {
    @ApiModelProperty(name = "startTime", value = "开始时间", example = "1590735421")
    private Integer startTime;
    @ApiModelProperty(name = "endTime", value = "结束时间", example = "1590735421")
    private Integer endTime;
}
