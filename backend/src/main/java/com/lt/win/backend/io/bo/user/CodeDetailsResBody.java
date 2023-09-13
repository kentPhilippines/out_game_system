package com.lt.win.backend.io.bo.user;

import com.lt.win.utils.components.pagination.ResPage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author: wells
 * @date: 2020/8/27
 * @description:
 */


@Data
@ApiModel(value = "CodeDetailsResBody", description = "打码量详情响应实体类")
public class CodeDetailsResBody {
    @ApiModelProperty(name = "codeRequire", value = "所需打码量", example = "79681096069025792")
    private BigDecimal codeRequire;
    @ApiModelProperty(name = "codeReal", value = "真实打码量", example = "79681096069025792")
    private BigDecimal codeReal;
    @ApiModelProperty(name = "extraCode", value = "额外打码量", example = "50.0")
    private BigDecimal extraCode;
    @ApiModelProperty(name = "CodeRecordsResDto", value = "打码量列表", example = "")
    private ResPage<CodeListResBody> codeListResDtoList;
}



