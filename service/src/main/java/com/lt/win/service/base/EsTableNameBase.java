package com.lt.win.service.base;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.zxp.esclientrhl.annotation.ESMetaData;

import javax.annotation.PostConstruct;

/**
 * @Auther: wells
 * @Date: 2022/11/8 22:49
 * @Description:
 */
@Slf4j
@Component
public class EsTableNameBase {
    @Autowired
    Environment environment;


    private static String indexSuffix;

    @PostConstruct
    public void setIndexSuffix() {
        indexSuffix = environment.getProperty("elasticsearch.index.suffix");
    }

    /**
     * @return java.lang.String
     * @Description 获取es的索引名
     * @Param [tClass]
     **/
    public static String getTableName(Class<?> tClass) {
        ESMetaData esMetaData = tClass.getAnnotation(ESMetaData.class);
        boolean suffix = esMetaData.suffix();
        String indexName = esMetaData.indexName();
        if (Strings.isNotEmpty(indexSuffix)) {
            indexName = suffix ? indexName + "_" + indexSuffix : indexName;
        }
        return indexName;
    }
}
