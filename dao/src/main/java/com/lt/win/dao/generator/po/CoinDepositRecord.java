package com.lt.win.dao.generator.po;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 充值记录表
 * </p>
 *
 * @author David
 * @since 2022-11-17
 */
@TableName("win_coin_deposit_record")
public class CoinDepositRecord extends Model<CoinDepositRecord> {

    private static final long serialVersionUID=1L;

    @TableId("id")
    private Long id;

    /**
     * 订单号(三方平台用)
     */
    @TableField("order_id")
    private String orderId;

    /**
     * 三方平台订单号
     */
    @TableField("plat_order_id")
    private String platOrderId;

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
     * 支付通道编码
     */
    @TableField("code")
    private String code;

    /**
     * 平台名称
     */
    @TableField("plat_name")
    private String platName;

    /**
     * 充值前金额
     */
    @TableField("coin_before")
    private BigDecimal coinBefore;

    /**
     * 加密地址
     */
    @TableField("pay_address")
    private String payAddress;

    /**
     * 充值金额
     */
    @TableField("pay_amount")
    private BigDecimal payAmount;

    /**
     * 汇率
     */
    @TableField("exchange_rate")
    private BigDecimal exchangeRate;

    /**
     * 到账金额
     */
    @TableField("real_amount")
    private BigDecimal realAmount;

    /**
     * 币种
     */
    @TableField("currency")
    private String currency;

    /**
     * 充值标识:1-首充 2-二充 9-其他
     */
    @TableField("dep_status")
    private Integer depStatus;

    /**
     * 类型:0-钱包充值 1-佣金钱包转账充值
     */
    @TableField("category")
    private Integer category;

    /**
     * 货币类型:0-数字货币 1-法币
     */
    @TableField("category_currency")
    private Integer categoryCurrency;

    /**
     * 转账类型:1-TRC-20 2-ERC-20 3-BANK 4-PIX 5-GCASH
     */
    @TableField("category_transfer")
    private Integer categoryTransfer;

    /**
     * 审核ID
     */
    @TableField("admin_uid")
    private Integer adminUid;

    /**
     * 备注
     */
    @TableField("mark")
    private String mark;

    /**
     * 状态: 0-申请中 1-成功 2-失败
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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPlatOrderId() {
        return platOrderId;
    }

    public void setPlatOrderId(String platOrderId) {
        this.platOrderId = platOrderId;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPlatName() {
        return platName;
    }

    public void setPlatName(String platName) {
        this.platName = platName;
    }

    public BigDecimal getCoinBefore() {
        return coinBefore;
    }

    public void setCoinBefore(BigDecimal coinBefore) {
        this.coinBefore = coinBefore;
    }

    public String getPayAddress() {
        return payAddress;
    }

    public void setPayAddress(String payAddress) {
        this.payAddress = payAddress;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public BigDecimal getRealAmount() {
        return realAmount;
    }

    public void setRealAmount(BigDecimal realAmount) {
        this.realAmount = realAmount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getDepStatus() {
        return depStatus;
    }

    public void setDepStatus(Integer depStatus) {
        this.depStatus = depStatus;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
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

    public Integer getAdminUid() {
        return adminUid;
    }

    public void setAdminUid(Integer adminUid) {
        this.adminUid = adminUid;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
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
        return "CoinDepositRecord{" +
        "id=" + id +
        ", orderId=" + orderId +
        ", platOrderId=" + platOrderId +
        ", uid=" + uid +
        ", username=" + username +
        ", code=" + code +
        ", platName=" + platName +
        ", coinBefore=" + coinBefore +
        ", payAddress=" + payAddress +
        ", payAmount=" + payAmount +
        ", exchangeRate=" + exchangeRate +
        ", realAmount=" + realAmount +
        ", currency=" + currency +
        ", depStatus=" + depStatus +
        ", category=" + category +
        ", categoryCurrency=" + categoryCurrency +
        ", categoryTransfer=" + categoryTransfer +
        ", adminUid=" + adminUid +
        ", mark=" + mark +
        ", status=" + status +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        "}";
    }
}
