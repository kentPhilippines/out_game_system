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
 * 彩种表
 * </p>
 *
 * @author David
 * @since 2023-09-17
 */
@TableName("win_lottery_type")
public class LotteryType extends Model<LotteryType> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 彩种编码
     */
    @TableField("code")
    private String code;

    /**
     * 彩种名称
     */
    @TableField("name")
    private String name;

    /**
     * 赔率
     */
    @TableField("odds")
    private BigDecimal odds;

    /**
     * 封盘时间（秒）
     */
    @TableField("pause_at")
    private Integer pauseAt;

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

    public BigDecimal getOdds() {
        return odds;
    }

    public void setOdds(BigDecimal odds) {
        this.odds = odds;
    }

    public Integer getPauseAt() {
        return pauseAt;
    }

    public void setPauseAt(Integer pauseAt) {
        this.pauseAt = pauseAt;
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
        return "LotteryType{" +
        "id=" + id +
        ", code=" + code +
        ", name=" + name +
        ", odds=" + odds +
        ", pauseAt=" + pauseAt +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        "}";
    }
}
