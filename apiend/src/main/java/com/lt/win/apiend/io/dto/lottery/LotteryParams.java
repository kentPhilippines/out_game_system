package com.lt.win.apiend.io.dto.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Auther: Jess
 * @Date: 2023/9/14 20:39
 * @Description:
 */
public interface LotteryParams {
    @Data
    @ApiModel(value = "LotteryInfoRep", description = "查询期数信息响应实体")
    class LotteryInfoRep {

    }

    @Data
    @ApiModel(value = "LotteryResultRep", description = "查询开奖结果响应实体")
    class LotteryResultRep {

    }

    @Data
    @ApiModel(value = "BetRes", description = "投注请求实体")
    class BetRes {

    }
    @Data
    @ApiModel(value = "BetRep", description = "投注响应实体")
    class BetRep {

    }
}
