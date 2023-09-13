package com.lt.win.dao.generator.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 每月税收统计
 * </p>
 *
 * @author David
 * @since 2021-02-01
 */
@TableName("win_revenue_statistics")
public class RevenueStatistics extends Model<RevenueStatistics> {

    private static final long serialVersionUID=1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 游戏ID
     */
    @TableField("game_id")
    private Integer gameId;

    /**
     * 游戏名称
     */
    @TableField("game_name")
    private String gameName;

    /**
     * 统计年
     */
    @TableField("year")
    private Integer year;

    /**
     * 统计月
     */
    @TableField("month")
    private Integer month;

    /**
     * 统计日
     */
    @TableField("day")
    private Integer day;

    /**
     *  投注金额
     */
    @TableField("bet_coin")
    private BigDecimal betCoin;

    /**
     * 有效投注金额
     */
    @TableField("valid_coin")
    private BigDecimal validCoin;

    /**
     * 输赢金额
     */
    @TableField("profit_coin")
    private BigDecimal profitCoin;

    /**
     * 税收比例
     */
    @TableField("rate")
    private BigDecimal rate;

    /**
     * 税收金额
     */
    @TableField("revenue_coin")
    private BigDecimal revenueCoin;

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

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public BigDecimal getBetCoin() {
        return betCoin;
    }

    public void setBetCoin(BigDecimal betCoin) {
        this.betCoin = betCoin;
    }

    public BigDecimal getValidCoin() {
        return validCoin;
    }

    public void setValidCoin(BigDecimal validCoin) {
        this.validCoin = validCoin;
    }

    public BigDecimal getProfitCoin() {
        return profitCoin;
    }

    public void setProfitCoin(BigDecimal profitCoin) {
        this.profitCoin = profitCoin;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getRevenueCoin() {
        return revenueCoin;
    }

    public void setRevenueCoin(BigDecimal revenueCoin) {
        this.revenueCoin = revenueCoin;
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
        return "RevenueStatistics{" +
        "id=" + id +
        ", gameId=" + gameId +
        ", gameName=" + gameName +
        ", year=" + year +
        ", month=" + month +
        ", day=" + day +
        ", betCoin=" + betCoin +
        ", validCoin=" + validCoin +
        ", profitCoin=" + profitCoin +
        ", rate=" + rate +
        ", revenueCoin=" + revenueCoin +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        "}";
    }
}
