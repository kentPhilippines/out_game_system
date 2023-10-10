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
 * 用户日志表
 * </p>
 *
 * @author David
 * @since 2023-10-09
 */
@TableName("win_user_login_log")
public class UserLoginLog extends Model<UserLoginLog> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * UID
     */
    @TableField("uid")
    private Integer uid;

    /**
     * 用户名
     */
    @TableField("username")
    private String username;

    /**
     * 账户余额
     */
    @TableField("coin")
    private BigDecimal coin;

    /**
     * IP地址
     */
    @TableField("ip")
    private String ip;

    /**
     * 游戏名称
     */
    @TableField("game_name")
    private String gameName;

    /**
     * 登录链接
     */
    @TableField("url")
    private String url;

    /**
     * 设备:m-手机 d-电脑 ANDROID-安卓 IOS-苹果
     */
    @TableField("device")
    private String device;

    /**
     * 设备详情
     */
    @TableField("user_agent")
    private String userAgent;

    /**
     * 类型:0-登出 1-登录 2-进入游戏
     */
    @TableField("category")
    private Integer category;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

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

    public BigDecimal getCoin() {
        return coin;
    }

    public void setCoin(BigDecimal coin) {
        this.coin = coin;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
        return "UserLoginLog{" +
        "id=" + id +
        ", uid=" + uid +
        ", username=" + username +
        ", coin=" + coin +
        ", ip=" + ip +
        ", gameName=" + gameName +
        ", url=" + url +
        ", device=" + device +
        ", userAgent=" + userAgent +
        ", category=" + category +
        ", remark=" + remark +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        "}";
    }
}
