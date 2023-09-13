package com.lt.win.apiend.base;

import com.lt.win.service.cache.KeyConstant;
import com.lt.win.service.cache.redis.DictionaryCache;
import com.lt.win.service.io.bo.DictionaryBo;
import com.lt.win.service.io.dto.Dictionary;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @description:查询字典
 * @author: andy
 * @date: 2020/9/17
 */
@Component
public class DictionaryBase {
    @Resource
    private DictionaryCache dictionaryCache;

    /**
     * 查询字典
     * <p>
     * 描述:当category为null时查询所有
     *
     * @param category 字典类型
     * @return map集合
     */
    public Map<String, List<Dictionary.ResDto>> getDictionary(String category) {
        return dictionaryCache.listDictionary(buildDictionaryBo(category));
    }

    /**
     * 获取win_dict_item表的一条记录
     *
     * @param category win_dict表的category
     * @return win_dict_item表的一条记录
     */
    public Map<String, String> getCategoryMap(String category) {
        return dictionaryCache.getCategoryMap(buildDictionaryBo(category));
    }

    /**
     * 通用请求对象
     *
     * @param category 字典类型
     * @return DictionaryBo
     */
    private DictionaryBo buildDictionaryBo(String category) {
        return DictionaryBo.builder()
                // 类型: 0-全部 1-前端 2-后台
                .isShowList(List.of(0, 1))
                .category(category)
                // 查询API字典
                .dictHashKey(KeyConstant.DICTIONARY_API_HASH)
                .build();
    }
}
