package com.lt.win.service.io.bo;

import com.lt.win.service.io.constant.ConstData;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 数据库查询字段映射类
 *
 * @author david
 * @since 2022/11/11
 */
public interface QueryBo {
    /**
     * 数据库查询字段映射类
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    class QueryDto {
        @ApiModelProperty(name = "count", value = "笔数", example = "100")
        private Integer count = ConstData.ZERO;
        @ApiModelProperty(name = "coin", value = "金额", example = "100")
        private BigDecimal coin = BigDecimal.ZERO;
    }
}
