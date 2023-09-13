package com.lt.win.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * @author ：wells
 * @version : 1.0.0
 * @date ：Created in 2019/11/18 13:27
 * @description :
 */
public class DataTransformaUtils {

    /**
     * list转换为JSONArray格式
     *
     * @param list
     * @return
     */

    public static JSONArray transformaJA(List<Map<String, Object>> list) {
        JSONArray ja = new JSONArray();
        if (list != null && !list.isEmpty()) {
            list.forEach(map -> {
                map.forEach((key, value) -> {
                    if (value == null) {
                        map.put(key, "");
                    }
                    if (value instanceof BigDecimal) {
                        map.put(key, tranBigDecimal((BigDecimal) value));
                    }
                });
            });
            ja = (JSONArray) JSONArray.parse(JSON.toJSONString(list, SerializerFeature.WriteMapNullValue, SerializerFeature.PrettyFormat));

        }
        return ja;
    }


    /**
     * map转换为JSONObject格式
     *
     * @param map
     * @return
     */

    public static JSONObject transformaJO(Map<String, Object> map) {
        JSONObject jo = new JSONObject();
        if (map != null && map.size() > 0) {
            map.forEach((key, value) -> {
                if (value == null) {
                    map.put(key, "");
                }
                if (value instanceof BigDecimal) {
                    map.put(key, tranBigDecimal((BigDecimal) value));
                }
            });
            jo = (JSONObject) JSONObject.parse(JSON.toJSONString(map));
        }
        return jo;
    }


    public static BigDecimal tranBigDecimal(BigDecimal bigDecimal) {
        DecimalFormat decimalFormat = new DecimalFormat(".00");//保留两位小数，并用都好隔开
        decimalFormat.setRoundingMode(RoundingMode.DOWN);
        return new BigDecimal(decimalFormat.format(bigDecimal));
    }

    public static BigDecimal tranBigDecimalFour(BigDecimal bigDecimal) {
        DecimalFormat decimalFormat = new DecimalFormat(".0000");//保留四位小数，并用都好隔开
        decimalFormat.setRoundingMode(RoundingMode.DOWN);
        return new BigDecimal(decimalFormat.format(bigDecimal));
    }

    public static BigDecimal objectTranBigDecimal(Object object) {
        if (object instanceof BigDecimal) {
            return tranBigDecimal((BigDecimal) object);
        } else if (object instanceof Double) {
            return tranBigDecimal(new BigDecimal((Double) object));
        }
        return new BigDecimal(0);
    }

    /**
     * 分页转换
     *
     * @param parMap
     * @param ja
     * @return
     */
    public static JSONObject jAtransformaPage(Map<String, Object> parMap, JSONArray ja) {
        String sortKey = parMap.getOrDefault("sortKey", "").toString();
        if (StringUtils.isNotEmpty(sortKey)) {
            String[] arr = sortKey.split(" ");
            if (arr.length >= 2) {
                String key = arr[0].trim();
                String order = arr[1].trim();
                if (order.toLowerCase().indexOf("desc") == 0) {
                    if (ja != null && !ja.isEmpty() && ((JSONObject) ja.get(0)).get(key) instanceof BigDecimal) {
                        ja.sort(Comparator.comparing(obj -> new BigDecimal(((JSONObject) obj).get(key).toString())).reversed());
                    } else if (ja != null && !ja.isEmpty() && ((JSONObject) ja.get(0)).get(key) instanceof Integer) {
                        ja.sort(Comparator.comparing(obj -> new Integer(((JSONObject) obj).get(key).toString())).reversed());
                    } else {
                        ja.sort(Comparator.comparing(obj -> ((JSONObject) obj).get(key).toString()).reversed());
                    }
                } else if (order.toLowerCase().indexOf("asc") == 0) {
                    if (ja != null && !ja.isEmpty() && ((JSONObject) ja.get(0)).get(key) instanceof BigDecimal) {
                        ja.sort(Comparator.comparing(obj -> new BigDecimal(((JSONObject) obj).get(key).toString())));
                    } else if (ja != null && !ja.isEmpty() && ((JSONObject) ja.get(0)).get(key) instanceof Integer) {
                        ja.sort(Comparator.comparing(obj -> new Integer(((JSONObject) obj).get(key).toString())));
                    } else {
                        ja.sort(Comparator.comparing(obj -> ((JSONObject) obj).get(key).toString()));
                    }
                }
            }
        }

        JSONObject reJO = new JSONObject();
        String pageNum = parMap.getOrDefault("pageNum", "1").toString();
        String pageSize = parMap.getOrDefault("pageSize", "20").toString();
        if (StringUtils.isNotEmpty(pageNum) && StringUtils.isNotEmpty(pageSize)) {
            int pageNumInt = Integer.valueOf(pageNum);
            int pageSizeInt = Integer.valueOf(pageSize);
            int startPageSize = (pageNumInt - 1) * pageSizeInt;
            int endPageSize = pageNumInt * pageSizeInt;
            if (endPageSize > ja.size()) {
                endPageSize = ja.size();
            }
            reJO.put("total", ja.size());
            JSONArray newJA = new JSONArray();
            for (int i = startPageSize; i < endPageSize; i++) {
                newJA.add(ja.get(i));
            }
            reJO.put("data", newJA);
        }
        return reJO;
    }
}