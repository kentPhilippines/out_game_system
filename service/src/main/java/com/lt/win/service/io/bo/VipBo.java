package com.lt.win.service.io.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * <p>
 * 代理下级列表
 * </p>
 *
 * @author david
 * @since 2020/7/24
 */
public interface VipBo {
    /**
     * 代理 MemberShip Benefits
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    class MemberShipResDto {
        @ApiModelProperty(name = "code", value = "Vip代号", example = "100")
        private String code;
        @ApiModelProperty(name = "name", value = "Vip名称", example = "100")
        private String name;
        @ApiModelProperty(name = "nextCode", value = "下一级代号", example = "100")
        private String nextCode = "";
        @ApiModelProperty(name = "name", value = "下一级名称", example = "100")
        private String nextName = "";
        @ApiModelProperty(name = "scoreUpgradeMin", value = "升级最低积分", example = "100")
        private Integer scoreUpgradeMin;
        @ApiModelProperty(name = "scoreUpgradeMax", value = "升级最高积分", example = "100")
        private Integer scoreUpgradeMax;
        @ApiModelProperty(name = "score", value = "现有积分", example = "100")
        private Integer score;
        @ApiModelProperty(name = "accumulatedDeposit", value = "累计充值", example = "100")
        private BigDecimal accumulatedDeposit;
    }

    /**
     * 代理 MemberShip Level Benefits
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    class MemberShipLevelResDto {
        @ApiModelProperty(name = "id", value = "id", example = "100")
        private Integer id;
        @ApiModelProperty(name = "code", value = "编码", example = "100")
        private String code;
        @ApiModelProperty(name = "name", value = "等级名称", example = "100")
        private String name;
        @ApiModelProperty(name = "icon", value = "等级图标", example = "100")
        private String icon;
        @ApiModelProperty(name = "points", value = "起始积分", example = "100")
        private Integer points;
        @ApiModelProperty(name = "pointsMultiplier", value = "升级倍数", example = "1.5")
        private BigDecimal pointsMultiplier;
        @ApiModelProperty(name = "tierReward", value = "到下一级积分", example = "100")
        private Integer tierReward;
    }

    /**
     * 代理 MemberShip Benefits
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    class Currency {
        @ApiModelProperty(name = "code", value = "Vip代号", example = "100")
        private String code;
        @ApiModelProperty(name = "accumulatedDeposit", value = "累计充值", example = "100")
        private BigDecimal accumulatedDeposit;
        @ApiModelProperty(name = "scoreUpgradeMin", value = "升级最低积分", example = "100")
        private Integer scoreUpgradeMin;
        @ApiModelProperty(name = "scoreUpgradeMax", value = "升级最高积分", example = "100")
        private Integer scoreUpgradeMax;
        @ApiModelProperty(name = "score", value = "现有积分", example = "100")
        private Integer score;
    }
}
