package com.lt.win.backend.io.bo.notice;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 公告管理-查询UID(站内信)
 * </p>
 *
 * @author andy
 * @since 2020/5/7
 */
@Data
public class UidListReqBody {
    @ApiModelProperty(name = "userMark", value = "用户标示", example = "6")
    private String userMark;
}
