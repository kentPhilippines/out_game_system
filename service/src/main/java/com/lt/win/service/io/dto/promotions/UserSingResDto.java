package com.lt.win.service.io.dto.promotions;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: wells
 * @date: 2020/5/15
 * @description:
 */
@Data
@ApiModel(value = "UserSingListResDto", description = "优惠活动签到请求实体")
public class UserSingResDto {
    @ApiModelProperty(name = "userSignList", value = "签到列表")
    private List<UserSingListResDto> userSignList;
    @ApiModelProperty(name = "consecutiveDays", value = "连续天数", example = "10")
    private Integer consecutiveDays;
    @ApiModelProperty(name = "cumulativeAmount", value = "累计金额", example = "108.00")
    private BigDecimal cumulativeAmount;
    @ApiModelProperty(name = "signCoin", value = "签到金额", example = "1")
    private BigDecimal signCoin;
}
