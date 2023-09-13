package com.lt.win.backend.io.bo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author : Wells
 * @Date : 2020-11-28 5:26 下午
 * @Description : xx
 */
@Data
@ApiModel(value = "CodeListResBody", description = "打码量列表响应实体类")
public class CodeListResBody {
    @ApiModelProperty(name = "id", value = "ID", example = "1")
    private Integer id;
    @ApiModelProperty(name = "uid", value = "用户ID", example = "10")
    private Integer uid;
    @ApiModelProperty(name = "coin", value = "金额", example = "100.00")
    private BigDecimal coin;
    @ApiModelProperty(name = "codeRequire", value = "所需打码量", example = "100.00")
    private BigDecimal codeRequire;
    @ApiModelProperty(name = "category", value = "类型:1-充值 2-活动；dic_code_records_category", example = "1")
    private Integer category;
    @ApiModelProperty(name = "referId", value = "关联ID", example = "69749995263365120")
    private Long referId;
    @ApiModelProperty(name = "status", value = "状态：1：稽核成功，2：稽核失败;dic_win_code_audit_status", example = "1")
    private Integer status;
    @ApiModelProperty(name = "remarks", value = "备注", example = "稽核成功！")
    private String remarks;
    @ApiModelProperty(name = "createdAt", value = "创建时间", example = "10")
    private Integer createdAt;
}
