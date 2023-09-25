package com.lt.win.backend.io.dto;

import com.lt.win.backend.io.bo.StartEndTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Auther: Jess
 * @Date: 2023/9/19 11:04
 * @Description:
 */
public interface LotteryManagerParams {

    @Data
    @ApiModel(value = "LotteryTypePageReq", description = "彩种列表请求参数")
    class LotteryTypePageReq {
        @ApiModelProperty(value = "彩种名称", example = "pk10")
        private String name;
    }


    @Data
    @ApiModel(value = "LotteryTypePageRes", description = "彩种列表响应参数")
    class LotteryTypePageRes {
        @ApiModelProperty(value = "ID", example = "1")
        private Integer id;
        @ApiModelProperty(value = "彩种编码", example = "pk10")
        private String code;
        @ApiModelProperty(value = "彩种名称", example = "pk10")
        private String name;
        @ApiModelProperty(value = "赔率", example = "1.98")
        private BigDecimal odds;
        @ApiModelProperty(value = "封盘时间（秒）", example = "10")
        private Integer pauseAt;
        @ApiModelProperty(value = "创建时间", example = "1694750201")
        private Integer createdAt;
    }

    @Data
    @ApiModel(value = "AddLotteryTypeReq", description = "新增彩种请求参数")
    class AddLotteryTypeReq {
        @ApiModelProperty(value = "彩种编码", example = "pk10")
        private String code;
        @ApiModelProperty(value = "彩种名称", example = "pk10")
        private String name;
        @ApiModelProperty(value = "赔率", example = "1.98")
        private BigDecimal odds;
        @ApiModelProperty(value = "封盘时间（秒）", example = "10")
        private Integer pauseAt;
    }

    @Data
    @ApiModel(value = "UpdateLotteryTypeReq", description = "修改彩种请求参数")
    class UpdateLotteryTypeReq {
        @ApiModelProperty(value = "ID", example = "1")
        private Integer id;
        @ApiModelProperty(value = "彩种编码", example = "pk10")
        private String code;
        @ApiModelProperty(value = "彩种名称", example = "pk10")
        private String name;
        @ApiModelProperty(value = "赔率", example = "1.98")
        private BigDecimal odds;
        @ApiModelProperty(value = "封盘时间（秒）", example = "10")
        private Integer pauseAt;
    }

    @Data
    @ApiModel(value = "DeleteLotteryTypeReq", description = "删除彩种请求参数")
    class DeleteLotteryTypeReq {
        @ApiModelProperty(value = "ID", example = "1")
        private Integer id;
    }

    @Data
    @ApiModel(value = "MainPlatePageReq", description = "主板列表请求参数")
    class MainPlatePageReq {
        @ApiModelProperty(value = "主板名称", example = "pk10")
        private String name;
    }

    @Data
    @ApiModel(value = "MainPlatePageRes", description = "主板列表响应参数")
    class MainPlatePageRes {
        @ApiModelProperty(value = "ID", example = "1")
        private Integer id;
        @ApiModelProperty(value = "板块编码", example = "1")
        private Integer code;
        @ApiModelProperty(value = "板块名称", example = "攻坚科技")
        private String name;
        @ApiModelProperty(value = "创建时间", example = "1694750201")
        private Integer createdAt;
    }

    @Data
    @ApiModel(value = "AddMainPlateReq", description = " 新增主板请求参数")
    class AddMainPlateReq {
        @ApiModelProperty(value = "板块编码", example = "1")
        private Integer code;
        @ApiModelProperty(value = "板块名称", example = "攻坚科技")
        private String name;
    }

    @Data
    @ApiModel(value = "UpdateMainPlateReq", description = "修改主板请求参数")
    class UpdateMainPlateReq {
        @ApiModelProperty(value = "ID", example = "1")
        private Integer id;
        @ApiModelProperty(value = "板块编码", example = "1")
        private Integer code;
        @ApiModelProperty(value = "板块名称", example = "攻坚科技")
        private String name;
    }

    @Data
    @ApiModel(value = "DeleteMainPlateReq", description = "删除主板请求参数")
    class DeleteMainPlateReq {
        @ApiModelProperty(value = "ID", example = "1")
        private Integer id;
    }


    @Data
    @ApiModel(value = "PlatePageReq", description = "板块列表请求参数")
    class PlatePageReq {
        @ApiModelProperty(value = "主板编码", example = "1")
        private Integer mainCode;
        @ApiModelProperty(value = "板块名称", example = "北京钛方")
        private String name;
    }

    @Data
    @ApiModel(value = "PlatePageRes", description = "板块列表响应参数")
    class PlatePageRes {
        @ApiModelProperty(value = "ID", example = "1")
        private Integer id;
        @ApiModelProperty(value = "主板编码", example = "1")
        private Integer mainCode;
        @ApiModelProperty(value = "主板名称", example = "攻坚科技")
        private String mainName;
        @ApiModelProperty(value = "板块编码", example = "1")
        private Integer code;
        @ApiModelProperty(value = "板块名称", example = "攻坚科技")
        private String name;
        @ApiModelProperty(value = "开奖次数", example = "1000")
        private Integer payoutCount;
        @ApiModelProperty(value = "创建时间", example = "1694750201")
        private Integer createdAt;
    }

    @Data
    @ApiModel(value = "AddPlateReq", description = " 新增板块请求参数")
    class AddPlateReq {
        @ApiModelProperty(value = "主板编码", example = "1")
        private Integer mainCode;
        @ApiModelProperty(value = "板块编码", example = "1")
        private Integer code;
        @ApiModelProperty(value = "板块名称", example = "攻坚科技")
        private String name;
        @ApiModelProperty(value = "开奖次数", example = "1000")
        private Integer payoutCount;
    }

    @Data
    @ApiModel(value = "UpdatePlateReq", description = "修改板块请求参数")
    class UpdatePlateReq {
        @ApiModelProperty(value = "ID", example = "1")
        private Integer id;
        @ApiModelProperty(value = "主板编码", example = "1")
        private Integer mainCode;
        @ApiModelProperty(value = "板块编码", example = "1")
        private Integer code;
        @ApiModelProperty(value = "板块名称", example = "攻坚科技")
        private String name;
        @ApiModelProperty(value = "开奖次数", example = "1000")
        private Integer payoutCount;
    }

    @Data
    @ApiModel(value = "DeletePlateReq", description = "删除板块请求参数")
    class DeletePlateReq {
        @ApiModelProperty(value = "ID", example = "1")
        private Integer id;
    }

    @Data
    @ApiModel(value = "OpenPageReq", description = "开奖结果列表请求参数")
    class OpenPageReq {
        @ApiModelProperty(name = "mainCode", value = "主板编号", example = "1")
        public Integer mainCode;
        @ApiModelProperty(name = "periodsNo", value = "板块名称", example = "20230915001")
        private String periodsNo;
        @ApiModelProperty(name = "openCode", value = "板块编号", example = "1")
        public String openCode;
    }

    @Data
    @ApiModel(value = "OpenPageRes", description = "开奖结果列表响应参数")
    class OpenPageRes {
        @ApiModelProperty(value = "ID", example = "1")
        private Integer id;
        @ApiModelProperty(name = "periodsNo", value = "板块名称", example = "20230915001")
        private String periodsNo;
        @ApiModelProperty(name = "lotteryCode", value = "彩种编号", example = "北京钛方")
        private String lotteryCode;
        @ApiModelProperty(name = "lotteryName", value = "彩种编号", example = "北京钛方")
        private String lotteryName;
        @ApiModelProperty(name = "openCode", value = "板块编号", example = "1")
        public String openCode;
        @ApiModelProperty(name = "openName", value = "板块名称", example = "1")
        public String openName;
        @ApiModelProperty(name = "mainCode", value = "主板编号", example = "1")
        public Integer mainCode;
        @ApiModelProperty(name = "mainName", value = "主板名称", example = "攻坚科技")
        public String mainName;
        @ApiModelProperty(name = "openAllCode", value = "开奖全部板块编号", example = "1,3,5,8,7,10,4,2,9,6")
        public String openAllCode;
        @ApiModelProperty(name = "openAllName", value = "开奖全部板块名称", example = "--")
        public String openAllName;
        @ApiModelProperty(name = "status", value = "开奖状态；0：未开奖 1:已开奖 字典：dic_lottery_open_status", example = "1")
        public Integer status;
        @ApiModelProperty(name = "updatedUser", value = "修改人", example = "admin")
        public String updatedUser = "";
        @ApiModelProperty(name = "createdAt", value = "创建时间", example = "1695024134")
        public Integer createdAt;
        @ApiModelProperty(name = "updatedAt", value = "修改时间", example = "1695024134")
        public Integer updatedAt;
    }

    @Data
    @ApiModel(value = "AddOpenReq", description = "新增开奖结果请求参数")
    class AddOpenReq {
        @ApiModelProperty(name = "periodsNo", value = "板块名称", example = "20230915001")
        private String periodsNo;
        @ApiModelProperty(name = "openCode", value = "板块编号", example = "1")
        public String openCode;
        @ApiModelProperty(name = "mainCode", value = "主板编号", example = "1")
        public Integer mainCode;
    }

    @Data
    @ApiModel(value = "UpdateOpenReq", description = "修改开奖结果请求参数")
    class UpdateOpenReq {
        @ApiModelProperty(value = "ID", example = "1")
        private Integer id;
        @ApiModelProperty(name = "openCode", value = "板块编号", example = "1")
        public String openCode;
    }

    @Data
    @ApiModel(value = "DeleteOpenReq", description = "删除开奖结果请求参数")
    class DeleteOpenReq {
        @ApiModelProperty(value = "ID", example = "1")
        private Integer id;
    }

    @Data
    @ApiModel(value = "BetRecordReq", description = "查询注单请求实体")
    class BetRecordReq extends StartEndTime {
        @ApiModelProperty(name = "periodsNo", value = "期号", example = "20230915001")
        public String periodsNo;
        @ApiModelProperty(name = "username", value = "用户名", example = "wells01")
        public String username;
        @ApiModelProperty(name = "betCode", value = "投注板块编号", example = "1")
        public Integer betCode;
        @ApiModelProperty(name = "payoutCode", value = "派彩板块编号", example = "1")
        public Integer payoutCode;
        @ApiModelProperty(name = "mainCode", value = "主板编号", example = "1")
        public Integer mainCode;
        @ApiModelProperty(name = "status", value = "注单状态 0:未结算  1:已结算 字典：dic_lottery_betslips_status", example = "1")
        public Integer status;
    }

    @Data
    @ApiModel(value = "BetRecordRes", description = "查询注单响应实体")
    class BetRecordRes {
        @ApiModelProperty(name = "id", value = "ID", example = "491017160991313920")
        public Long id;
        @ApiModelProperty(name = "periodsNo", value = "期号", example = "20230915001")
        public String periodsNo;
        @ApiModelProperty(name = "username", value = "用户名", example = "wells01")
        public String username;
        @ApiModelProperty(name = "odds", value = "赔率", example = "1.98")
        public BigDecimal odds;
        @ApiModelProperty(name = "coinBet", value = "投注金额", example = "10")
        public BigDecimal coinBet;
        @ApiModelProperty(name = "coinPayout", value = "派彩金额", example = "10")
        public BigDecimal coinPayout;
        @ApiModelProperty(name = "status", value = "注单状态 0:未结算  1:已结算 字典：dic_lottery_betslips_status", example = "1")
        public Integer status;
        @ApiModelProperty(name = "betName", value = "北京钛方", example = "1")
        public String betName;
        @ApiModelProperty(name = "payoutName", value = "北京钛方", example = "1")
        public String payoutName = "";
        @ApiModelProperty(name = "mainName", value = "攻坚科技", example = "1")
        public String mainName;
        @ApiModelProperty(name = "createdAt", value = "创建时间", example = "1694750201")
        public Integer createdAt;
    }

    @Data
    @ApiModel(value = "MainPlateRes", description = "查询主板响应实体")
    class MainPlateRes {
        @ApiModelProperty(name = "mainCode", value = "主板编号", example = "1")
        public Integer mainCode;
        @ApiModelProperty(name = "mainName", value = "主板名称", example = "攻坚科技")
        public String mainName;
    }

    @Data
    @ApiModel(value = "PlateReq", description = "查询板块请求实体")
    class PlateReq {
        @ApiModelProperty(name = "mainCode", value = "主板编号", example = "1")
        public Integer mainCode;
    }

    @Data
    @ApiModel(value = "PlateRes", description = "查询板块响应实体")
    class PlateRes {
        @ApiModelProperty(name = "plateCode", value = "板块编号", example = "1")
        public Integer plateCode;
        @ApiModelProperty(name = "plateName", value = "板块名称", example = "北京钛方")
        public String plateName;
    }


    @Data
    @ApiModel(value = "PlateBetStatisticsReq", description = "板块统计请求实体")
    class PlateBetStatisticsReq {
        @ApiModelProperty(name = "periodsNo", value = "期号", example = "20230915001")
        public String periodsNo;
        @ApiModelProperty(name = "mainCode", value = "主板编号", example = "1")
        public Integer mainCode;
    }

    @Data
    @ApiModel(value = "PlateBetStatisticsRes", description = "板块统计响应实体")
    class PlateBetStatisticsRes {
        @ApiModelProperty(name = "restTime", value = "倒计时（秒）", example = "10")
        public Integer restTime;
        @ApiModelProperty(name = "list", value = "板块统计列表", example = "--")
        public List<PlateBetStatisticsDto> list;
    }

    @Data
    @ApiModel(value = "PlateBetStatisticsDto", description = "板块统计实体")
    class PlateBetStatisticsDto {
        @ApiModelProperty(name = "plateName", value = "板块名称", example = "北京钛方")
        public String plateName;
        @ApiModelProperty(name = "betTotal", value = "投注总额", example = "100")
        public BigDecimal betTotal;
        @ApiModelProperty(name = "winTotal", value = "盈利总额", example = "100")
        public BigDecimal winTotal;
    }

    @Data
    @ApiModel(value = "OpenSettleReq", description = "开奖结果-结算请求实体")
    class OpenSettleReq {
        @ApiModelProperty(name = "periodsNo", value = "期号", example = "20230915001")
        public String periodsNo;
        @ApiModelProperty(name = "mainCode", value = "主板编号", example = "1")
        public Integer mainCode;
    }

    @Data
    @ApiModel(value = "BetSettleReq", description = "注单记录-结算请求实体")
    class BetSettleReq {
        @ApiModelProperty(name = "id", value = "ID", example = "491017160991313920")
        public Long id;
    }
}

