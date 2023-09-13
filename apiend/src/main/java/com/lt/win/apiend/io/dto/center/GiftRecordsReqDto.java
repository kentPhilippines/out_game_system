package com.lt.win.apiend.io.dto.center;

import com.lt.win.apiend.io.dto.StartEndTime;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @author: David
 * @date: 17/04/2020
 * @description:
 */
@Data
public class GiftRecordsReqDto extends StartEndTime {
    @Min(value = 0, message = "范围0-4")
    @Max(value = 4, message = "范围0-4")
    @ApiModelProperty(name = "status", value = "状态:0-申请中 1-同意 2-拒绝 3-已发货 4-已送达", example = "1")
    private Integer status;
}
