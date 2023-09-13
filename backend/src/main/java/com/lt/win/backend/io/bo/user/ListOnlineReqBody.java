package com.lt.win.backend.io.bo.user;

import com.lt.win.utils.components.pagination.BasePage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 会员列表-团队在线 请求
 * </p>
 *
 * @author andy
 * @since 2020/3/21
 */
@Data
public class ListOnlineReqBody extends BasePage {
    @ApiModelProperty(name = "uid", value = "UID", example = "6", required = true)
    @NotNull(message = "uid不能为空")
    private Integer uid;
}
