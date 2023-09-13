package com.lt.win.backend.io.bo.user;

import com.lt.win.utils.components.pagination.BasePage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 会员管理-提款消费明细
 * </p>
 *
 * @author andy
 * @since 2020/6/5
 */
@Data
public class ListCodeRecordsReqBody extends BasePage {
    @NotNull(message = "uid不能为空")
    @ApiModelProperty(name = "uid", value = "UID", example = "1", required = true)
    private Integer uid;
}
