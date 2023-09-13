package com.lt.win.apiend.io.dto.centeragent;

import com.lt.win.utils.components.pagination.ResPage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 代理中心-我的下级-列表-直推人数列表(弹框)
 * </p>
 *
 * @author andy
 * @since 2020/5/4
 */
@Data
public class SubordinateListZtResBody {

    @ApiModelProperty(name = "userInfo", value = "用户信息")
    private UserInfoDto userInfo;

    @ApiModelProperty(name = "listZT", value = "列表")
    private ResPage<SubordinateListZt> listZT;

}
