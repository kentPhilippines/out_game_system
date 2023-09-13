package com.lt.win.dao.generator.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 三方支付配置信息
 * </p>
 *
 * @author David
 * @since 2022-09-30
 */
@TableName("win_pay_plat_config")
public class PayPlatConfig extends Model<PayPlatConfig> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 平台名称
     */
    @TableField("plat_name")
    private String platName;

    /**
     * 商户号
     */
    @TableField("merchant_id")
    private String merchantId;

    /**
     * 商户秘钥
     */
    @TableField("api_key")
    private String apiKey;

    /**
     * 状态:0-停用 1-启用 2-删除
     */
    @TableField("status")
    private Integer status;

    /**
     * 平台特殊配置
     */
    @TableField("plat_config")
    private String platConfig;

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

    public String getPlatName() {
        return platName;
    }

    public void setPlatName(String platName) {
        this.platName = platName;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPlatConfig() {
        return platConfig;
    }

    public void setPlatConfig(String platConfig) {
        this.platConfig = platConfig;
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
        return "PayPlatConfig{" +
        "id=" + id +
        ", platName=" + platName +
        ", merchantId=" + merchantId +
        ", apiKey=" + apiKey +
        ", status=" + status +
        ", platConfig=" + platConfig +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        "}";
    }
}
