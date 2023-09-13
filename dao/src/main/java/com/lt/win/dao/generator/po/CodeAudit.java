package com.lt.win.dao.generator.po;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author David
 * @since 2022-09-20
 */
@TableName("win_code_audit")
public class CodeAudit extends Model<CodeAudit> {

    private static final long serialVersionUID=1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * uid
     */
    @TableField("uid")
    private Integer uid;

    /**
     * 用户名
     */
    @TableField("username")
    private String username;

    /**
     * 稽核人Id
     */
    @TableField("audit_id")
    private Integer auditId;

    /**
     * 所需打码量
     */
    @TableField("code_require")
    private BigDecimal codeRequire;

    /**
     * 真实打码量
     */
    @TableField("code_real")
    private BigDecimal codeReal;

    /**
     * 对应的提款订单ID
     */
    @TableField("refer_id")
    private Long referId;

    /**
     * 状态:1-稽核成功 2-稽核失败
     */
    @TableField("status")
    private Integer status;

    /**
     * 创建时间
     */
    @TableField("created_at")
    private Integer createdAt;

    @TableField("updated_at")
    private Integer updatedAt;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAuditId() {
        return auditId;
    }

    public void setAuditId(Integer auditId) {
        this.auditId = auditId;
    }

    public BigDecimal getCodeRequire() {
        return codeRequire;
    }

    public void setCodeRequire(BigDecimal codeRequire) {
        this.codeRequire = codeRequire;
    }

    public BigDecimal getCodeReal() {
        return codeReal;
    }

    public void setCodeReal(BigDecimal codeReal) {
        this.codeReal = codeReal;
    }

    public Long getReferId() {
        return referId;
    }

    public void setReferId(Long referId) {
        this.referId = referId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Integer createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Integer updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "CodeAudit{" +
        "id=" + id +
        ", uid=" + uid +
        ", username=" + username +
        ", auditId=" + auditId +
        ", codeRequire=" + codeRequire +
        ", codeReal=" + codeReal +
        ", referId=" + referId +
        ", status=" + status +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        "}";
    }
}
