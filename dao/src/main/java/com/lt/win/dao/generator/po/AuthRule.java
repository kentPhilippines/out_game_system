package com.lt.win.dao.generator.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 菜单表
 * </p>
 *
 * @author David
 * @since 2022-09-20
 */
@TableName("win_auth_rule")
public class AuthRule extends Model<AuthRule> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 图标
     */
    @TableField("icon")
    private String icon;

    /**
     * 规则唯一标识Controller/action
     */
    @TableField("menu_name")
    private String menuName;

    /**
     * 菜单名称
     */
    @TableField("title")
    private String title;

    /**
     * 父节点ID
     */
    @TableField("pid")
    private Integer pid;

    /**
     * 是否主菜单:1-是 0-否
     */
    @TableField("is_menu")
    private Integer isMenu;

    /**
     * 是否按钮:1-是 0-否
     */
    @TableField("is_race_menu")
    private Integer isRaceMenu;

    /**
     * 状态: 1-启用 0-禁用
     */
    @TableField("status")
    private Integer status;

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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getIsMenu() {
        return isMenu;
    }

    public void setIsMenu(Integer isMenu) {
        this.isMenu = isMenu;
    }

    public Integer getIsRaceMenu() {
        return isRaceMenu;
    }

    public void setIsRaceMenu(Integer isRaceMenu) {
        this.isRaceMenu = isRaceMenu;
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

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "AuthRule{" +
        "id=" + id +
        ", icon=" + icon +
        ", menuName=" + menuName +
        ", title=" + title +
        ", pid=" + pid +
        ", isMenu=" + isMenu +
        ", isRaceMenu=" + isRaceMenu +
        ", status=" + status +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        "}";
    }
}
