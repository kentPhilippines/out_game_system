package com.lt.win.dao.generator.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 优惠活动表
 * </p>
 *
 * @author David
 * @since 2022-09-24
 */
@TableName("win_promotions")
public class Promotions extends Model<Promotions> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 活动标识:首充优惠-First Deposit Bonus 续充优惠-Second Deposit Bonus 首单包赔-Risk-Free Bet 快乐周末-Happy Weekend Bonus
     */
    @TableField("code")
    private String code;

    /**
     * 名称中文
     */
    @TableField("code_zh")
    private String codeZh;

    /**
     * 图片
     */
    @TableField("img")
    private String img;

    /**
     * 类型:1-充值优惠 2-豪礼赠送
     */
    @TableField("category")
    private String category;

    /**
     * 补充信息
     */
    @TableField("info")
    private String info;

    /**
     * 详情描述
     */
    @TableField("descript")
    private String descript;

    /**
     * 开始时间
     */
    @TableField("started_at")
    private Integer startedAt;

    /**
     * 派彩类型: 0-自动派彩 1-人工派彩 2-手动派彩
     */
    @TableField("payout_category")
    private Integer payoutCategory;

    /**
     * 结算时间
     */
    @TableField("ended_at")
    private Integer endedAt;

    /**
     * 排序(从高到底、ID降序)
     */
    @TableField("sort")
    private Integer sort;

    /**
     * 状态:1-启用 0-停用
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeZh() {
        return codeZh;
    }

    public void setCodeZh(String codeZh) {
        this.codeZh = codeZh;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public Integer getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Integer startedAt) {
        this.startedAt = startedAt;
    }

    public Integer getPayoutCategory() {
        return payoutCategory;
    }

    public void setPayoutCategory(Integer payoutCategory) {
        this.payoutCategory = payoutCategory;
    }

    public Integer getEndedAt() {
        return endedAt;
    }

    public void setEndedAt(Integer endedAt) {
        this.endedAt = endedAt;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
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
        return "Promotions{" +
        "id=" + id +
        ", code=" + code +
        ", codeZh=" + codeZh +
        ", img=" + img +
        ", category=" + category +
        ", info=" + info +
        ", descript=" + descript +
        ", startedAt=" + startedAt +
        ", payoutCategory=" + payoutCategory +
        ", endedAt=" + endedAt +
        ", sort=" + sort +
        ", status=" + status +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        "}";
    }
}
