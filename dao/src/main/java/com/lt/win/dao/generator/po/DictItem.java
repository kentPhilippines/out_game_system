package com.lt.win.dao.generator.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 字典项表
 * </p>
 *
 * @author David
 * @since 2022-09-26
 */
@TableName("win_dict_item")
public class DictItem extends Model<DictItem> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 字典码
     */
    @TableField("code")
    private String code;

    /**
     * 字典名称
     */
    @TableField("title")
    private String title;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 排序:从高到低
     */
    @TableField("sort")
    private Integer sort;

    /**
     * 字典表ID
     */
    @TableField("refer_id")
    private Integer referId;

    /**
     * 状态:1-启用 0-禁用
     */
    @TableField("status")
    private Integer status;

    /**
     * 类型:0-全部 1-前端 2-后台
     */
    @TableField("is_show")
    private Integer isShow;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Integer getReferId() {
        return referId;
    }

    public void setReferId(Integer referId) {
        this.referId = referId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsShow() {
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
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
        return "DictItem{" +
        "id=" + id +
        ", code=" + code +
        ", title=" + title +
        ", remark=" + remark +
        ", sort=" + sort +
        ", referId=" + referId +
        ", status=" + status +
        ", isShow=" + isShow +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        "}";
    }
}
