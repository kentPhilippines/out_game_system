package com.lt.win.dao.generator.po;

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
 * @since 2022-11-16
 */
@TableName("win_pay_channel")
public class PayChannel extends Model<PayChannel> {

    private static final long serialVersionUID=1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 通道编码
     */
    @TableField("code")
    private String code;

    /**
     * 通道名称
     */
    @TableField("name")
    private String name;

    /**
     * 平台ID
     */
    @TableField("plat_id")
    private Integer platId;

    /**
     * 平台名称
     */
    @TableField("plat_name")
    private String platName;

    /**
     * 支付类型；1:代收 2:代付
     */
    @TableField("category")
    private Integer category;

    /**
     * 转账类型：1-TRC,2-ERC,3-BANK,4-PIX,5-GCASH
     */
    @TableField("category_transfer")
    private Integer categoryTransfer;

    /**
     * 提款货币类型:0-数字货币 1-法币方式;1:USDT 2:Bank Card
     */
    @TableField("category_currency")
    private Integer categoryCurrency;

    /**
     * 最小金额
     */
    @TableField("min_coin")
    private Integer minCoin;

    /**
     * 最大金额
     */
    @TableField("max_coin")
    private Integer maxCoin;

    /**
     * 状态;0:关闭 1:开启
     */
    @TableField("status")
    private Integer status;

    /**
     * 请求三方支付地址
     */
    @TableField("request_url")
    private String requestUrl;

    /**
     * 回调地址
     */
    @TableField("notify_url")
    private String notifyUrl;

    /**
     * 币种
     */
    @TableField("currency")
    private String currency;

    /**
     * 通道配置参数
     */
    @TableField("channel_config")
    private String channelConfig;

    /**
     * 创建时间
     */
    @TableField("created_at")
    private Integer createdAt;

    /**
     * 修改时间
     */
    @TableField("updated_at")
    private Integer updatedAt;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPlatId() {
        return platId;
    }

    public void setPlatId(Integer platId) {
        this.platId = platId;
    }

    public String getPlatName() {
        return platName;
    }

    public void setPlatName(String platName) {
        this.platName = platName;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Integer getCategoryTransfer() {
        return categoryTransfer;
    }

    public void setCategoryTransfer(Integer categoryTransfer) {
        this.categoryTransfer = categoryTransfer;
    }

    public Integer getCategoryCurrency() {
        return categoryCurrency;
    }

    public void setCategoryCurrency(Integer categoryCurrency) {
        this.categoryCurrency = categoryCurrency;
    }

    public Integer getMinCoin() {
        return minCoin;
    }

    public void setMinCoin(Integer minCoin) {
        this.minCoin = minCoin;
    }

    public Integer getMaxCoin() {
        return maxCoin;
    }

    public void setMaxCoin(Integer maxCoin) {
        this.maxCoin = maxCoin;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getChannelConfig() {
        return channelConfig;
    }

    public void setChannelConfig(String channelConfig) {
        this.channelConfig = channelConfig;
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
        return "PayChannel{" +
        "id=" + id +
        ", code=" + code +
        ", name=" + name +
        ", platId=" + platId +
        ", platName=" + platName +
        ", category=" + category +
        ", categoryTransfer=" + categoryTransfer +
        ", categoryCurrency=" + categoryCurrency +
        ", minCoin=" + minCoin +
        ", maxCoin=" + maxCoin +
        ", status=" + status +
        ", requestUrl=" + requestUrl +
        ", notifyUrl=" + notifyUrl +
        ", currency=" + currency +
        ", channelConfig=" + channelConfig +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        "}";
    }
}
