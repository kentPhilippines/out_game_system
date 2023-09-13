package com.lt.win.backend.io.bo.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * <p>
 * 会员列表-团队在线-人数 响应
 * </p>
 *
 * @author andy
 * @since 2020/3/21
 */
@Data
@Builder
public class ListOnlineCountResBody {
    @ApiModelProperty(name = "count", value = "在线人数", example = "99")
    private long count = 0;
    @ApiModelProperty(name = "uid", value = "UID", example = "6")
    private Integer uid;
}
