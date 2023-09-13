package com.lt.win.service.io.constant;


/**
 * 数据库查询SQL语句统一定义类
 *
 * @author David
 * @since 2022-11-11
 */
public class ConstSql {
    /**
     * 首存查询
     */
    public static final String DEPOSIT_COIN_AND_COUNT = "ifNull(SUM(real_amount), 0) as coin, count(1) as count";

    /**
     * 数据库字段映射表
     */
    public static final String CREATED_AT = "created_at";
    public static final String UID = "uid";
    public static final String STATUS = "status";
    public static final String DEP_STATUS = "dep_status";
    public static final String COIN = "coin";
}

