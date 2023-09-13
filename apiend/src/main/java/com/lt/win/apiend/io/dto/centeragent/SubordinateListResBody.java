package com.lt.win.apiend.io.dto.centeragent;

import com.lt.win.utils.components.pagination.ResPage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>
 * 代理中心-我的下级
 * </p>
 *
 * @author andy
 * @since 2020/4/22
 */
@Data
public class SubordinateListResBody {
    @ApiModelProperty(name = "activeCount", value = "在线人数", example = "100")
    private Integer activeCount;
    @ApiModelProperty(name = "offlineCount", value = "离线人数", example = "100")
    private Integer offlineCount;
    @ApiModelProperty(name = "subordinateList", value = "下级列表", example = "100")
    private ResPage<SubordinateList> subordinateList;


    @Data
    public static class SubordinateList {
        @ApiModelProperty(name = "username", value = "用户名称", example = "andy")
        private String username;

        @ApiModelProperty(name = "role", value = "用户类型:0-会员 1-代理", example = "0")
        private Integer role;

        @ApiModelProperty(name = "dlCount", value = "代理人数", example = "300")
        private Integer dlCount;

        @ApiModelProperty(name = "wjCount", value = "玩家人数", example = "200")
        private Integer wjCount;

        @ApiModelProperty(name = "ztCount", value = "直推人数", example = "100")
        private Integer ztCount;

        @ApiModelProperty(name = "coin", value = "金额", example = "100")
        private BigDecimal coin;

        @ApiModelProperty(name = "status", value = "在线状态", example = "100")
        private Integer status;

        @ApiModelProperty(name = "createdAt", value = "时间", example = "1587226981")
        private Integer createdAt;

        @ApiModelProperty(name = "uid", value = "uid", example = "1")
        private Integer uid;
    }
}
