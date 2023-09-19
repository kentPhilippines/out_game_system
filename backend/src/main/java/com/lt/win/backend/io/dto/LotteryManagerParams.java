package com.lt.win.backend.io.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @Auther: Jess
 * @Date: 2023/9/19 11:04
 * @Description:
 */
public interface LotteryManagerParams {

    @Data
    @ApiModel(value = "LotteryTypePageReq", description = "彩种列表请求参数")
    class LotteryTypePageReq {

    }


    @Data
    @ApiModel(value = "LotteryTypePageRes", description = "彩种列表响应参数")
    class LotteryTypePageRes {

    }

}
