package com.lt.win.backend.io.dto;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * 游戏记录
 *
 * @author suki
 */
public interface RecordParams {
    /**
     * 按游戏ID查询注单信息
     */
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    class QueryGameRecordDto {
        @NotNull(message = "游戏ID不能为空")
        @ApiModelProperty(name = "id", value = "游戏ID", example = "1")
        private Integer id;
        @ApiModelProperty(name = "uid", value = "用户ID", example = "1")
        private Integer uid;
        @ApiModelProperty(name = "orderId", value = "注单ID", example = "1")
        private String orderId;
        @ApiModelProperty(name = "username", value = "用户名", example = "1")
        private String username;
        @ApiModelProperty(name = "gameName", value = "游戏名称")
        private String gameName;
        @ApiModelProperty(name = "status", value = "状态", example = "1")
        private Integer status;
        @ApiModelProperty(name = "startTime", value = "创建时间", example = "1")
        private Integer startTime;
        @ApiModelProperty(name = "endTime", value = "创建时间", example = "1")
        private Integer endTime;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    class GameListResBody {

        @ApiModelProperty(name = "id", value = "厂商ID", example = "701")
        private Integer id;
        @ApiModelProperty(name = "name", value = "游戏名称", example = "BWG体育")
        private String name;
        @ApiModelProperty(name = "groupId", value = "游戏组id", example = "彩票")
        private Integer groupId;
        @ApiModelProperty(name = "subcatalogGameList", value = "游戏子类列表")
        private List<SubcatalogGameList> subcatalogGameList;
    }

    @Builder
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class SubcatalogGameList {
        @ApiModelProperty(name = "id", value = "厂商ID", example = "701")
        private String id;
        @ApiModelProperty(name = "name", value = "游戏名称", example = "BWG体育")
        private String name;

    }


    /**
     * E-Sports公共实体类
     */
    @Data
    class ESportsBetslips {
        @ApiModelProperty(name = "id", value = "订单号")
        private String id;
        @ApiModelProperty(name = "xbUid", value = "用户id")
        private String xbUid;
        @ApiModelProperty(name = "xbUsername", value = "用户名")
        private String xbUsername;
        @ApiModelProperty(name = "gameType", value = "游戏类型")
        private String gameType;
        @ApiModelProperty(name = "betOn", value = "投注类型")
        private BigDecimal betOn;
        @ApiModelProperty(name = "xbCoin", value = "投注金额")
        private BigDecimal xbCoin;
        @ApiModelProperty(name = "xbProfit", value = "盈亏金额")
        private BigDecimal xbProfit;
        @ApiModelProperty(name = "xbStatus", value = "状态")
        private String xbStatus;
        @ApiModelProperty(name = "createdAt", value = "投注时间")
        private Integer createdAt;
        @ApiModelProperty(name = "updatedAt", value = "更新时间")
        private Integer updatedAt;
    }

    /**
     * live公共实体类
     */
    @Data
    class LiveBetslips {
        @ApiModelProperty(name = "id", value = "订单号")
        private String id;
        @ApiModelProperty(name = "xbUid", value = "用户id")
        private String xbUid;
        @ApiModelProperty(name = "xbUsername", value = "用户名")
        private String xbUsername;
        @ApiModelProperty(name = "gameType", value = "游戏类型")
        private String gameType;
        @ApiModelProperty(name = "gameName", value = "游戏名称")
        private String gameName;
        @ApiModelProperty(name = "xbCoin", value = "投注金额")
        private BigDecimal xbCoin;
        @ApiModelProperty(name = "xbProfit", value = "盈亏金额")
        private BigDecimal xbProfit;
        @ApiModelProperty(name = "xbStatus", value = "状态")
        private String xbStatus;
        @ApiModelProperty(name = "createdAt", value = "投注时间")
        private Integer createdAt;
        @ApiModelProperty(name = "updatedAt", value = "更新时间")
        private Integer updatedAt;
    }

    /**
     * sport公共实体类
     */
    @Data
    class SportBetslips {
        @ApiModelProperty(name = "id", value = "订单号")
        private String id;
        @ApiModelProperty(name = "xbUid", value = "用户id")
        private String xbUid;
        @ApiModelProperty(name = "xbUsername", value = "用户名")
        private String xbUsername;
        @ApiModelProperty(name = "gameType", value = "游戏类型")
        @JSONField(name = "gameName")
        private String gameType;
        @ApiModelProperty(name = "betType", value = "投注类型")
        private String betType;
        @ApiModelProperty(name = "xbCoin", value = "投注金额")
        private BigDecimal xbCoin;
        @ApiModelProperty(name = "xbProfit", value = "盈亏金额")
        private BigDecimal xbProfit;
        @ApiModelProperty(name = "xbStatus", value = "状态")
        private String xbStatus;
        @ApiModelProperty(name = "createdAt", value = "投注时间")
        private Integer createdAt;
        @ApiModelProperty(name = "updatedAt", value = "更新时间")
        private Integer updatedAt;
    }

    /**
     * E-Games公共实体类
     */
    @Data
    class EGamesBetslips {


        @ApiModelProperty(name = "id", value = "订单号")
        private String id;
        @ApiModelProperty(name = "xbUid", value = "用户id")
        private String xbUid;
        @ApiModelProperty(name = "xbUsername", value = "用户名")
        private String xbUsername;
        @ApiModelProperty(name = "gameType", value = "游戏类型")
        private String gameType;
        @ApiModelProperty(name = "gameName", value = "游戏名称")
        private String gameName;
        @ApiModelProperty(name = "xbCoin", value = "投注金额")
        private BigDecimal xbCoin;
        @ApiModelProperty(name = "xbProfit", value = "盈亏金额")
        private BigDecimal xbProfit;
        @ApiModelProperty(name = "xbStatus", value = "状态")
        private String xbStatus;
        @ApiModelProperty(name = "createdAt", value = "投注时间")
        private Integer createdAt;
        @ApiModelProperty(name = "updatedAt", value = "更新时间")
        private Integer updatedAt;
    }

    /**
     * Lottery公共实体类
     */

    @Data
    class LotteryBetslips {

        @ApiModelProperty(name = "id", value = "订单号")
        private String id;
        @ApiModelProperty(name = "xbUid", value = "用户id")
        private String xbUid;
        @ApiModelProperty(name = "xbUsername", value = "用户名")
        private String xbUsername;
        @ApiModelProperty(name = "betContent", value = "投注内容")
        @JSONField(name = "gameType")
        private String betContent;
        @ApiModelProperty(name = "gameName", value = "游戏名称")
        private String gameName;
        @ApiModelProperty(name = "actionNo", value = "期号")
        private String actionNo;
        @ApiModelProperty(name = "xbCoin", value = "投注金额")
        private BigDecimal xbCoin;
        @ApiModelProperty(name = "coinBefore", value = "投注前金额")
        private BigDecimal coinBefore;
        @ApiModelProperty(name = "xbProfit", value = "盈亏金额")
        private BigDecimal xbProfit;
        @ApiModelProperty(name = "xbStatus", value = "状态")
        private String xbStatus;
        @ApiModelProperty(name = "createdAt", value = "投注时间")
        private Integer createdAt;
        @ApiModelProperty(name = "updatedAt", value = "更新时间")
        private Integer updatedAt;


    }

    /**
     * chess公共实体类
     */

    @Data
    class ChessBetslips {
        @ApiModelProperty(name = "id", value = "订单号")
        private String id;
        @ApiModelProperty(name = "xbUid", value = "用户id")
        private String xbUid;
        @ApiModelProperty(name = "xbUsername", value = "用户名")
        private String xbUsername;
        @ApiModelProperty(name = "player", value = "玩家名")
        private String player;
        @ApiModelProperty(name = "gameType", value = "投注类型")
        private String gameType;
        @ApiModelProperty(name = "gameName", value = "游戏名称")
        private String gameName;
        @ApiModelProperty(name = "xbCoin", value = "投注金额")
        private BigDecimal xbCoin;
        @ApiModelProperty(name = "xbProfit", value = "盈亏金额")
        private BigDecimal xbProfit;
        @ApiModelProperty(name = "xbStatus", value = "状态")
        private String xbStatus;
        @ApiModelProperty(name = "createdAt", value = "投注时间")
        private Integer createdAt;
        @ApiModelProperty(name = "updatedAt", value = "更新时间")
        private Integer updatedAt;

    }

    /**
     * animalFight实体类
     */
    @Data
    class AnimalFightBetslips {
        @ApiModelProperty(name = "id", value = "订单号")
        private String id;
        @ApiModelProperty(name = "xbUid", value = "用户id")
        private String xbUid;
        @ApiModelProperty(name = "xbUsername", value = "用户名")
        private String xbUsername;
        @ApiModelProperty(name = "player", value = "玩家名")
        private String player;
        @ApiModelProperty(name = "matchType", value = "赛事类型")
        @JSONField(name = "gameType")
        private String matchType;
        @ApiModelProperty(name = "betOn", value = "投注类型")
        @JSONField(name = "gameName")
        private String betOn;
        @ApiModelProperty(name = "xbCoin", value = "投注金额")
        private BigDecimal xbCoin;
        @ApiModelProperty(name = "xbProfit", value = "盈亏金额")
        private BigDecimal xbProfit;
        @ApiModelProperty(name = "xbStatus", value = "状态")
        private String xbStatus;
        @ApiModelProperty(name = "createdAt", value = "投注时间")
        private Integer createdAt;
        @ApiModelProperty(name = "updatedAt", value = "更新时间")
        private Integer updatedAt;


    }

    /**
     * Fishing公共实体类
     */
    @Data
    class FishingBetslips {
        @ApiModelProperty(name = "id", value = "订单号")
        private String id;
        @ApiModelProperty(name = "xbUid", value = "用户id")
        private String xbUid;
        @ApiModelProperty(name = "xbUsername", value = "用户名")
        private String xbUsername;
        @ApiModelProperty(name = "player", value = "玩家姓名")
        private String player;
        @ApiModelProperty(name = "gameType", value = "游戏种类")
        @JSONField(name = "gameName")
        private String gameType;
        @ApiModelProperty(name = "xbCoin", value = "投注金额")
        private BigDecimal xbCoin;
        @ApiModelProperty(name = "xbProfit", value = "盈亏金额")
        private BigDecimal xbProfit;
        @ApiModelProperty(name = "xbStatus", value = "状态")
        private String xbStatus;
        @ApiModelProperty(name = "createdAt", value = "投注时间")
        private Integer createdAt;
        @ApiModelProperty(name = "updatedAt", value = "更新时间")
        private Integer updatedAt;
    }

    @Data
    class GrossResBody {
        @ApiModelProperty(name = "orderNum", value = "订单数")
        private Integer orderNum;
        @ApiModelProperty(name = "betAmount", value = "投注总额")
        private BigDecimal betAmount;
        @ApiModelProperty(name = "validAmount", value = "有效打码量")
        private BigDecimal validAmount;
        @ApiModelProperty(name = "profitAmount", value = "输赢总额")
        private BigDecimal profitAmount;
    }
}
