package com.lt.win.service.base;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lt.win.utils.components.pagination.ReqPage;
import com.lt.win.utils.components.pagination.ResPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.util.EntityUtils;
import org.apache.poi.ss.formula.functions.T;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @Auther: wells
 * @Date: 2022/10/25 16:51
 * @Description:
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ESSqlSearchBase {
    private final RestHighLevelClient restHighLevelClient;

    /**
     * @return java.util.List<T>
     * @Description sql查询es返回List
     * @Param [sql, target]
     **/
    public <T> List<T> search(String sql, Supplier<T> target) {
        List<JSONObject> jsonObjects = searchJson(sql);
        return jsonObjects.stream().map(jsonObject ->
                (T) JSONObject.parseObject(jsonObject.toJSONString(), target.get().getClass()))
                .collect(Collectors.toList());
    }

    /**
     * @return T
     * @Description sql查询es返回List
     * @Param [sql, target]
     **/
    public <T> T searchObject(String sql, Supplier<T> target) {
        List<T> list = search(sql, target);
        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0);
        }
        return target.get();
    }


    /**
     * @return T
     * @Description sql查询es返回一个值
     * @Param [sql, tClass]
     **/
    public String searchValue(String sql) {
        List<JSONObject> list = searchJson(sql);
        if (CollectionUtils.isNotEmpty(list)) {
            Optional<Object> optional = list.get(0).values().stream().findFirst();
            if (optional.isPresent()) {
                return String.valueOf(optional.get());
            }
        }
        return null;
    }

    /**
     * @return T
     * @Description sql查询es返回JSONobject列表
     * @Param [sql, tClass]
     **/
    public List<JSONObject> searchJson(String sql) {
        List<JSONObject> list = new ArrayList<>();
        try {
            Request request = new Request("POST", "/_sql");
            request.setJsonEntity("{\"query\":\" " + sql + "\"}");
            Response response = restHighLevelClient.getLowLevelClient().performRequest(request);
            String responseBody = EntityUtils.toString(response.getEntity(), "utf-8");
            JSONObject reJson = JSONObject.parseObject(responseBody);
            JSONArray columnsJsonArr = reJson.getJSONArray("columns");
            JSONArray rowsJsonArr = reJson.getJSONArray("rows");
            if (CollectionUtils.isNotEmpty(rowsJsonArr)) {
                Object[] columnsArr = columnsJsonArr.toArray();
                Object[] rowsArr = rowsJsonArr.toArray();
                for (Object o : rowsArr) {
                    JSONArray valueArray = (JSONArray) o;
                    JSONObject rowJson = new JSONObject();
                    for (int j = 0; j < valueArray.size(); j++) {
                        rowJson.put(((JSONObject) columnsArr[j]).getString("name"), valueArray.get(j));
                    }
                    list.add(rowJson);
                }
            }
        } catch (Exception e) {
            log.error("esSql查询异常", e);
        }
        return list;
    }

    /**
     * @return T
     * @Description sql查询es返回分页数据
     * @Param [sql, tClass]
     **/
    public <T, K> ResPage<T> searchPage(String sql, Supplier<T> target, ReqPage<K> rePage) {
        String[] sortFieldArr = rePage.getSortField();
        if (sortFieldArr.length > 0) {
            sql += " order by ";
            StringBuilder fileSql = new StringBuilder();
            for (String s : sortFieldArr) {
                fileSql.append(" ").append(s);
            }
            sql += fileSql;
        }
        String sortKey = rePage.getSortKey();
        if (StringUtils.isNotEmpty(sortKey)) {
            sql += " " + sortKey;
        }
        long current = rePage.getCurrent();
        long size = rePage.getSize();
        long startPage = (current - 1) * size;
        long endPage = current * size;
        List<T> list = search(sql, target);
        ResPage<T> resPage = ResPage.get(rePage.getPage());
        int page = 0;
        if (CollectionUtils.isNotEmpty(list)) {
            int listSize = list.size();
            if (startPage < listSize) {
                endPage = endPage > listSize ? listSize : endPage;
                List<T> subList = list.subList((int) startPage, (int) endPage);
                resPage.setList(subList);
            }
            page = listSize % size > 0 ? (listSize / (int) size) + 1 : listSize / (int) size;
        }
        resPage.setTotal(list.size());
        resPage.setPages(page);
        return resPage;
    }

}
