package com.lt.win.backend.io.bo.user;

import com.lt.win.utils.components.pagination.ResPage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: wells
 * @date: 2020/8/27
 * @description:
 */

public interface AgentCenterParameter {

    @Data
    @ApiModel(value = "AgentUserListReqBody", description = "代理中心会员列表请求实体类")
    class AgentUserListReqBody {
        @ApiModelProperty(name = "uid", value = "用户ID", example = "10")
        private Integer uid;
        @ApiModelProperty(name = "username", value = "用户名", example = "10")
        private String username;
        @ApiModelProperty(name = "agentUsername", value = "代理Id(与层级同时使用)", example = "11")
        private String agentUsername;
        @ApiModelProperty(name = "agentLayer", value = "代理层级(1,2,3,4,5,6)", example = "1")
        private Integer agentLayer;
        @ApiModelProperty(name = "levelId", value = "会员等级", example = "2")
        private Integer levelId;
        @ApiModelProperty(name = "status", value = "状态:10-正常 9-冻结 8-删除；字典key:dic_user_profile_status", example = "1")
        private Integer status;
        @ApiModelProperty(name = "startTime", value = "开始时间", example = "1595692801")
        private Integer startTime;
        @ApiModelProperty(name = "endTime", value = "结束时间", example = "1595692801")
        private Integer endTime;
    }


    @Data
    @ApiModel(value = "AgentUserListResBody", description = "代理中心会员列表响应实体类")
    class AgentUserListResBody {
        @ApiModelProperty(name = "uid", value = "用户ID", example = "10")
        private Integer uid;
        @ApiModelProperty(name = "userName", value = "用户名称", example = "老王")
        private String userName;
        @ApiModelProperty(name = "levelId", value = "会员等级", example = "2")
        private Integer levelId;
        @ApiModelProperty(name = "coin", value = "会员余额", example = "100.00")
        private BigDecimal coin;
        @ApiModelProperty(name = "teamCount", value = "团队人数", example = "")
        private List<Team> teamCount;
        @ApiModelProperty(name = "teamAmount", value = "团队金额", example = "")
        private List<TeamAmount> teamAmount;
        @ApiModelProperty(name = "supUid1", value = "上一级级代理UID", example = "1")
        private Integer supUid1;
        @ApiModelProperty(name = "supUid2", value = "上二级级代理UID", example = "2")
        private Integer supUid2;
        @ApiModelProperty(name = "supUid3", value = "上三级级代理UID", example = "3")
        private Integer supUid3;
        @ApiModelProperty(name = "supUid4", value = "上四级级代理UID", example = "4")
        private Integer supUid4;
        @ApiModelProperty(name = "supUid5", value = "上五级级代理UID", example = "5")
        private Integer supUid5;
        @ApiModelProperty(name = "supUid6", value = "上六级级代理UID", example = "6")
        private Integer supUid6;
        @ApiModelProperty(name = "supUid1Name", value = "上一级级代理名字", example = "test001")
        private String supUid1Name;
        @ApiModelProperty(name = "supUid2Name", value = "上二级级代理名字", example = "test002")
        private String supUid2Name;
        @ApiModelProperty(name = "supUid3Name", value = "上三级级代理名字", example = "test003")
        private String supUid3Name;
        @ApiModelProperty(name = "supUid4Name", value = "上四级级代理名字", example = "test004")
        private String supUid4Name;
        @ApiModelProperty(name = "supUid5Name", value = "上五级级代理名字", example = "test005")
        private String supUid5Name;
        @ApiModelProperty(name = "supUid6Name", value = "上六级级代理名字", example = "test006")
        private String supUid6Name;
        @ApiModelProperty(name = "status", value = "状态:10-正常 9-冻结 8-删除；字典key:dic_user_profile_status", example = "1")
        private Integer status;
        @ApiModelProperty(name = "createdAt", value = "创建时间", example = "1595692801")
        private Integer createdAt;
    }

    @Data
    @Builder
    @ApiModel(value = "Team", description = "团队人数")
    class Team {
        @ApiModelProperty(name = "agentLayer", value = "代理层", example = "下一级名称")
        private Integer agentLayer;
        @ApiModelProperty(name = "count", value = "人数", example = "15")
        private Integer count;
    }

    @Data
    @Builder
    @ApiModel(value = "TeamAmount", description = "团队金额")
    class TeamAmount {
        @ApiModelProperty(name = "agentLayer", value = "代理层", example = "下一级名称")
        private Integer agentLayer;
        @ApiModelProperty(name = "amount", value = "金额", example = "15.0")
        private BigDecimal amount;
    }


    @Data
    @ApiModel(value = "AgentCommissionReqBody", description = "代理中心会佣奖励请求实体类")
    class AgentCommissionReqBody {
        @ApiModelProperty(name = "agentId", value = "代理Id", example = "11")
        private Integer agentId;
        @ApiModelProperty(name = "username", value = "用户名", example = "老王")
        private String username;
        @ApiModelProperty(name = "status", value = "状态:0-未发放 1-已发放;字典key:dic_coin_commission_status", example = "1")
        private Integer status;
        @ApiModelProperty(name = "category", value = "类型:0-流水佣金 1-活跃会员佣金 2-满额人头彩金;字典key:dic_coin_commission_category", example = "1")
        private Integer category;
        @ApiModelProperty(name = "startTime", value = "开始时间", example = "1595692801")
        private Integer startTime;
        @ApiModelProperty(name = "endTime", value = "结束时间", example = "1595692801")
        private Integer endTime;
    }

    @Data
    @Builder
    @ApiModel(value = "AgentCommissionResBody", description = "代理中心会佣奖励响应实体类")
    class AgentCommissionResBody {
        @ApiModelProperty(name = "totalCoin", value = "累计金额", example = "100.00")
        private BigDecimal totalCoin;
        @ApiModelProperty(name = "totalBetCoin", value = "总投注额", example = "200.00")
        private BigDecimal totalBetCoin;
        @ApiModelProperty(name = "agentCommissionResPage", value = "代理中心会佣奖励列表", example = "")
        private ResPage<AgentCommissionList> agentCommissionResPage;
    }


    @Data
    @ApiModel(value = "AgentCommissionList", description = "代理中心会佣奖励列表")
    class AgentCommissionList {
        @ApiModelProperty(name = "id", value = "id", example = "88732945036218368")
        private Long id;
        @ApiModelProperty(name = "uid", value = "代理ID", example = "10")
        private Integer uid;
        @ApiModelProperty(name = "username", value = "用户名", example = "老王")
        private String username;
        @ApiModelProperty(name = "riqi", value = "佣金时间", example = "200101")
        private Integer riqi;
        @ApiModelProperty(name = "coin", value = "佣金金额", example = "100.00")
        private BigDecimal coin;
        @ApiModelProperty(name = "subUid", value = "下级UID", example = "11")
        private String subUid;
        @ApiModelProperty(name = "subUsername", value = "下级Username", example = "test999")
        private String subUsername;
        @ApiModelProperty(name = "agentLevel", value = "代理等级", example = "test999")
        private Integer agentLevel;
        @ApiModelProperty(name = "subBetTrunover", value = "投注总额/会员数", example = "100.11/3")
        private String subBetTrunover;
        @ApiModelProperty(name = "status", value = "发放状态:0-未发放 1-已发放；字典key:dic_coin_commission_status", example = "1")
        private Integer status;
        @ApiModelProperty(name = "createdAt", value = "发放时间", example = "1595692801")
        private Integer createdAt;
    }

    @Data
    @ApiModel(value = "AgentCommissionDetailsReqBody", description = "代理中心用户详情请求实体类")
    class AgentCommissionDetailsReqBody {
        @ApiModelProperty(name = "id", value = "ID", example = "88732944730034176")
        private Long id;
    }

    @Data
    @ApiModel(value = "AgentCommissionDetailsResDtoBody", description = "代理中心用户详情响应实体类")
    class AgentCommissionDetailsResDtoBody {
        @ApiModelProperty(name = "uid", value = "用户id", example = "10")
        private Integer uid;
        @ApiModelProperty(name = "username", value = "用户名称", example = "老王")
        private String username;
        @ApiModelProperty(name = "createdAt", value = "创建时间", example = "1601485200")
        private Integer createdAt;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @ApiModel(value = "SmsCodeResDto", description = "验证码列表响应")
    class SmsCodeDto {

        @ApiModelProperty(name = "areaCode", value = "用户名称", example = "老王")
        private String areaCode;
        @ApiModelProperty(name = "mobile", value = "手机", example = "1601485200")
        private String mobile;
        @ApiModelProperty(name = "code", value = "验证码", example = "10")
        private Integer code;
        @ApiModelProperty(name = "createAt", value = "创建时间", example = "1601485200")
        private Long createAt;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    class SmsCodeReqDto {
        @ApiModelProperty(name = "email", value = "邮箱地址")
        private String email;
    }
}
