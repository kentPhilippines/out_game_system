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
 * @since 2023-09-15
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
     * 注单状态 0:待开彩  1派彩成功  2: 退款
     */
    @TableField("status")
    private Integer status;

    /**
     * 开奖板块编号
     */
    @TableField("payout_code")
    private Integer payoutCode;

    /**
     * 开奖板块名称
     */
    @TableField("payout_name")
    private String payoutName;

    /**
     * 投注板块编号
     */
    @TableField("bet_code")
    private Integer betCode;

    /**
     * 投注板块名称
     */
    @TableField("bet_name")
    private String betName;

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

    public Integer getPayoutCode() {
        return payoutCode;
    }

    public void setPayoutCode(Integer payoutCode) {
        this.payoutCode = payoutCode;
    }

    public String getPayoutName() {
        return payoutName;
    }

    public void setPayoutName(String payoutName) {
        this.payoutName = payoutName;
    }

    public Integer getBetCode() {
        return betCode;
    }

    public void setBetCode(Integer betCode) {
        this.betCode = betCode;
    }

    public String getBetName() {
        return betName;
    }

    public void setBetName(String betName) {
        this.betName = betName;
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
        ", payoutCode=" + payoutCode +
        ", payoutName=" + payoutName +
        ", betCode=" + betCode +
        ", betName=" + betName +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        "}";
    }
}
