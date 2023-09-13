package com.lt.win.backend.configuration.tenant;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author : Wells
 * @Date : 2020-11-04 12:28 上午
 * @Description : xx
 */
@Data
@ConfigurationProperties(prefix = "com.lt.win.backend.configuration")
public class TenantProperties {
    /**
     * 是否开启租户模式
     */
    private Boolean enable = true;

    /**
     * 需要排除的多租户的表
     */
    private List<String> ignoreTables = Arrays.asList(
            "win_auth_group_access",
            "TABLES",
            "COLUMNS"
    );
    /**
     * 多租户字段名称
     */
    private String column = "uid";
    private String columnExt = "xb_uid";
    private String columnUser = "id";

    /**
     * 排除不进行租户隔离的sql
     * 样例全路径：vip.mate.mapper.UserMapper.findList
     */
    private List<String> ignoreSqls = new ArrayList<>();
}
