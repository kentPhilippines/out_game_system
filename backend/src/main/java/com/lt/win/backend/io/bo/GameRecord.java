package com.lt.win.backend.io.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 游戏记录
 *
 * @author suki
 */
public interface GameRecord {

    @Data
    class GameListReqBody {

        @NotNull(message = "游戏组id")
        @ApiModelProperty(name = "groupId", value = "游戏组id", example = "1")
        private Integer groupId;
    }

    @Data
    class GameDetailsReqBody {

        @NotNull(message = "订单id")
        @ApiModelProperty(name = "orderId", value = "订单id", example = "2")
        private String orderId;
    }

}
