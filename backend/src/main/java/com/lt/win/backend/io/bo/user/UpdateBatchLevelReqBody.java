package com.lt.win.backend.io.bo.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 * 会员管理-批量修改等级-请求body
 * </p>
 *
 * @author andy
 * @since 2020/6/8
 */
@Data
public class UpdateBatchLevelReqBody {
    @NotNull(message = "id不能为空")
    @ApiModelProperty(name = "uidList", value = "uidList", example = "[1,2,3]", required = true)
    private List<Integer> uidList;

    @NotNull(message = "levelId不能为空")
    @ApiModelProperty(name = "levelId", value = "会员等级", example = "1", required = true)
    private Integer levelId;
}
