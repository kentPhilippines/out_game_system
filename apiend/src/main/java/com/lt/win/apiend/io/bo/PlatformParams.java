package com.lt.win.apiend.io.bo;

import com.lt.win.apiend.io.dto.StartEndTime;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Null;
import java.math.BigDecimal;

/**
 * @description: 平台游戏 参数
 * @author: David
 * @date: 04/06/2020
 */
public interface PlatformParams {
    /**
     * 平台按日期查询统计金额、注单信息公共入参
     */
    @EqualsAndHashCode(callSuper = true)
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    class GameQueryDateDto extends StartEndTime {
        @ApiModelProperty(name = "id", value = "游戏ID", example = "all不传 other->gameId")
        private Integer id;
        @ApiModelProperty(name = "isAgent", value = "代理", example = "单用户不传 1->代理")
        private Integer isAgent;
        @ApiModelProperty(name = "username", value = "用户名", example = "")
        private String username;
    }

    /**
     * 平台按日期查询统计金额、注单信息公共入参
     */
    @EqualsAndHashCode(callSuper = true)
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    class GameCoinStatisticsByDateReqDto extends StartEndTime {
        @Null(message = "ID不能为空")
        @ApiModelProperty(name = "id", value = "游戏ID", example = "1")
        private Integer id;
    }

    /**
     * 平台按日期查询投注金额、盈亏金额
     */
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    class CoinStatisticsResDto {
        @ApiModelProperty(name = "coinBet", value = "投注金额", example = "101")
        private BigDecimal coinBet;

        @ApiModelProperty(name = "coinProfit", value = "盈亏金额", example = "100.00")
        private BigDecimal coinProfit;
    }

    /**
     * 查询平台列表
     */
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    class PlatListResInfo {
        @ApiModelProperty(name = "id", value = "平台ID", example = "601")
        private Integer id;
        @ApiModelProperty(name = "name", value = "名称",example = "龙游")
        private String name;
        @ApiModelProperty(name = "nameZh", value = "名称(中文)")
        private String nameZh;
    }

    /**
     * 查询平台列表
     */
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    class PlatTransferResInfo {
        @ApiModelProperty(name = "coinUp", value = "上分金额", example = "601")
        private BigDecimal coinUp;
        @ApiModelProperty(name = "coinDown", value = "下分金额　")
        private BigDecimal coinDown;
    }
}
