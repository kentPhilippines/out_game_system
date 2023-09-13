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
 * 会员等级
 * </p>
 *
 * @author David
 * @since 2022-09-23
 */
@TableName("win_user_level")
public class UserLevel extends Model<UserLevel> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 会员等级
     */
    @TableField("code")
    private String code;

    /**
     * 等级名称
     */
    @TableField("name")
    private String name;

    /**
     * ICON
     */
    @TableField("icon")
    private String icon;

    /**
     * 最低积分
     */
    @TableField("score_upgrade_min")
    private Integer scoreUpgradeMin;

    /**
     * 最高积分
     */
    @TableField("score_upgrade_max")
    private Integer scoreUpgradeMax;

    /**
     * 经验率
     */
    @TableField("score_upgrade_rate")
    private BigDecimal scoreUpgradeRate;

    /**
     * 等级保持积分
     */
    @TableField("score_relegation")
    private Integer scoreRelegation;

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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getScoreUpgradeMin() {
        return scoreUpgradeMin;
    }

    public void setScoreUpgradeMin(Integer scoreUpgradeMin) {
        this.scoreUpgradeMin = scoreUpgradeMin;
    }

    public Integer getScoreUpgradeMax() {
        return scoreUpgradeMax;
    }

    public void setScoreUpgradeMax(Integer scoreUpgradeMax) {
        this.scoreUpgradeMax = scoreUpgradeMax;
    }

    public BigDecimal getScoreUpgradeRate() {
        return scoreUpgradeRate;
    }

    public void setScoreUpgradeRate(BigDecimal scoreUpgradeRate) {
        this.scoreUpgradeRate = scoreUpgradeRate;
    }

    public Integer getScoreRelegation() {
        return scoreRelegation;
    }

    public void setScoreRelegation(Integer scoreRelegation) {
        this.scoreRelegation = scoreRelegation;
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
        return "UserLevel{" +
        "id=" + id +
        ", code=" + code +
        ", name=" + name +
        ", icon=" + icon +
        ", scoreUpgradeMin=" + scoreUpgradeMin +
        ", scoreUpgradeMax=" + scoreUpgradeMax +
        ", scoreUpgradeRate=" + scoreUpgradeRate +
        ", scoreRelegation=" + scoreRelegation +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        "}";
    }
}
