package com.lt.win.backend.io.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * @description: 平台游戏 参数
 * @author: David
 * @date: 04/06/2020
 */
public interface Platform {
    /**
     * 第三方平台列表
     */
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    class PlatListResInfo {
        @ApiModelProperty(name = "id", value = "平台ID", example = "601")
        private Integer id;
        @ApiModelProperty(name = "name", value = "名称")
        private String name;
        @ApiModelProperty(name = "nameZh", value = "名称(中文)")
        private String nameZh;
    }

    /**
     * 第三方平台游戏列表
     */
    @Data
    class GameListInfo {

        @ApiModelProperty(name = "id", value = "游戏列表ID", example = "601")
        private Integer id;

        @ApiModelProperty(name = "name", value = "名称", example = "泛亚电竞")
        private String name;

        @ApiModelProperty(name = "icon", value = "logo")
        private String icon;

        @ApiModelProperty(name = "groupId", value = "gameGroupId游戏种类:1-体育 2-电子 3-真人 4-捕鱼 5-棋牌 6-电竞 7-彩票", example = "6")
        private Integer groupId;

        @ApiModelProperty(name = "model", value = "model", hidden = true)
        private String model;
    }

    /**
     * 三方平台游戏列表
     */
    @Data
    class GameListReqDto {
        @ApiModelProperty(name = "groupId", value = "游戏组:1-体育 2-电子 3-真人 4-捕鱼 5-棋牌 6-电竞 7-彩票 8-电竞 9-快速游戏 10-技能游戏 不填-全部", example = "2")
        private int groupId;


    }

    /**
     * 第三方平台余额查询:reqBody
     */
    @Data
    class GameBalanceReqDto {
        @NotNull
        @ApiModelProperty(name = "id", value = "游戏ID", example = "1")
        private Integer id;

        @ApiModelProperty(name = "UID", value = "UID", example = "1")
        private Integer uid;
    }

    /**
     * 第三方平台余额查询:resBody
     */
    @Data
    @Builder
    class GameBalanceResDto {
        @ApiModelProperty(name = "id", value = "游戏ID", example = "101")
        private Integer id;
        @ApiModelProperty(name = "coin", value = "余额", example = "100.00")
        private BigDecimal coin;
        @ApiModelProperty(name = "uid", value = "用户id", example = "100.00")
        private Integer uid;
    }

    /**
     * 三方平台游戏列表
     */
    @Data
    class CheckTransferStatusReqDto {
        @NotNull
        @ApiModelProperty(name = "orderId", value = "上下分主键ID", example = "2")
        private String orderId;
    }

    /**
     * 三方平台游戏列表补单入参数
     */
    @Data
    class GenBetSlipsSupplementalReqDto {
        @NotNull
        @ApiModelProperty(name = "gameId", value = "游戏ID", example = "2")
        private Integer gameId;
        @NotNull
        @ApiModelProperty(name = "startTime", value = "开始时间", example = "2")
        private Integer startTime;
        @NotNull
        @ApiModelProperty(name = "endTime", value = "结束时间", example = "2")
        private Integer endTime;
    }

    /**
     * 三方平台补单入参
     */
    @Data
    class BetSlipsSupplementalReqDto {
        @NotNull(message = "id不能为空")
        @ApiModelProperty(name = "id", value = "订单ID", example = "10")
        private Integer id;
    }

    /**
     * 三方平台拉单异常补单入参
     */
    @Data
    class BetSlipsExceptionReqDto {
        @NotNull(message = "id不能为空")
        @ApiModelProperty(name = "id", value = "订单ID", example = "10")
        private Integer id;
    }

    /**
     * 老虎机游戏补单接口
     */
    @Data
    class SyncSlotGameReqDto {
        @NotNull(message = "id不能为空")
        @ApiModelProperty(name = "gameId", value = "老虎机ID列表", example = "10")
        private Integer gameId;
    }

    /**
     * 三级联动:游戏类型->平台名称->游戏名称:第一级/游戏类型
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    class GameGroupDict {
        @ApiModelProperty(name = "groupId", value = "游戏groupId->1-体育 2-电子 3-真人 4-捕鱼 5-棋牌 6-电竞 7-彩票 8动物", example = "1")
        private Integer groupId;
        @ApiModelProperty(name = "groupName", value = "游戏groupName:1-体育 2-电子 3-真人 4-捕鱼 5-棋牌 6-电竞 7-彩票 8动物", example = "体育")
        private String groupName;

        /**
         * 一对多
         */
        @ApiModelProperty(name = "list", value = "平台名称列表")
        private List<GameListDict> list;
    }

    /**
     * 三级联动:游戏类型->平台名称->游戏名称:第二级/平台名称
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    class GameListDict {
        @ApiModelProperty(name = "gameListId", value = "平台ID", example = "703")
        private Integer gameListId;
        @ApiModelProperty(name = "gameListName", value = "平台名称", example = "Futures")
        private String gameListName;

        /**
         * 一对多
         */
        @ApiModelProperty(name = "list", value = "游戏名称列表")
        private List<GameListSubDict> list;

    }

    /**
     * 三级联动:游戏类型->平台名称->游戏名称:第三级/游戏名称
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    class GameListSubDict {
        @ApiModelProperty(name = "gameId", value = "游戏ID", example = "100")
        private String gameId;
        @ApiModelProperty(name = "gameName", value = "游戏名称", example = "PARITY")
        private String gameName;

    }


}
