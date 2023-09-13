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
public class UidListResBody {

    @ApiModelProperty(name = "id", value = "ID", example = "6")
    private Integer id;
    @ApiModelProperty(name = "username", value = "用户名", example = "test999")
    private String username;
    @ApiModelProperty(name = "levelId", value = "用户等级", example = "1")
    private Integer levelId;

}
