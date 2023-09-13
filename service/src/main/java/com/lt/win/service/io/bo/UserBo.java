package com.lt.win.service.io.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 会员模块统一入参 出参
 *
 * @author david
 */
public interface UserBo {
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    class ListLevelDto {
        @ApiModelProperty(name = "id", value = "Id", example = "1", required = true)
        private Integer id;
        @ApiModelProperty(name = "code", value = "会员等级", example = "vip0")
        private String code;
        @ApiModelProperty(name = "name", value = "名称", example = "Copper")
        private String name;
        @ApiModelProperty(name = "icon", value = "ICON", example = "111")
        private String icon;
        @ApiModelProperty(name = "scoreUpgradeRate", value = "经验率", example = "100")
        private BigDecimal scoreUpgradeRate;
        @ApiModelProperty(name = "scoreUpgradeMin", value = "最低积分", example = "100")
        private Integer scoreUpgradeMin;
        @ApiModelProperty(name = "scoreUpgradeMax", value = "最高积分", example = "100")
        private Integer scoreUpgradeMax;
        @ApiModelProperty(name = "scoreRelegation", value = "等级保持积分", example = "100")
        private Integer scoreRelegation;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    class AgentCommissionRateDto {
        @ApiModelProperty(name = "agentLevel")
        private Integer agentLevel;
        @ApiModelProperty(name = "agentLevelRate", value = "佣金比例")
        private String agentLevelRate;
    }
}
