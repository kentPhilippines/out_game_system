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
 * @since 2023-09-25
 */
@TableName("win_lottery_open")
public class LotteryOpen extends Model<LotteryOpen> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 期号
     */
    @TableField("periods_no")
    private String periodsNo;

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
    private String openCode;

    /**
     * 主板编号
     */
    @TableField("main_code")
    private Integer mainCode;

    /**
     * 开奖全部板块编号
     */
    @TableField("open_all_code")
    private String openAllCode;

    /**
     * 开奖状态；0：未开奖 1:已开奖
     */
    @TableField("status")
    private Integer status;

    /**
     *  修改人
     */
    @TableField("update_user")
    private String updateUser;

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

    public String getPeriodsNo() {
        return periodsNo;
    }

    public void setPeriodsNo(String periodsNo) {
        this.periodsNo = periodsNo;
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

    public String getOpenCode() {
        return openCode;
    }

    public void setOpenCode(String openCode) {
        this.openCode = openCode;
    }

    public Integer getMainCode() {
        return mainCode;
    }

    public void setMainCode(Integer mainCode) {
        this.mainCode = mainCode;
    }

    public String getOpenAllCode() {
        return openAllCode;
    }

    public void setOpenAllCode(String openAllCode) {
        this.openAllCode = openAllCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
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
        ", periodsNo=" + periodsNo +
        ", lotteryCode=" + lotteryCode +
        ", lotteryName=" + lotteryName +
        ", openCode=" + openCode +
        ", mainCode=" + mainCode +
        ", openAllCode=" + openAllCode +
        ", status=" + status +
        ", updateUser=" + updateUser +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        "}";
    }
}
