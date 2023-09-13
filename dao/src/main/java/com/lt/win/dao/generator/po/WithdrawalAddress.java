package com.lt.win.dao.generator.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 用户提款址表
 * </p>
 *
 * @author David
 * @since 2022-11-14
 */
@TableName("win_withdrawal_address")
public class WithdrawalAddress extends Model<WithdrawalAddress> {

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
     * 货币类型:0-数字货币 1-法币
     */
    @TableField("category_currency")
    private Integer categoryCurrency;

    /**
     * 转账类型：1-TRC,2-ERC,3-BANK,4-PIX,5-GCASH
     */
    @TableField("category_transfer")
    private Integer categoryTransfer;

    /**
     * 提款地址
     */
    @TableField("address")
    private String address;

    /**
     * 状态:1-默认地址(启用) 2-正常启用 3-删除
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

    public Integer getCategoryCurrency() {
        return categoryCurrency;
    }

    public void setCategoryCurrency(Integer categoryCurrency) {
        this.categoryCurrency = categoryCurrency;
    }

    public Integer getCategoryTransfer() {
        return categoryTransfer;
    }

    public void setCategoryTransfer(Integer categoryTransfer) {
        this.categoryTransfer = categoryTransfer;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
        return "WithdrawalAddress{" +
        "id=" + id +
        ", uid=" + uid +
        ", username=" + username +
        ", categoryCurrency=" + categoryCurrency +
        ", categoryTransfer=" + categoryTransfer +
        ", address=" + address +
        ", status=" + status +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        "}";
    }
}
