package com.lt.win.function;

import com.google.common.collect.ArrayTable;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

/**
 * @author: wells
 * @date: 2020/8/7
 * @description:
 */

public class TableTest {
    @Test
    void testTable() {
        Table<String, Integer, BigDecimal> aTable = HashBasedTable.create();
        aTable.put("1", 1, BigDecimal.valueOf(0.01));
        aTable.put("2", 1, BigDecimal.valueOf(0.02));
        aTable.put("3", 1, BigDecimal.valueOf(0.03));
        aTable.put("4", 1, BigDecimal.valueOf(0.04));
        var value = aTable.get("1", "1");
        System.out.println("value=" + value);
        var rowMap = aTable.rowMap();
        var column = aTable.columnMap();
        Table<String, String, BigDecimal> bTable = HashBasedTable.create();

    }

}
