package com.lt.win.apiend.io.dto.platform;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 三方游戏平台->批量下分
 * </p>
 *
 * @author andy
 * @since 2020/12/14
 */
public interface CoinDownBatch {

    /**
     * 三方游戏平台->批量下分 ReqBody
     */
    @Data
    @Builder
    class CoinDownBatchReqBody {
        @ApiModelProperty(name = "id", value = "平台ID", example = "17")
        private Integer id;
        @ApiModelProperty(name = "coin", value = "平台余额", example = "20.00")
        private BigDecimal coin;
    }

    /**
     * 三方游戏平台->批量下分 ResBody
     */
    @Data
    @Builder
    class CoinDownBatchResBody {
        @ApiModelProperty(name = "coin", value = "账户余额", example = "100.00")
        private BigDecimal coin;

        @ApiModelProperty(name = "list", value = "平台余额列表")
        private List<CoinDownBatchPlatInfo> list;

    }

    @Data
    @Builder
    class CoinDownBatchPlatInfo {
        @ApiModelProperty(name = "id", value = "平台ID", example = "17")
        private Integer id;
        @ApiModelProperty(name = "coin", value = "平台余额", example = "20.00")
        private BigDecimal coin;


        @ApiModelProperty(name = "code", value = "返回码", example = "0")
        private Integer code;
        @ApiModelProperty(name = "msg", value = "返回异常信息", example = "0")
        private String msg;
    }
}
