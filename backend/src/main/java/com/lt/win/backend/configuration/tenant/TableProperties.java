package com.lt.win.backend.configuration.tenant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author : Wells
 * @Date : 2020-11-10 4:19 下午
 * @Description : xx
 */
@Component
public class TableProperties {
    @Autowired
    TableDao tableDao;
    private static final String USER_TABLE_NAME = "win_user";
    public static final Map<String, String> NO_IGNORE_TABLE = new HashMap<>();

    @PostConstruct
    public Map<String, String> getIgnoreTable() {
        var list = tableDao.listTable();
        for (var map : list) {
            var tableName = map.get("TABLE_NAME").toString();
            var columnList = tableDao.listTableColumn(tableName);
            if (USER_TABLE_NAME.equals(tableName)) {
                NO_IGNORE_TABLE.put(tableName, new TenantProperties().getColumnUser());
                continue;
            }
            for (var columnMap : columnList) {
                var columnName = columnMap.get("COLUMN_NAME");
                if (columnName.equals(new TenantProperties().getColumn())) {
                    NO_IGNORE_TABLE.put(tableName, new TenantProperties().getColumn());
                    break;
                } else if (columnName.equals(new TenantProperties().getColumnExt())) {
                    NO_IGNORE_TABLE.put(tableName, new TenantProperties().getColumnExt());
                    break;
                }
            }
        }
        return NO_IGNORE_TABLE;
    }
}
