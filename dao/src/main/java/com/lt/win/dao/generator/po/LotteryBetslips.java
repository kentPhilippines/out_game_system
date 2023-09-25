package com.lt.win.dao.generator.po;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 彩种投注表
 * </p>
 *
 * @author David
 * @since 2023-09-25
 */
@TableName("win_lottery_betslips")
public class LotteryBetslips extends Model<LotteryBetslips> {

    private static final long serialVersionUID=1L;

    @TableId("id")
    private Long id;

    /**
     * 期号
     */
    @TableField("periods_no")
    private String periodsNo;

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
     * 投注金额
     */
    @TableField("coin_bet")
    private BigDecimal coinBet;

    /**
     * 派彩金额
     */
    @TableField("coin_payout")
    private BigDecimal coinPayout;

    /**
     * 注单状态 0:未结算  1:已结算  
     */
    @TableField("status")
    private Integer status;

    /**
     * 主板编号
     */
    @TableField("main_code")
    private Integer mainCode;

    /**
     * 开奖板块编号
     */
    @TableField("payout_code")
    private String payoutCode;

    /**
     * 投注板块编号
     */
    @TableField("bet_code")
    private Integer betCode;

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

    public String getPeriodsNo() {
        return periodsNo;
    }

    public void setPeriodsNo(String periodsNo) {
        this.periodsNo = periodsNo;
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

    public BigDecimal getCoinBet() {
        return coinBet;
    }

    public void setCoinBet(BigDecimal coinBet) {
        this.coinBet = coinBet;
    }

    public BigDecimal getCoinPayout() {
        return coinPayout;
    }

    public void setCoinPayout(BigDecimal coinPayout) {
        this.coinPayout = coinPayout;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getMainCode() {
        return mainCode;
    }

    public void setMainCode(Integer mainCode) {
        this.mainCode = mainCode;
    }

    public String getPayoutCode() {
        return payoutCode;
    }

    public void setPayoutCode(String payoutCode) {
        this.payoutCode = payoutCode;
    }

    public Integer getBetCode() {
        return betCode;
    }

    public void setBetCode(Integer betCode) {
        this.betCode = betCode;
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
        return "LotteryBetslips{" +
        "id=" + id +
        ", periodsNo=" + periodsNo +
        ", uid=" + uid +
        ", username=" + username +
        ", code=" + code +
        ", name=" + name +
        ", odds=" + odds +
        ", coinBet=" + coinBet +
        ", coinPayout=" + coinPayout +
        ", status=" + status +
        ", mainCode=" + mainCode +
        ", payoutCode=" + payoutCode +
        ", betCode=" + betCode +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        "}";
    }
}
