package com.lt.win.backend.io.bo;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 游戏管理
 * </p>
 *
 * @author andy
 * @since 2020/6/8
 */
public interface PlatManager {

    /**
     * 平台管理-列表 reqBody
     */
    @Data
    class ListPlatReqBody extends StartEndTime {
        @ApiModelProperty(name = "code", value = "平台编码", example = "DG")
        private String code;

        @ApiModelProperty(name = "name", value = "平台名称", example = "DG视讯")
        private String name;

        @ApiModelProperty(name = "status", value = "状态:1-启用 2-停用", example = "1")
        private Integer status;
    }

    /**
     * 平台管理-列表 resBody
     */
    @Data
    class ListPlatResBody {

        @ApiModelProperty(name = "id", value = "主键ID", example = "1")
        private Integer id;

        @ApiModelProperty(name = "code", value = "平台编码", example = "DG")
        private String code;

        @ApiModelProperty(name = "name", value = "游戏名称", example = "DG")
        private String platName;

        @ApiModelProperty(name = "name", value = "游戏名称", example = "DG")
        private String name;

        @ApiModelProperty(name = "revenueRate", value = "税收比例", example = "DG")
        private String revenueRate;

        @ApiModelProperty(name = "sort", value = "排序", example = "9")
        private Integer sort;

        @ApiModelProperty(name = "remark", value = "备注", example = "9")
        private String remark;

        @ApiModelProperty(name = "updatedAt", value = "时间", example = "1590735421")
        private Integer updatedAt;

        @ApiModelProperty(name = "status", value = "状态:1-启用 0-停用 2-维护", example = "1")
        private Integer status;

        @ApiModelProperty(name = "maintenance", value = "配置:维护信息:info-信息 start-开始时间 end-结束时间", example = "{\"info\":\"\",\"start\":\"1594118396\",\"end\":\"1594118396\"}")
        private JSONObject maintenance;


    }

    /**
     * 平台管理-编辑或启用平台 reqBody
     */
    @Data
    class UpdatePlatReqBody {
        @NotNull(message = "id不能为空")
        @ApiModelProperty(name = "id", value = "主键ID", required = true, example = "303")
        private Integer id;

        @ApiModelProperty(name = "name", value = "游戏名称", example = "DG")
        private String name;

        @ApiModelProperty(name = "code", value = "平台编码", example = "DG")
        private String code;

        @ApiModelProperty(name = "sort", value = "排序", example = "99")
        private Integer sort;

        @ApiModelProperty(name = "status", value = "状态:1-启用 0-停用 2-维护", example = "1")
        private Integer status;

        @ApiModelProperty(name = "maintenance", value = "配置:json格式", example = "{\"AGENT_NAME\":\"DGTE0101XT\",\"API_KEY\":\"a17e7d2af78743989fec09741c22f0c5\",\"API_URL\":\"http://api.dg99web.com\",\"ENVIRONEMENT\":\"TEST\"}")
        private String maintenance;
    }


    /**
     * 平台子游戏管理-列表 reqBody
     */
    @EqualsAndHashCode(callSuper = true)
    @Data
    class ListSubGameReqBody extends StartEndTime {
        @ApiModelProperty(name = "gameId", value = "游戏ID", example = "HB电子")
        private Integer gameId;

        @ApiModelProperty(name = "name", value = "游戏名称", example = "伦敦猎人")
        private String name;

        @ApiModelProperty(name = "favoriteStar", value = "收藏数量", example = "1")
        private Integer favoriteStar;

        @ApiModelProperty(name = "hotStar", value = "热度值", example = "1")
        private Integer hotStar;

        @ApiModelProperty(name = "isNew", value = "最新游戏", example = "1")
        private Integer isNew;

        @ApiModelProperty(name = "device", value = "显示", example = "设备:0/不填-all 1-pc 2-h5")
        private Integer device;

        @ApiModelProperty(name = "status", value = "状态:0/不填-全部 1-启用 2-停用", example = "1")
        private Integer status;
    }

    /**
     * 平台子游戏管理-列表 resBody
     */
    @Data
    class ListSubGameResBody {
        @ApiModelProperty(name = "id", value = "主键ID", example = "1")
        private String id;
        @ApiModelProperty(name = "gamePlat", value = "游戏平台", example = "HB电子")
        private String gamePlat;
        @ApiModelProperty(name = "plat", value = "游戏名称", example = "HB电子")
        private String name;
        @ApiModelProperty(name = "isNew", value = "是否最新", example = "是否最新:1-是 0-否")
        private Integer isNew;
        @ApiModelProperty(name = "favoriteStar", value = "收藏值", example = "999")
        private Integer favoriteStar;
        @ApiModelProperty(name = "hotStar", value = "热度", example = "999")
        private Integer hotStar;
        @ApiModelProperty(name = "device", value = "设备", example = "设备:0-all 1-pc 2-h5")
        private Integer device;
        @ApiModelProperty(name = "sort", value = "排序", example = "9")
        private Integer sort;
        @ApiModelProperty(name = "status", value = "状态: 1-启用 2-停用", example = "1")
        private Integer status;
        @ApiModelProperty(name = "updatedAt", value = "时间", example = "1590735421")
        private Integer updatedAt;
        @ApiModelProperty(name = "img", value = "游戏封面图", example = "http://www.google.com/dd.png")
        private String img;
        @ApiModelProperty(name = "platId", value = "平台id", example = "1")
        private Integer platId;
        @ApiModelProperty(name = "platName", value = "平台名称", example = "ddd")
        private String platName;
        @ApiModelProperty(name = "isCasino", value = "是否isCasino 1是 0否", example = "0")
        private Integer isCasino;
    }

    /**
     * 平台子游戏管理-编辑或启用平台 reqBody
     */
    @Data
    class UpdateSubGameReqBody {
        @NotNull(message = "id不能为空")
        @ApiModelProperty(name = "id", value = "主键ID", required = true, example = "0021831e-bb91-40dc-8776-5dafc936e8cd")
        private String id;
        @NotNull(message = "platId不能为空")
        @ApiModelProperty(name = "platId", value = "平台id", required = true, example = "25")
        private Integer platId;

        @ApiModelProperty(name = "isNew", value = "收否最新:1-是 0-否", example = "1")
        private Integer isNew;

        @ApiModelProperty(name = "img", value = "游戏封面图", example = "https://www.baidu.com/img.png")
        private String img;

        @ApiModelProperty(name = "是否推荐主页", value = "0否 1是", example = "1")
        private Integer isCasino;

        @ApiModelProperty(name = "favoriteStar", value = "收藏值", example = "1")
        private Integer favoriteStar;

        @ApiModelProperty(name = "hotStar", value = "热度", example = "1")
        private Integer hotStar;

        @ApiModelProperty(name = "device", value = "显示类型:0-ALL 1-PC 2-MOBILE", example = "1")
        private Integer device;

        @ApiModelProperty(name = "sort", value = "排序", example = "99")
        private Integer sort;

        @ApiModelProperty(name = "status", value = "状态: 1-启用 2-停用", example = "2")
        private Integer status;
    }

    /**
     * 游戏列表 resBody
     */
    @Data
    class GameListResBody {

        @ApiModelProperty(name = "id", value = "游戏ID", example = "701")
        private Integer id;

        @ApiModelProperty(name = "name", value = "游戏名称", example = "BWG体育")
        private String name;
    }

    @Data
    @ApiModel(value = "UpdateSportCompetitionRedDto", description = "修改热门赛事配置实例")
    class UpdateSportCompetitionRedDto {
        @NotNull(message = "meId不能为空")
        @ApiModelProperty(name = "meid", value = "赛事id")
        private Integer meid;
        @ApiModelProperty(name = "gameName", value = "游戏名称")
        private String gameName;
        @ApiModelProperty(name = "homeTeamName", value = "主队名称")
        private String homeTeamName;
        @ApiModelProperty(name = "visitTeamName", value = "主队名称")
        private String visitTeamName;
        @ApiModelProperty(name = "homeTeamName", value = "主队名称")
        private String homeTeamIcon;
        @ApiModelProperty(name = "visitTeamName", value = "主队名称")
        private String visitTeamIcon;
        @ApiModelProperty(name = "timestamp", value = "开赛时间")
        private Integer timestamp;
        @ApiModelProperty(name = "sort", value = "优先级排序")
        private Integer sort;
        @ApiModelProperty(name = "status", value = "状态 0-禁用，1-启用")
        private Integer status;
    }

    @Data
    @ApiModel(value = "DeleteSportCompetitionRedDto", description = "删除热门赛事配置实例")
    class DeleteSportCompetitionRedDto {
        @NotNull(message = "meId不能为空")
        @ApiModelProperty(name = "meid", value = "赛事id")
        private Integer meid;
    }

    @Data
    @ApiModel(value = "TeamLogoDto", description = "队徽列表实例")
    class TeamLogoDto {
        @ApiModelProperty(name = "id", value = "ID", example = "1")
        private Integer id;
        @ApiModelProperty(name = "teamLogoName", value = "队徽名称", example = "巴萨")
        private String teamLogoName;
        @ApiModelProperty(name = "teamLogoUrl", value = "队徽url", example = "group1/M01/00/08/wKh2AV_BCQaAPymiAAJBSTnVxcc085.jpg")
        private String teamLogoUrl;
    }

    @Data
    @ApiModel(value = "TeamLogoReqDto", description = "队徽列表请求实例")
    class TeamLogoReqDto {
        @ApiModelProperty(name = "teamLogoName", value = "队徽名称", example = "巴萨")
        private String teamLogoName;
    }

    @Data
    @ApiModel(value = "DeleteTeamLogoDto", description = "删除队徽实例")
    class DeleteTeamLogoDto {
        @NotNull(message = "ids不能为空")
        @ApiModelProperty(name = "ids", value = "队徽ID", example = "1,2")
        private String ids;
        @ApiModelProperty(name = "teamLogoUrls", value = "多个队徽url", example = "group1/M01/00/08/wKh2AV_BCQaAPymiAAJBSTnVxcc085.jpg,group1/M01/00/08/wKh2AV_BCQaAPymiAAJBSTnVxcc085.jpg")
        private String teamLogoUrls;
    }
}
