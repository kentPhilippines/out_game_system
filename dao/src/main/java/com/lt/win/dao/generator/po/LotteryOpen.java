package com.lt.win.dao.generator.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 彩票开奖表
 * </p>
 *
 * @author David
 * @since 2023-09-14
 */
@TableName("win_lottery_open")
public class LotteryOpen extends Model<LotteryOpen> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 期号
     */
    @TableField("issue")
    private Integer issue;

    /**
     * 彩种编码
     */
    @TableField("lottery_code")
    private String lotteryCode;

    /**
     * 彩种名称
     */
    @TableField("lottery_name")
    private String lotteryName;

    /**
     * 开奖板块编号
     */
    @TableField("open_code")
    private Integer openCode;

    /**
     * 开奖板块名称
     */
    @TableField("open_name")
    private String openName;

    /**
     * 开奖全部板块编号
     */
    @TableField("open_all_code")
    private String openAllCode;

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

    public String getLotteryCode() {
        return lotteryCode;
    }

    public void setLotteryCode(String lotteryCode) {
        this.lotteryCode = lotteryCode;
    }

    public String getLotteryName() {
        return lotteryName;
    }

    public void setLotteryName(String lotteryName) {
        this.lotteryName = lotteryName;
    }

    public Integer getOpenCode() {
        return openCode;
    }

    public void setOpenCode(Integer openCode) {
        this.openCode = openCode;
    }

    public String getOpenName() {
        return openName;
    }

    public void setOpenName(String openName) {
        this.openName = openName;
    }

    public String getOpenAllCode() {
        return openAllCode;
    }

    public void setOpenAllCode(String openAllCode) {
        this.openAllCode = openAllCode;
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
        return "LotteryOpen{" +
        "id=" + id +
        ", issue=" + issue +
        ", lotteryCode=" + lotteryCode +
        ", lotteryName=" + lotteryName +
        ", openCode=" + openCode +
        ", openName=" + openName +
        ", openAllCode=" + openAllCode +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        "}";
    }
}
