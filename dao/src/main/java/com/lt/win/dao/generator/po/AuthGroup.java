package com.lt.win.dao.generator.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author David
 * @since 2022-09-20
 */
@TableName("win_auth_group")
public class AuthGroup extends Model<AuthGroup> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 角色组ID
     */
    @TableField("admin_group_id")
    private Integer adminGroupId;

    /**
     * 父类ID
     */
    @TableField("pid")
    private Integer pid;

    /**
     * 创建人
     */
    @TableField("parent")
    private Integer parent;

    /**
     * 角色名
     */
    @TableField("title")
    private String title;

    /**
     * 状态: 1-启用 0-冻结
     */
    @TableField("status")
    private Integer status;

    /**
     * 菜单ID集合
     */
    @TableField("rules")
    private String rules;

    /**
     * 用户组ID
     */
    @TableField("operate_id")
    private Integer operateId;

    /**
     * 数据权限
     */
    @TableField("data_permission")
    private String dataPermission;

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

    public Integer getAdminGroupId() {
        return adminGroupId;
    }

    public void setAdminGroupId(Integer adminGroupId) {
        this.adminGroupId = adminGroupId;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public Integer getOperateId() {
        return operateId;
    }

    public void setOperateId(Integer operateId) {
        this.operateId = operateId;
    }

    public String getDataPermission() {
        return dataPermission;
    }

    public void setDataPermission(String dataPermission) {
        this.dataPermission = dataPermission;
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
        return "AuthGroup{" +
        "id=" + id +
        ", adminGroupId=" + adminGroupId +
        ", pid=" + pid +
        ", parent=" + parent +
        ", title=" + title +
        ", status=" + status +
        ", rules=" + rules +
        ", operateId=" + operateId +
        ", dataPermission=" + dataPermission +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        "}";
    }
}
