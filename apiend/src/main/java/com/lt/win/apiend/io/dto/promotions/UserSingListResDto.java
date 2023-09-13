package com.lt.win.apiend.io.dto.promotions;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: wells
 * @date: 2020/5/2
 * @description:
 */
@Data
@ApiModel(value = "UserSingListResDto", description = "优惠活动查看签到请求实体")
public class UserSingListResDto {
    @ApiModelProperty(name = "weekOfDay", value = "一周的某天", example = "1")
    private Integer weekOfDay;
    @ApiModelProperty(name = "status", value = "状态->0:已过期,1:已签到,2,未签到", example = "1")
    private Integer status;
}
