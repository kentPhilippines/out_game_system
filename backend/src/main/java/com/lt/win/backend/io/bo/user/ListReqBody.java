package com.lt.win.backend.io.bo.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * 会员管理-列表查询
 * </p>
 *
 * @author andy
 * @since 2020/3/12
 */
@Data
public class ListReqBody extends UserInfo {
    @ApiModelProperty(name = "startTime", value = "开始时间", example = "1583932627")
    private Integer startTime;
    @ApiModelProperty(name = "endTime", value = "结束时间", example = "1584725218")
    private Integer endTime;
    @ApiModelProperty(name = "mobile", value = "手机号", example = "19912345678")
    private String mobile;
    @ApiModelProperty(name = "onLineNumFlag", value = "是否按在线人数查询:Y-需要 N-不需要,[人数0查全部]", example = "N")
    private String onLineNumFlag;
    @ApiModelProperty(name = "uidList", value = "在线UID列表", hidden = true)
    private List<Integer> uidList;
}
