package com.lt.win.dao.generator.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 用户消息状态表
 * </p>
 *
 * @author David
 * @since 2022-09-20
 */
@TableName("win_notice_status")
public class NoticeStatus extends Model<NoticeStatus> {

    private static final long serialVersionUID=1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户ID
     */
    @TableField("uid")
    private Integer uid;

    /**
     * 消息ID
     */
    @TableField("notice_id")
    private Integer noticeId;

    /**
     * 0-未读1-已读
     */
    @TableField("is_read")
    private Integer isRead;

    /**
     * 0-未删除1-删除
     */
    @TableField("is_delete")
    private Integer isDelete;

    /**
     * 创建时间
     */
    @TableField("create_at")
    private Integer createAt;

    /**
     * 创基时间
     */
    @TableField("update_at")
    private Integer updateAt;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Integer noticeId) {
        this.noticeId = noticeId;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Integer createAt) {
        this.createAt = createAt;
    }

    public Integer getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Integer updateAt) {
        this.updateAt = updateAt;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "NoticeStatus{" +
        "id=" + id +
        ", uid=" + uid +
        ", noticeId=" + noticeId +
        ", isRead=" + isRead +
        ", isDelete=" + isDelete +
        ", createAt=" + createAt +
        ", updateAt=" + updateAt +
        "}";
    }
}
