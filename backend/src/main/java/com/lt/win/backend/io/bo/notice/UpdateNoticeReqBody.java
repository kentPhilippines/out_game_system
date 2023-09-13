package com.lt.win.backend.io.bo.notice;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 公告管理-修改公告
 * </p>
 *
 * @author andy
 * @since 2020/5/6
 */
@Data
public class UpdateNoticeReqBody extends AddNoticeReqBody {
    @NotNull(message = "id不能为空")
    @ApiModelProperty(name = "id", value = "ID", example = "1", required = true)
    private Integer id;
}
