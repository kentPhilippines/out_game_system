package com.lt.win.backend.io.dto;

import com.lt.win.service.io.dto.BaseParams;
import com.lt.win.utils.components.pagination.ResPage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;


/**
 * @author: David
 * @date: 2022/8/31
 * <p>
 * 活动参数
 */

public interface PromotionsParameter {
    @Data
    @ApiModel(value = "PromotionsGroupResDto", description = "活动组请求实体类")
    class PromotionsGroupResDto {
        @ApiModelProperty(name = "groupId", value = "活动组ID")
        private Integer groupId;
        @ApiModelProperty(name = "groupCodeZh", value = "活动组名称")
        private String groupCodeZh;
        @ApiModelProperty(name = "promotionsList", value = "活动组的活动")
        private List<PromotionsResDto> promotionsList;
    }

    @Data
    @ApiModel(value = "PromotionsResDto", description = "活动组活动请求实体类")
    class PromotionsResDto {
        @ApiModelProperty(name = "id", value = "ID")
        private Integer id;
        @ApiModelProperty(name = "codeZh", value = "活动标题", example = "一倍流水首存")
        private String codeZh;
    }


    @Data
    @ApiModel(value = "ListReqDto", description = "活动列表请求实体类")
    class ListReqDto {
        @ApiModelProperty(name = "lang", value = "语言", example = "zh")
        private String lang;
        @ApiModelProperty(name = "codeZh", value = "活动标题", example = "一倍流水首存")
        private String codeZh;
        @ApiModelProperty(name = "category", value = "活动类型", example = "1")
        private Integer category;
        @ApiModelProperty(name = "status", value = "状态:1-启用 0-停用", example = "1")
        private Integer status;
        @ApiModelProperty(name = "startTime", value = "开始时间", example = "1587224407")
        private Integer startTime;
        @ApiModelProperty(name = "endTime", value = "结束时间", example = "1587224407")
        private Integer endTime;

    }

    @Data
    @ApiModel(value = "ListResDto", description = "活动列表响应实体类")
    class ListResDto {
        @ApiModelProperty(name = "id", value = "ID")
        private Integer id;
        @ApiModelProperty(name = "code", value = "活动标识")
        private String code;
        @ApiModelProperty(name = "codeZh", value = "活动标题")
        private String codeZh;
        @ApiModelProperty(name = "img", value = "图片路径")
        private String img;
        @ApiModelProperty(name = "category", value = "活动类型")
        private String category;
        @ApiModelProperty(name = "description", value = "活动内容")
        private String description;
        @ApiModelProperty(name = "startedAt", value = "开始时间")
        private Integer startedAt;
        @ApiModelProperty(name = "endedAt", value = "结束时间")
        private Integer endedAt;
        @ApiModelProperty(name = "status", value = "状态:1-启用 0-停用")
        private Integer status;
        @ApiModelProperty(name = "flowClaim", value = "流水倍数", example = "100")
        private Integer flowClaim;
        @ApiModelProperty(name = "minCoin", value = "最低充值金额", example = "100")
        private BigDecimal minCoin;
        @ApiModelProperty(name = "maxCoin", value = "最高充值金额", example = "100")
        private BigDecimal maxCoin;
        @ApiModelProperty(name = "rate", value = "赠送比例", example = "100")
        private BigDecimal rate;
        @ApiModelProperty(name = "depositNums", value = "存款次数-续存优惠使用", example = "100")
        private Integer depositNums;
        @ApiModelProperty(name = "subCategory", value = "首单包赔-1-限制申请前投注 0-不限制", example = "100")
        private Integer subCategory;
        @ApiModelProperty(name = "weekdays", value = "每周几(快乐周五)使用", example = "100")
        private Integer weekdays;
    }

    @Data
    @ApiModel(value = "UpdateReqDto", description = "修改活动请求实体类")
    class SavaOrUpdateReqDto {
        @ApiModelProperty(name = "id", value = "ID", example = "1")
        private Integer id;
        @ApiModelProperty(name = "code", value = "活动标识")
        private String code;
        @NotNull(message = "lang不能空")
        @ApiModelProperty(name = "lang", value = "请求语音", example = "zh")
        private String lang;
        @NotNull(message = "活动标题不能空")
        @ApiModelProperty(name = "codeZh", value = "活动标题", example = "一倍流水首存")
        private String codeZh;
        @ApiModelProperty(name = "img", value = "图片标识", example = "img")
        private String img;
        @NotNull(message = "活动类型不能空")
        @ApiModelProperty(name = "category", value = "活动类型", example = "1")
        private Integer category;
        @ApiModelProperty(name = "description", value = "活动内容", example = "****")
        private String description;
        @ApiModelProperty(name = "status", value = "状态:1-启用 0-停用", example = "1")
        private Integer status;
        @ApiModelProperty(name = "flowClaim", value = "流水倍数", example = "100")
        private Integer flowClaim;
        @ApiModelProperty(name = "minCoin", value = "最低充值金额", example = "100")
        private BigDecimal minCoin;
        @ApiModelProperty(name = "maxCoin", value = "最高充值金额", example = "100")
        private BigDecimal maxCoin;
        @ApiModelProperty(name = "rate", value = "赠送比例", example = "100")
        private BigDecimal rate;
        @ApiModelProperty(name = "depositNums", value = "存款次数-续存优惠使用", example = "100")
        private Integer depositNums;
        @ApiModelProperty(name = "subCategory", value = "首单包赔-1-限制申请前投注 0-不限制", example = "100")
        private Integer subCategory;
        @ApiModelProperty(name = "weekdays", value = "每周几(快乐周五)使用", example = "100")
        private Integer weekdays;
        @ApiModelProperty(name = "startTime", value = "开始时间", example = "1587224407")
        private Integer startTime;
        @ApiModelProperty(name = "endTime", value = "结束时间", example = "1587224407")
        private Integer endTime;
    }


    @Data
    @ApiModel(value = "DeleteReqDto", description = "删除活动请求实体类")
    class DeleteReqDto {
        @ApiModelProperty(name = "id", value = "ID", example = "1")
        @NotNull(message = "id不能为空！")
        private Long id;
    }

    @Data
    @ApiModel(value = "SingListReqDto", description = "签到列表请求实体")
    class SingListReqDto {
        @ApiModelProperty(name = "uid", value = "用户ID", example = "26")
        private Integer uid;
        @ApiModelProperty(name = "username", value = "用户名", example = "老王")
        private String username;
        @ApiModelProperty(name = "startTime", value = "开始时间", example = "1587224407")
        private Integer startTime;
        @ApiModelProperty(name = "endTime", value = "结束时间", example = "1587224407")
        private Integer endTime;
    }

    @Data
    @Builder
    @ApiModel(value = "SingListResDto", description = "签到列表响应实体")
    class SingListResDto {
        @ApiModelProperty(name = "totalRewardsCoin", value = "总领取彩金")
        private BigDecimal totalRewardsCoin;
        @ApiModelProperty(name = "singList", value = "签到列表数据")
        private ResPage<SingList> singList;
    }

    @Data
    @ApiModel(value = "SingList", description = "签到列表响应实体")
    class SingList {
        @ApiModelProperty(name = "uid", value = "用户ID", example = "26")
        private Integer uid;
        @ApiModelProperty(name = "username", value = "用户名", example = "ww")
        private String username;
        @ApiModelProperty(name = "signDay", value = "已签到天数", example = "10")
        private Integer signDay;
        @ApiModelProperty(name = "receiveCoin", value = "共领取彩金", example = "100.11")
        private BigDecimal receiveCoin;
    }

    @Data
    @ApiModel(value = "SingDetailReqDto", description = "签到详情请求实体类")
    class SingDetailReqDto {
        @ApiModelProperty(name = "id", value = "记录ID", example = "69743419269976064")
        private Long id;
        @ApiModelProperty(name = "uid", value = "用户ID", example = "26")
        private Integer uid;
        @ApiModelProperty(name = "year", value = "年份", example = "2020")
        private Integer year;
        @ApiModelProperty(name = "nw", value = "周数", example = "2")
        private Integer nw;
    }

    @Data
    @ApiModel(value = "SingDetailResDto", description = "签到详情响应实体类")
    class SingDetailResDto {
        @ApiModelProperty(name = "id", value = "ID", example = "1")
        private Long id;
        @ApiModelProperty(name = "uid", value = "用户ID")
        private Integer uid;
        @ApiModelProperty(name = "userName", value = "用户名称")
        private String userName;
        @ApiModelProperty(name = "year", value = "年分")
        private Integer year;
        @ApiModelProperty(name = "nw", value = "周数")
        private Integer nw;
        @ApiModelProperty(name = "dayOfWeek", value = "已签到")
        private String dayOfWeek;
        @ApiModelProperty(name = "updatedAt", value = "修改时间")
        private Integer updatedAt;
    }

    @Data
    @ApiModel(value = "RewardsReqDto", description = "活动请求实体类")
    class RewardsReqDto {
        @ApiModelProperty(name = "id", value = "ID", example = "1")
        private Long id;
        @ApiModelProperty(name = "uid", value = "用户ID", example = "25")
        private Integer uid;
        @ApiModelProperty(name = "username", value = "用户名", example = "老王")
        private String username;
        @ApiModelProperty(name = "category", value = "类型:0-被邀请奖金1-邀请奖金 2-充值返利；", example = "25")
        private Integer category;
        @NotNull(message = "活动ID不能为空！")
        @ApiModelProperty(name = "referId", value = "活动ID（不能为空）", example = "1")
        private Integer referId;
        @ApiModelProperty(name = "startTime", value = "开始时间", example = "1587224407")
        private Integer startTime;
        @ApiModelProperty(name = "endTime", value = "结束时间", example = "1587224407")
        private Integer endTime;
    }

    @Data
    @Builder
    @ApiModel(value = "RewardsReDto", description = "活动默认响应实体类")
    class RewardsReDto {
        @ApiModelProperty(name = "totalRewardsCoin", value = "总领取彩金")
        private BigDecimal totalRewardsCoin;
        @ApiModelProperty(name = "rewardsList", value = "首充列表数据")
        private ResPage<RewardsDefaultList> rewardsList;
    }


    @Data
    @Builder
    @ApiModel(value = "FirstDepositResDto", description = "首充活动响应实体类")
    class FirstDepositResDto {
        @ApiModelProperty(name = "totalRewardsCoin", value = "总领取彩金")
        private BigDecimal totalRewardsCoin;
        @ApiModelProperty(name = "rewardsList", value = "首充列表数据")
        private ResPage<RewardsList> rewardsList;
    }

    @Data
    class RewardsList {
        @ApiModelProperty(name = "depositCategory", value = "类型:1-银联 2-微信 3-支付宝 4-QQ 5-QR扫码;dic_pay_online_category")
        private Integer depositCategory;
        @ApiModelProperty(name = "depositType", value = "充值方式:0-离线 1-在线;dic_coin_deposit_pay_type")
        private Integer depositType;
        @ApiModelProperty(name = "depositCoin", value = "充值金额")
        private BigDecimal depositCoin;
        @ApiModelProperty(name = "id", value = "ID", example = "1")
        private Long id;
        @ApiModelProperty(name = "uid", value = "用户ID")
        private Integer uid;
        @ApiModelProperty(name = "username", value = "用户名", example = "老王")
        private String username;
        @ApiModelProperty(name = "codeZh", value = "活动标题")
        private String codeZh;
        @ApiModelProperty(name = "referId", value = "活动ID")
        private Integer referId;
        @ApiModelProperty(name = "coin", value = "赠送金额")
        private BigDecimal coin;
        @ApiModelProperty(name = "created_at", value = "创建时间")
        private Integer createdAt;
        @ApiModelProperty(name = "status", value = "状态:1-正常 0-撤销")
        private Integer status;
    }

    @Data
    class RewardsDefaultList {
        @ApiModelProperty(name = "id", value = "ID", example = "1")
        private Long id;
        @ApiModelProperty(name = "uid", value = "用户ID")
        private Integer uid;
        @ApiModelProperty(name = "username", value = "用户名", example = "老王")
        private String username;
        @ApiModelProperty(name = "codeZh", value = "活动标题")
        private String codeZh;
        @ApiModelProperty(name = "referId", value = "活动ID")
        private Integer referId;
        @ApiModelProperty(name = "coin", value = "派彩金额")
        private BigDecimal coin;
        @ApiModelProperty(name = "betCoin", value = "有效投注金额")
        private BigDecimal betCoin;
        @ApiModelProperty(name = "details", value = "游戏详细信息")
        private String details;
        @ApiModelProperty(name = "created_at", value = "创建时间")
        private Integer createdAt;
        @ApiModelProperty(name = "status", value = "状态:1-正常 0-撤销")
        private Integer status;
    }


    @Data
    @Builder
    @ApiModel(value = "SportAndLiveResDto", description = "体育与真人响应实体类")
    class SportAndLiveResDto {
        @ApiModelProperty(name = "totalRewardsCoin", value = "总领取彩金")
        private BigDecimal totalRewardsCoin;
        @ApiModelProperty(name = "sportAndLiveList", value = "体育与真人列表数据")
        private ResPage<SportsAndLiveList> sportAndLiveList;
    }

    @Data
    class SportsAndLiveList {
        @ApiModelProperty(name = "id", value = "ID", example = "1")
        private Long id;
        @ApiModelProperty(name = "uid", value = "用户ID")
        private Integer uid;
        @ApiModelProperty(name = "username", value = "用户名", example = "老王")
        private String username;
        @ApiModelProperty(name = "codeZh", value = "活动标题")
        private String codeZh;
        @ApiModelProperty(name = "referId", value = "活动ID")
        private Integer referId;
        @ApiModelProperty(name = "gameName", value = "游戏名称")
        private String gameName;
        @ApiModelProperty(name = "games", value = "连赢场数")
        private Integer games;
        @ApiModelProperty(name = "betCoin", value = "有效投注")
        private BigDecimal betCoin;
        @ApiModelProperty(name = "winCoin", value = "派彩金额")
        private BigDecimal winCoin;
        @ApiModelProperty(name = "coin", value = "赠送金额")
        private BigDecimal coin;
        @ApiModelProperty(name = "created_at", value = "创建时间")
        private Integer createdAt;
        @ApiModelProperty(name = "status", value = "状态:1-正常 0-撤销")
        private Integer status;
    }

    @Data
    @Builder
    @ApiModel(value = "RedEnvelopeWarListResDto", description = "红包雨列表响应实体类")
    class RedEnvelopeWarListResDto {
        @ApiModelProperty(name = "totalRewardsCoin", value = "总领取彩金")
        private BigDecimal totalRewardsCoin;
        @ApiModelProperty(name = "redWarList", value = "红包雨列表数据")
        private ResPage<RedEnvelopeWarResDto> redWarList;
    }

    @Data
    @ApiModel(value = "RedEnvelopeWarResDto", description = "红包雨响应实体类")
    class RedEnvelopeWarResDto {
        @ApiModelProperty(name = "uid", value = "用户ID")
        private Integer uid;
        @ApiModelProperty(name = "referId", value = "活动ID")
        private Integer referId;
        @ApiModelProperty(name = "codeZh", value = "活动标题")
        private String codeZh;
        @ApiModelProperty(name = "coin", value = "红包金额")
        private BigDecimal coin;
        @ApiModelProperty(name = "createdAt", value = "领取时间")
        private Integer createdAt;
        @ApiModelProperty(name = "status", value = "状态:1-正常 0-撤销")
        private Integer status;
    }

    @Data
    @ApiModel(value = "AllRebateListReqDto", description = "返水列表请求实体类")
    class AllRebateListReqDto {
        @ApiModelProperty(name = "uid", value = "用户ID", example = "25")
        private Integer uid;
        @ApiModelProperty(name = "username", value = "用户名", example = "老王")
        private String username;
        @ApiModelProperty(name = "id", value = "ID", example = "70528990917562368")
        private Long id;
        @ApiModelProperty(name = "referId", value = "活动ID", example = "10")
        private Integer referId;
        @ApiModelProperty(name = "startTime", value = "开始时间", example = "1587224407")
        private Integer startTime;
        @ApiModelProperty(name = "endTime", value = "结束时间", example = "1587224407")
        private Integer endTime;
    }

    @Data
    @Builder
    @ApiModel(value = "AllRebateListResDto", description = "返水列表响应实体类")
    class AllRebateListResDto {
        @ApiModelProperty(name = "totalRewardsCoin", value = "总领取彩金")
        private BigDecimal totalRewardsCoin;
        @ApiModelProperty(name = "allRebateList", value = "红包雨列表数据")
        private ResPage<AllRebate> allRebateList;
    }

    @Data
    @ApiModel(value = "AllRebate", description = "返水响应实体类")
    class AllRebate {
        @ApiModelProperty(name = "id", value = "记录ID")
        private Long id;
        @ApiModelProperty(name = "uid", value = "用户ID")
        private Integer uid;
        @ApiModelProperty(name = "username", value = "用户名", example = "老王")
        private String username;
        @ApiModelProperty(name = "gameName", value = "返水类型")
        private String gameName;
        @ApiModelProperty(name = "codeZh", value = "活动标题")
        private String codeZh;
        @ApiModelProperty(name = "coinBetValid", value = "有效投注额")
        private BigDecimal coinBetValid;
        @ApiModelProperty(name = "coin", value = "派彩金额")
        private BigDecimal coin;
        @ApiModelProperty(name = "createdAt", value = "创建时间")
        private Integer createdAt;
        @ApiModelProperty(name = "status", value = "状态:1-正常 0-撤销")
        private Integer status;
    }


    @Data
    @Builder
    @ApiModel(value = "FriendRebateListResDto", description = "邀请好友返水响应实体类")
    class FriendRebateListResDto {
        @ApiModelProperty(name = "totalRewardsCoin", value = "总领取彩金")
        private BigDecimal totalRewardsCoin;
        @ApiModelProperty(name = "friendRebateList", value = "红包雨列表数据")
        private ResPage<FriendRebate> friendRebateList;
    }

    @Data
    @ApiModel(value = "FriendRebate", description = "邀请好友返水实体类")
    class FriendRebate {
        @ApiModelProperty(name = "id", value = "ID", example = "1")
        private Long id;
        @ApiModelProperty(name = "codeZh", value = "活动标题")
        private String codeZh;
        @ApiModelProperty(name = "referId", value = "活动ID")
        private Integer referId;
        @ApiModelProperty(name = "category", value = "类型:0-被邀请奖金1-邀请奖金 2-充值返利")
        private Integer category;
        @ApiModelProperty(name = "uid", value = "uid", example = "1")
        private Integer uid;
        @ApiModelProperty(name = "username", value = "用户名", example = "老王")
        private String username;
        @ApiModelProperty(name = "beInviteId", value = "被邀请人ID", example = "2")
        private Integer beInviteId;
        @ApiModelProperty(name = "inviteCoin", value = "邀请金额", example = "2.1")
        private BigDecimal inviteCoin;
        @ApiModelProperty(name = "coin", value = "赠送金额", example = "2.1")
        private BigDecimal coin;
        @ApiModelProperty(name = "createdAt", value = "创建时间", example = "1588470650")
        private Integer createdAt;
        @ApiModelProperty(name = "status", value = "状态:1-正常 0-撤销", example = "1")
        private Integer status;
    }


    @Data
    @ApiModel(value = "GiftReceiveListReqDto", description = "豪礼列表请求实体类")
    class GiftReceiveListReqDto {
        @ApiModelProperty(name = "uid", value = "用户ID", example = "25")
        private Integer uid;
        @ApiModelProperty(name = "username", value = "用户名", example = "老王")
        private String username;
        @ApiModelProperty(name = "category", value = "活动类型", example = "1")
        private Integer category;
        @ApiModelProperty(name = "status", value = "状态:0-申请中 1-同意 2-拒绝 3-已发货 4-已送达", example = "1")
        private Integer status;
        @ApiModelProperty(name = "startTime", value = "开始时间", example = "1587224407")
        private Integer startTime;
        @ApiModelProperty(name = "endTime", value = "结束时间", example = "1587224407")
        private Integer endTime;
    }

    @Data
    @ApiModel(value = "GiftReceiveListResDto", description = "豪礼响应实体类")
    class GiftReceiveListResDto {
        @ApiModelProperty(name = "id", value = "豪礼记录ID")
        private Long id;
        @ApiModelProperty(name = "uid", value = "用户ID")
        private Integer uid;
        @ApiModelProperty(name = "username", value = "用户名", example = "老王")
        private String username;
        @ApiModelProperty(name = "codeZh", value = "活动名称", example = "10")
        private String codeZh;
        @ApiModelProperty(name = "giftName", value = "礼物详情")
        private String giftName;
        @ApiModelProperty(name = "createdAt", value = "创建时间", example = "1588470650")
        private Integer createdAt;
        @ApiModelProperty(name = "status", value = "状态:0-申请中 1-同意 2-拒绝 3-已发货 4-已送达", example = "1")
        private Integer status;
    }

    @Data
    @ApiModel(value = "GiftReceiveUpdateReqDto", description = "修改豪礼请求实体类")
    class GiftReceiveUpdateReqDto {
        @NotNull(message = "ID不能为空！")
        @ApiModelProperty(name = "id", value = "记录ID")
        private Long id;
        @ApiModelProperty(name = "status", value = "状态:0-申请中 1-同意 2-拒绝 3-已发货 4-已送达", example = "1")
        private Integer status;
        @ApiModelProperty(name = "mark", value = "备注", example = "1")
        private String mark;
    }


    @Data
    @ApiModel(value = "OrderDetailReqDto", description = "豪礼详情请求实体类")
    class OrderDetailReqDto {
        @ApiModelProperty(name = "id", value = "记录ID")
        private Long id;
    }

    @Data
    @ApiModel(value = "OrderDetailDto", description = "豪礼详情实体类")
    class OrderDetailDto {
        @ApiModelProperty(name = "id", value = "记录Id")
        private Long id;
        @ApiModelProperty(name = "username", value = "用户名称")
        private String username;
        @ApiModelProperty(name = "consignee", value = "收货人姓名")
        private String consignee;
        @ApiModelProperty(name = "number", value = "手机号码")
        private String number;
        @ApiModelProperty(name = "postNo", value = "订单号")
        private String postNo;
        @ApiModelProperty(name = "addressDetail", value = "详细地址")
        private String addressDetail;
        @ApiModelProperty(name = "reason", value = "拒绝理由")
        private String reason;
    }

    @Data
    @ApiModel(value = "PromotionsByLangReqDto", description = "语音获取活动信息请求实体类")
    class PromotionsByLangReqDto {
        @ApiModelProperty(name = "id", value = "活动记录Id")
        private Integer id;
        @ApiModelProperty(name = "lang", value = "语音")
        private String lang;
    }

    @Data
    @Builder
    @ApiModel(value = "PromotionsByLangResDto", description = "语音获取活动信息响应实体类")
    class PromotionsByLangResDto {
        @ApiModelProperty(name = "langMsg", value = "语言描述")
        private String langMsg;
        @ApiModelProperty(name = "codeZh", value = "活动名称")
        private String codeZh;
        @ApiModelProperty(name = "img", value = "图片地址")
        private String img;
        @ApiModelProperty(name = "descript", value = "活动详情")
        private String descript;
    }

    @Data
    @Builder
    @ApiModel(value = "PlatLang", description = "平台语言请求实体类")
    class PlatLangResDto {
        @ApiModelProperty(name = "lang", value = "语言", example = "zh")
        private String lang;
        @ApiModelProperty(name = "langMsg", value = "语言描述", example = "中文")
        private String langMsg;
    }


    @Data
    @Builder
    @ApiModel(value = "RewardsListReqDto", description = "活动彩金列表请求实体类")
    class RewardsListReqDto {
        @ApiModelProperty(name = "username", value = "用户名称", example = "test001")
        private String username;
        @ApiModelProperty(name = "id", value = "记录ID", example = "1")
        private Integer id;
        @ApiModelProperty(name = "startTime", value = "开始时间", example = "1587224407")
        private Integer startTime;
        @ApiModelProperty(name = "endTime", value = "结束时间", example = "1587224407")
        private Integer endTime;
        @ApiModelProperty(name = "status", value = "状态:0-申请中 1-已满足 2-已结束", example = "1")
        private Integer status;
    }

    @Data
    @ApiModel(value = "RewardsListResDto", description = "活动彩金列表响应实体类")
    class RewardsListResDto {
        @ApiModelProperty(name = "id", value = "id", example = "1")
        private Long id;
        @ApiModelProperty(name = "uid", value = "uid", example = "test001")
        private Integer uid;
        @ApiModelProperty(name = "username", value = "用户名", example = "13523456754")
        private String username;
        @ApiModelProperty(name = "coin", value = "领取彩金金额", example = "test001")
        private BigDecimal coin;
        @ApiModelProperty(name = "coinBefore", value = "领取彩金前金额", example = "test001")
        private BigDecimal coinBefore;
        @ApiModelProperty(name = "flowClaim", value = "所需流水倍数", example = "1")
        private Integer flowClaim;
        @ApiModelProperty(name = "codes", value = "所需打码量", example = "100")
        private BigDecimal codes;
        @ApiModelProperty(name = "endedAt", value = "截止时间", example = "100")
        private Integer endedAt;
        @ApiModelProperty(name = "status", value = "状态:0-申请中 1-已满足 2-已结束", example = "1")
        private Integer status;
        @ApiModelProperty(name = "createdAt", value = "创建时间", example = "1587224407")
        private Integer createdAt;
        @ApiModelProperty(name = "updatedAt", value = "更新时间", example = "1587224407")
        private Integer updatedAt;
        @ApiModelProperty(name = "referCode", value = "活动编码", example = "First Deposit Bonus")
        private String referCode;
    }

    @Data
    @ApiModel(value = "RegisterRewardsUpdate", description = "注册彩金修改响应实体类")
    class RegisterRewardsUpdate {
        @ApiModelProperty(name = "id", value = "记录ID", example = "1")
        private Integer id;
        @ApiModelProperty(name = "status", value = "状态:0-申请中 1-已满足 2-已结束", example = "1")
        private Integer status;
    }


    @EqualsAndHashCode(callSuper = true)
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @SuperBuilder
    @ApiModel(value = "BlogListReqDto", description = "博客列表请求入参数")
    class BlogListReqDto extends BaseParams.TimeParams {
        @ApiModelProperty(name = "category", value = "组别名称", example = "1")
        private String category;
        @ApiModelProperty(name = "lang", value = "语言", example = "1")
        private String lang;
        @ApiModelProperty(name = "status", value = "状态:1-启用 0-停用", example = "1")
        private Integer status;
        @ApiModelProperty(name = "updateBy", value = "操作人姓名", example = "1")
        private String updateBy;
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @ApiModel(value = "BlogSaveOrUpdateReqDto", description = "博客更新或新增接口")
    class BlogSaveOrUpdateReqDto {
        @ApiModelProperty(name = "id", value = "id", example = "1")
        private Integer id;
        @ApiModelProperty(name = "lang", value = "语言", example = "1")
        private String lang;
        @ApiModelProperty(name = "category", value = "组别名称", example = "1")
        private String category;
        @ApiModelProperty(name = "image", value = "图片", example = "1")
        private String image;
        @ApiModelProperty(name = "title", value = "主标题", example = "1")
        private String title;
        @ApiModelProperty(name = "titleSub", value = "副标题", example = "1")
        private String titleSub;
        @ApiModelProperty(name = "content", value = "图片", example = "1")
        private String content;
        @ApiModelProperty(name = "recommend", value = "推荐:0-否 1-是", example = "1")
        private Integer recommend;
        @ApiModelProperty(name = "status", value = "状态:1-启用 0-停用", example = "1")
        private Integer status;
        @ApiModelProperty(name = "sort", value = "'排序'", example = "1")
        private Integer sort;
    }
}
