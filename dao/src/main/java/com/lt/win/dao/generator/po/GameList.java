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
 * 游戏类型表
 * </p>
 *
 * @author David
 * @since 2022-09-20
 */
@TableName("win_game_list")
public class GameList extends Model<GameList> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 名称
     */
    @TableField("name")
    private String name;

    /**
     * 图标
     */
    @TableField("icon")
    private String icon;

    /**
     * 类型:1-体育 2-电子 3-真人 4-捕鱼 5-棋牌 6-电竞 7-彩票 8-动物 9-快速 10-技能
     */
    @TableField("group_id")
    private Integer groupId;

    /**
     * 平台配置表ID(plat_list)
     */
    @TableField("plat_list_id")
    private Integer platListId;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 排序: 从高到低
     */
    @TableField("sort")
    private Integer sort;

    /**
     * 税收比例
     */
    @TableField("revenue_rate")
    private BigDecimal revenueRate;

    /**
     * 状态: 1-启用 0-停用
     */
    @TableField("status")
    private Integer status;

    @TableField("created_at")
    private Integer createdAt;

    @TableField("updated_at")
    private Integer updatedAt;

    @TableField("game_count")
    private Integer gameCount;
    /**
     * 维护时间
     */
    @TableField("maintenance")
    private String maintenance;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getPlatListId() {
        return platListId;
    }

    public void setPlatListId(Integer platListId) {
        this.platListId = platListId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public BigDecimal getRevenueRate() {
        return revenueRate;
    }

    public void setRevenueRate(BigDecimal revenueRate) {
        this.revenueRate = revenueRate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getMaintenance() {
        return maintenance;
    }

    public void setMaintenance(String maintenance) {
        this.maintenance = maintenance;
    }

    public Integer getGameCount() {
        return gameCount;
    }

    public void setGameCount(Integer gameCount) {
        this.gameCount = gameCount;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "GameList{" +
        "id=" + id +
        ", name=" + name +
        ", icon=" + icon +
        ", groupId=" + groupId +
        ", platListId=" + platListId +
        ", remark=" + remark +
        ", sort=" + sort +
        ", revenueRate=" + revenueRate +
        ", status=" + status +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        ", maintenance=" + maintenance +
        "}";
    }
}
