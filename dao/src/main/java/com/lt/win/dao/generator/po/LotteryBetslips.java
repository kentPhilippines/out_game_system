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
 * 彩种投注表
 * </p>
 *
 * @author David
 * @since 2023-09-14
 */
@TableName("win_lottery_betslips")
public class LotteryBetslips extends Model<LotteryBetslips> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 期号
     */
    @TableField("issue")
    private Integer issue;

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
     * 注单状态 1:待开彩  2:派彩成功  3: 退款
     */
    @TableField("status")
    private Integer status;

    /**
     * 开奖板块编号
     */
    @TableField("plate_code")
    private Integer plateCode;

    /**
     * 开奖板块名称
     */
    @TableField("plate_name")
    private String plateName;

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


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIssue() {
        return issue;
    }

    public void setIssue(Integer issue) {
        this.issue = issue;
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

    public Integer getPlateCode() {
        return plateCode;
    }

    public void setPlateCode(Integer plateCode) {
        this.plateCode = plateCode;
    }

    public String getPlateName() {
        return plateName;
    }

    public void setPlateName(String plateName) {
        this.plateName = plateName;
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
        ", issue=" + issue +
        ", uid=" + uid +
        ", username=" + username +
        ", code=" + code +
        ", name=" + name +
        ", coinBet=" + coinBet +
        ", coinPayout=" + coinPayout +
        ", status=" + status +
        ", plateCode=" + plateCode +
        ", plateName=" + plateName +
        ", betCode=" + betCode +
        ", betName=" + betName +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        "}";
    }
}
