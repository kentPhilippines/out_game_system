package com.lt.win.dao.generator.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 系统配置
 * </p>
 *
 * @author David
 * @since 2022-09-20
 */
@TableName("win_config")
public class Config extends Model<Config> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 名字
     */
    @TableField("title")
    private String title;

    /**
     * 名字中文
     */
    @TableField("title_zh")
    private String titleZh;

    /**
     * 字段值
     */
    @TableField("value")
    private String value;

    /**
     * 类型:0-全部 1-WEB 2-后台 3-不显示
     */
    @TableField("show_app")
    private Integer showApp;

    /**
     * 支持修改:1-支持 0-不支持
     */
    @TableField("can_modify")
    private Integer canModify;

    /**
     * 是否启用:1-启用 0-不启用
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleZh() {
        return titleZh;
    }

    public void setTitleZh(String titleZh) {
        this.titleZh = titleZh;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getShowApp() {
        return showApp;
    }

    public void setShowApp(Integer showApp) {
        this.showApp = showApp;
    }

    public Integer getCanModify() {
        return canModify;
    }

    public void setCanModify(Integer canModify) {
        this.canModify = canModify;
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
        return "Config{" +
        "id=" + id +
        ", title=" + title +
        ", titleZh=" + titleZh +
        ", value=" + value +
        ", showApp=" + showApp +
        ", canModify=" + canModify +
        ", status=" + status +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        "}";
    }
}
