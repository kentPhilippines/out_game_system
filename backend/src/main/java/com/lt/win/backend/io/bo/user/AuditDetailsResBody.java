package com.lt.win.backend.io.bo.user;

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
@ApiModel(value = "AuditDetailsResBody", description = "稽核详情响应实体类")
public class AuditDetailsResBody {
    @ApiModelProperty(name = "id", value = "ID", example = "1")
    private Integer id;
    @ApiModelProperty(name = "uid", value = "用户ID", example = "10")
    private Integer uid;
    @ApiModelProperty(name = "auditId", value = "稽核人Id", example = "10")
    private Integer auditId;
    @ApiModelProperty(name = "codeRequire", value = "所需打码量", example = "100.00")
    private BigDecimal codeRequire;
    @ApiModelProperty(name = "codeReal", value = "真实打码量", example = "100.00")
    private BigDecimal codeReal;
    @ApiModelProperty(name = "referId", value = "对应的提款订单ID", example = "69749995263365120")
    private Long referId;
    @ApiModelProperty(name = "status", value = "状态：1：稽核成功，2：稽核失败;dic_win_code_audit_status", example = "1")
    private Integer status;
    @ApiModelProperty(name = "createdAt", value = "创建时间", example = "10")
    private Integer createdAt;
}
