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
 * 客户表
 * </p>
 *
 * @author David
 * @since 2023-10-03
 */
@TableName("win_user")
public class User extends Model<User> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名
     */
    @TableField("username")
    private String username;

    /**
     * 头像
     */
    @TableField("avatar")
    private String avatar;

    /**
     * 冻结金额
     */
    @TableField("fcoin")
    private BigDecimal fcoin;

    /**
     * 佣金可提现金额
     */
    @TableField("coin_commission")
    private BigDecimal coinCommission;

    /**
     * 会员等级
     */
    @TableField("level_id")
    private Integer levelId;

    /**
     * 角色:0-会员 1-代理 4-测试
     */
    @TableField("role")
    private Integer role;

    /**
     * 是否推广:0-不是 1-是
     */
    @TableField("is_promoter")
    private Integer isPromoter;

    /**
     * 会员旗
     */
    @TableField("flag")
    private Integer flag;

    /**
     * 真实姓名
     */
    @TableField("real_name")
    private String realName;

    /**
     * 个性签名
     */
    @TableField("signature")
    private String signature;

    /**
     * 生日
     */
    @TableField("birthday")
    private String birthday;

    /**
     * 区号
     */
    @TableField("area_code")
    private String areaCode;

    /**
     * 手机号码
     */
    @TableField("mobile")
    private String mobile;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 性别:1-男 0-女 2-未知
     */
    @TableField("sex")
    private Integer sex;

    /**
     * 是否绑定银行卡:1-已绑定 0-未绑定
     */
    @TableField("bind_bank")
    private Integer bindBank;

    /**
     * 家庭地址
     */
    @TableField("address")
    private String address;

    /**
     * 积分
     */
    @TableField("score")
    private Integer score;

    /**
     * 推广码
     */
    @TableField("promo_code")
    private String promoCode;

    /**
     * 上1级代理
     */
    @TableField("sup_uid_1")
    private Integer supUid1;

    /**
     * 上1级代理
     */
    @TableField("sup_username_1")
    private String supUsername1;

    /**
     * 上2级代理
     */
    @TableField("sup_uid_2")
    private Integer supUid2;

    /**
     * 上3级代理
     */
    @TableField("sup_uid_3")
    private Integer supUid3;

    /**
     * 上4级代理
     */
    @TableField("sup_uid_4")
    private Integer supUid4;

    /**
     * 上5级代理
     */
    @TableField("sup_uid_5")
    private Integer supUid5;

    /**
     * 上6级代理
     */
    @TableField("sup_uid_6")
    private Integer supUid6;

    /**
     * 顶级推广用户名
     */
    @TableField("sup_uid_top")
    private Integer supUidTop;

    /**
     * 顶级推广用户名
     */
    @TableField("sup_username_top")
    private String supUsernameTop;

    /**
     * 顶级推广层级
     */
    @TableField("sup_level_top")
    private Integer supLevelTop;

    /**
     * 登录密码
     */
    @TableField("password_hash")
    private String passwordHash;

    /**
     * 取款密码
     */
    @TableField("password_coin")
    private String passwordCoin;

    /**
     * IP地址
     */
    @TableField("ip")
    private String ip;

    /**
     * 状态:10-正常 9-冻结 8-删除
     */
    @TableField("status")
    private Integer status;

    @TableField("created_at")
    private Integer createdAt;

    @TableField("updated_at")
    private Integer updatedAt;

    /**
     * 备注
     */
    @TableField("mark")
    private String mark;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public BigDecimal getFcoin() {
        return fcoin;
    }

    public void setFcoin(BigDecimal fcoin) {
        this.fcoin = fcoin;
    }

    public BigDecimal getCoinCommission() {
        return coinCommission;
    }

    public void setCoinCommission(BigDecimal coinCommission) {
        this.coinCommission = coinCommission;
    }

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Integer getIsPromoter() {
        return isPromoter;
    }

    public void setIsPromoter(Integer isPromoter) {
        this.isPromoter = isPromoter;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getBindBank() {
        return bindBank;
    }

    public void setBindBank(Integer bindBank) {
        this.bindBank = bindBank;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    public Integer getSupUid1() {
        return supUid1;
    }

    public void setSupUid1(Integer supUid1) {
        this.supUid1 = supUid1;
    }

    public String getSupUsername1() {
        return supUsername1;
    }

    public void setSupUsername1(String supUsername1) {
        this.supUsername1 = supUsername1;
    }

    public Integer getSupUid2() {
        return supUid2;
    }

    public void setSupUid2(Integer supUid2) {
        this.supUid2 = supUid2;
    }

    public Integer getSupUid3() {
        return supUid3;
    }

    public void setSupUid3(Integer supUid3) {
        this.supUid3 = supUid3;
    }

    public Integer getSupUid4() {
        return supUid4;
    }

    public void setSupUid4(Integer supUid4) {
        this.supUid4 = supUid4;
    }

    public Integer getSupUid5() {
        return supUid5;
    }

    public void setSupUid5(Integer supUid5) {
        this.supUid5 = supUid5;
    }

    public Integer getSupUid6() {
        return supUid6;
    }

    public void setSupUid6(Integer supUid6) {
        this.supUid6 = supUid6;
    }

    public Integer getSupUidTop() {
        return supUidTop;
    }

    public void setSupUidTop(Integer supUidTop) {
        this.supUidTop = supUidTop;
    }

    public String getSupUsernameTop() {
        return supUsernameTop;
    }

    public void setSupUsernameTop(String supUsernameTop) {
        this.supUsernameTop = supUsernameTop;
    }

    public Integer getSupLevelTop() {
        return supLevelTop;
    }

    public void setSupLevelTop(Integer supLevelTop) {
        this.supLevelTop = supLevelTop;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getPasswordCoin() {
        return passwordCoin;
    }

    public void setPasswordCoin(String passwordCoin) {
        this.passwordCoin = passwordCoin;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "User{" +
        "id=" + id +
        ", username=" + username +
        ", avatar=" + avatar +
        ", fcoin=" + fcoin +
        ", coinCommission=" + coinCommission +
        ", levelId=" + levelId +
        ", role=" + role +
        ", isPromoter=" + isPromoter +
        ", flag=" + flag +
        ", realName=" + realName +
        ", signature=" + signature +
        ", birthday=" + birthday +
        ", areaCode=" + areaCode +
        ", mobile=" + mobile +
        ", email=" + email +
        ", sex=" + sex +
        ", bindBank=" + bindBank +
        ", address=" + address +
        ", score=" + score +
        ", promoCode=" + promoCode +
        ", supUid1=" + supUid1 +
        ", supUsername1=" + supUsername1 +
        ", supUid2=" + supUid2 +
        ", supUid3=" + supUid3 +
        ", supUid4=" + supUid4 +
        ", supUid5=" + supUid5 +
        ", supUid6=" + supUid6 +
        ", supUidTop=" + supUidTop +
        ", supUsernameTop=" + supUsernameTop +
        ", supLevelTop=" + supLevelTop +
        ", passwordHash=" + passwordHash +
        ", passwordCoin=" + passwordCoin +
        ", ip=" + ip +
        ", status=" + status +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        ", mark=" + mark +
        "}";
    }
}
