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
 * 打码量记录表
 * </p>
 *
 * @author David
 * @since 2022-09-20
 */
@TableName("win_code_records")
public class CodeRecords extends Model<CodeRecords> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * UID
     */
    @TableField("uid")
    private Integer uid;

    /**
     * 用户名
     */
    @TableField("username")
    private String username;

    /**
     * 金额
     */
    @TableField("coin")
    private BigDecimal coin;

    /**
     * 需求打码量
     */
    @TableField("code_require")
    private BigDecimal codeRequire;

    /**
     * 类型:1-充值 2-活动
     */
    @TableField("category")
    private Integer category;

    /**
     * 关联ID
     */
    @TableField("refer_id")
    private Long referId;

    /**
     * 关联提款ID
     */
    @TableField("refer_withdrawal_id")
    private Long referWithdrawalId;

    /**
     * 备注
     */
    @TableField("remarks")
    private String remarks;

    /**
     * 状态:0-未结算 1-结算
     */
    @TableField("status")
    private Integer status;

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

    public BigDecimal getCoin() {
        return coin;
    }

    public void setCoin(BigDecimal coin) {
        this.coin = coin;
    }

    public BigDecimal getCodeRequire() {
        return codeRequire;
    }

    public void setCodeRequire(BigDecimal codeRequire) {
        this.codeRequire = codeRequire;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Long getReferId() {
        return referId;
    }

    public void setReferId(Long referId) {
        this.referId = referId;
    }

    public Long getReferWithdrawalId() {
        return referWithdrawalId;
    }

    public void setReferWithdrawalId(Long referWithdrawalId) {
        this.referWithdrawalId = referWithdrawalId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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
        return "CodeRecords{" +
        "id=" + id +
        ", uid=" + uid +
        ", username=" + username +
        ", coin=" + coin +
        ", codeRequire=" + codeRequire +
        ", category=" + category +
        ", referId=" + referId +
        ", referWithdrawalId=" + referWithdrawalId +
        ", remarks=" + remarks +
        ", status=" + status +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        "}";
    }
}
