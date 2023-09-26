package com.lt.win.apiend.io.dto.lottery;

import com.lt.win.apiend.io.dto.StartEndTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.joda.time.base.BaseDateTime;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Auther: Jess
 * @Date: 2023/9/14 20:39
 * @Description:
 */
public interface LotteryParams {
    @Data
    @ApiModel(value = "LotteryInfoReq", description = "查询期数信息请求实体")
    class LotteryInfoReq {
        @ApiModelProperty(name = "mainCode", value = "主板编号", example = "1")
        public Integer mainCode = 1;
    }

    @Data
    @ApiModel(value = "LotteryInfoRes", description = "查询期数信息响应实体")
    class LotteryInfoRes {
        @ApiModelProperty(name = "mainCode", value = "主板编号", example = "1")
        public Integer mainCode;
        @ApiModelProperty(name = "mainName", value = "主板名称", example = "攻坚科技")
        public String mainName;
        @ApiModelProperty(name = "periodsNo", value = "期号", example = "20230915001")
        public String periodsNo;
        @ApiModelProperty(name = "restTime", value = "倒计时", example = "100")
        public Integer restTime;
        @ApiModelProperty(name = "odds", value = "赔率", example = "1.98")
        public BigDecimal odds;
        @ApiModelProperty(name = "plateDtoList", value = "板块集合", example = "")
        public List<PlateDto> plateDtoList;
    }

    @Data
    @ApiModel(value = "LotteryResultReq", description = "查询开奖结果请求实体")
    class LotteryResultReq {
        @ApiModelProperty(name = "mainCode", value = "主板编号", example = "1")
        public Integer mainCode = 1;
    }

    @Data
    @ApiModel(value = "LotteryResultRes", description = "查询开奖结果响应实体")
    class LotteryResultRes {
        @ApiModelProperty(name = "num", value = "第几期", example = "50")
        public Integer num;
        @ApiModelProperty(name = "plateName", value = "板块集合", example = "")
        public String plateName;
    }

    @Data
    @ApiModel(value = "BetRes", description = "投注请求实体")
    class BetRes {
        @ApiModelProperty(name = "mainCode", value = "主板编号", example = "1")
        public Integer mainCode;
        @ApiModelProperty(name = "betList", value = "投注实体", example = "")
        public List<BetDto> betList;
    }

    @Data
    @ApiModel(value = "BetDto", description = "投注实体")
    class BetDto {
        @ApiModelProperty(name = "plateCode", value = "板块编码", example = "1")
        public Integer plateCode;
        @ApiModelProperty(name = "coin", value = "投注金额", example = "10")
        public BigDecimal coin;
    }


    @Data
    @ApiModel(value = "BetRep", description = "投注响应实体")
    class BetRep {
        @ApiModelProperty(name = "status", value = "投注状态", example = "1：成功；0：失败")
        public Integer status;
    }

    @Data
    @ApiModel(value = "PlateDto", description = "板块实体")
    class PlateDto {
        @ApiModelProperty(name = "plateCode", value = "板块编码", example = "1")
        public Integer plateCode;
        @ApiModelProperty(name = "plateName", value = "板块名称", example = "华为概念")
        public String plateName;
        @ApiModelProperty(name = "payoutCount", value = "开奖次数", example = "100")
        public Integer payoutCount = 0;
    }


    @Data
    @ApiModel(value = "BetRecordReq", description = "查询注单请求实体")
    class BetRecordReq extends StartEndTime {
        @ApiModelProperty(name = "periodsNo", value = "期号", example = "20230915001")
        public String periodsNo;
        @ApiModelProperty(name = "betCode", value = "投注板块编号", example = "1")
        public Integer betCode;
        @ApiModelProperty(name = "payoutCode", value = "派彩板块编号", example = "1")
        public Integer payoutCode;
        @ApiModelProperty(name = "mainCode", value = "主板编号", example = "1")
        public Integer mainCode;
    }

    @Data
    @ApiModel(value = "BetRecordRes", description = "查询注单响应实体")
    class BetRecordRes {
        @ApiModelProperty(name = "periodsNo", value = "期号", example = "20230915001")
        public String periodsNo;
        @ApiModelProperty(name = "odds", value = "赔率", example = "1.98")
        public BigDecimal odds;
        @ApiModelProperty(name = "coinBet", value = "投注金额", example = "10")
        public BigDecimal coinBet;
        @ApiModelProperty(name = "coinPayout", value = "派彩金额", example = "10")
        public BigDecimal coinPayout;
        @ApiModelProperty(name = "status", value = "注单状态 0:待开彩  1派彩成功  2: 退款", example = "1")
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

}
