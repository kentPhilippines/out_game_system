package com.lt.win.dao.generator.po;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 奖金表
 * </p>
 *
 * @author David
 * @since 2022-10-13
 */
@TableName("win_coin_rewards")
public class CoinRewards extends Model<CoinRewards> {

    private static final long serialVersionUID=1L;

    @TableId("id")
    private Long id;

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
     * 即时金额
     */
    @TableField("coin_before")
    private BigDecimal coinBefore;

    /**
     * 关联ID(活动表ID)
     */
    @TableField("refer_id")
    private Integer referId;

    /**
     * 关联Code(活动表Code)
     */
    @TableField("refer_code")
    private String referCode;

    /**
     * 流水倍数
     */
    @TableField("flow_claim")
    private Integer flowClaim;

    /**
     * 所需打码量
     */
    @TableField("codes")
    private BigDecimal codes;

    /**
     * 活动结束时间
     */
    @TableField("ended_at")
    private Integer endedAt;

    /**
     * 备注
     */
    @TableField("info")
    private String info;

    /**
     * 状态:0-申请中 1-已满足 2-已结束
     */
    @TableField("status")
    private Integer status;

    @TableField("created_at")
    private Integer createdAt;

    @TableField("updated_at")
    private Integer updatedAt;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public BigDecimal getCoinBefore() {
        return coinBefore;
    }

    public void setCoinBefore(BigDecimal coinBefore) {
        this.coinBefore = coinBefore;
    }

    public Integer getReferId() {
        return referId;
    }

    public void setReferId(Integer referId) {
        this.referId = referId;
    }

    public String getReferCode() {
        return referCode;
    }

    public void setReferCode(String referCode) {
        this.referCode = referCode;
    }

    public Integer getFlowClaim() {
        return flowClaim;
    }

    public void setFlowClaim(Integer flowClaim) {
        this.flowClaim = flowClaim;
    }

    public BigDecimal getCodes() {
        return codes;
    }

    public void setCodes(BigDecimal codes) {
        this.codes = codes;
    }

    public Integer getEndedAt() {
        return endedAt;
    }

    public void setEndedAt(Integer endedAt) {
        this.endedAt = endedAt;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
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
        return "CoinRewards{" +
        "id=" + id +
        ", uid=" + uid +
        ", username=" + username +
        ", coin=" + coin +
        ", coinBefore=" + coinBefore +
        ", referId=" + referId +
        ", referCode=" + referCode +
        ", flowClaim=" + flowClaim +
        ", codes=" + codes +
        ", endedAt=" + endedAt +
        ", info=" + info +
        ", status=" + status +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        "}";
    }
}
