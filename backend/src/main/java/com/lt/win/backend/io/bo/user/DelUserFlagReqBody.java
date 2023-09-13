package com.lt.win.backend.io.bo.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * <p>
 * 会员旗管理-用户会员旗列表(弹框)-批量删除
 * </p>
 *
 * @author andy
 * @since 2020/3/25
 */
@Data
public class DelUserFlagReqBody {
    @ApiModelProperty(name = "uidList", value = "uid列表：Integer类型", required = true)
    @NotEmpty(message = "uidList不能为空")
    private List<Integer> uidList;
}
