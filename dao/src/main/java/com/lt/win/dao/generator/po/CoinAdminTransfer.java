package com.lt.win.dao.generator.po;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 后台调账记录
 * </p>
 *
 * @author David
 * @since 2022-09-20
 */
@TableName("win_coin_admin_transfer")
public class CoinAdminTransfer extends Model<CoinAdminTransfer> {

    private static final long serialVersionUID=1L;

    @TableId("id")
    private Long id;

    /**
     * 管理员ID
     */
    @TableField("admin_id")
    private Integer adminId;

    /**
     * 调账金额
     */
    @TableField("coin")
    private BigDecimal coin;

    /**
     * 调账前金额
     */
    @TableField("coin_before")
    private BigDecimal coinBefore;

    /**
     * 用户ID
     */
    @TableField("uid")
    private Integer uid;

    /**
     * 用户名
     */
    @TableField("username")
    private String username;

    /**
     * 调账原因:0-其他 1-误存调账 2-活动调账
     */
    @TableField("category")
    private Integer category;

    /**
     * 调账原因
     */
    @TableField("mark")
    private String mark;

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

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
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

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
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
        return "CoinAdminTransfer{" +
        "id=" + id +
        ", adminId=" + adminId +
        ", coin=" + coin +
        ", coinBefore=" + coinBefore +
        ", uid=" + uid +
        ", username=" + username +
        ", category=" + category +
        ", mark=" + mark +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        "}";
    }
}
