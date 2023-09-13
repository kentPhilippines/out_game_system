package com.lt.win.backend.base;

import com.lt.win.backend.io.bo.ComprehensiveChart;
import com.lt.win.utils.DateNewUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @description: 报表统计通用类
 * @author: andy
 * @date: 2020/8/24
 */
@Component
public class ReportCommon {

    /**
     * 计算年月日日期范围:按天统计数量
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return List<ComprehensiveChart.ListChart> name = 年月日 count=默认0
     */
    public List<ComprehensiveChart.ListChart> rangeDayListWithCount(int startTime, int endTime) {
        List<ComprehensiveChart.ListChart> list = new ArrayList<>();
        List<String> daysRangeList = DateNewUtils.daysRangeList(startTime, endTime, DateNewUtils.Format.yyyy_MM_dd);
        daysRangeList.forEach(s -> list.add(ComprehensiveChart.ListChart.builder().count(0).name(s).build()));
        return list;
    }

    /**
     * 计算年月日日期范围:按天统计金额
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return List<ComprehensiveChart.ListCoinChart> name = 年月日 coin=默认0.00
     */
    public List<ComprehensiveChart.ListCoinChart> daysRangeListWithCoin(int startTime, int endTime) {
        List<ComprehensiveChart.ListCoinChart> list = new ArrayList<>();
        List<String> daysRangeList = DateNewUtils.daysRangeList(startTime, endTime, DateNewUtils.Format.yyyy_MM_dd);
        daysRangeList.forEach(s -> list.add(ComprehensiveChart.ListCoinChart.builder().coin(BigDecimal.ZERO).name(s).build()));
        return list;
    }

    /**
     * 填充数据:按name属性的Value填充count的value
     *
     * @param allList     全部年月日列表
     * @param realDataMap 真实年月日数据列表
     */
    public void fullCountList(List<ComprehensiveChart.ListChart> allList, Map<String, Integer> realDataMap) {
        if (Optional.ofNullable(realDataMap).isEmpty() || realDataMap.isEmpty()) {
            return;
        }
        allList.forEach(o -> {
            String name = o.getName();
            if (realDataMap.containsKey(name)) {
                o.setCount(realDataMap.get(name));
            }
        });
    }

    /**
     * 填充数据:按name属性的value填充coin的value
     *
     * @param allList     全部年月日列表
     * @param realDataMap 真实年月日数据列表
     */
    public void fullCoinList(List<ComprehensiveChart.ListCoinChart> allList, Map<String, BigDecimal> realDataMap) {
        if (Optional.ofNullable(realDataMap).isEmpty() || realDataMap.isEmpty()) {
            return;
        }
        allList.forEach(o -> {
            String name = o.getName();
            if (realDataMap.containsKey(name)) {
                o.setCoin(realDataMap.get(name));
            }
        });
    }
}
