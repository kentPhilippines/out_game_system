package com.lt.win.backend.io.bo.user;

import com.lt.win.backend.io.bo.StartEndTime;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * 会员管理-资金统计 请求body
 * </p>
 *
 * @author andy
 * @since 2020/3/20
 */
@Data
public class CapitalStatisticsListReqBody extends StartEndTime {
    @ApiModelProperty(name = "uid", value = "UID[必填]", example = "1")
    private Integer uid;

    @ApiModelProperty(name = "uidList", value = "uidList", hidden = true)
    private List<Integer> uidList;
}
