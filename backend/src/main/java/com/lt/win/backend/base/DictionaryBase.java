package com.lt.win.backend.base;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.google.common.base.CaseFormat;
import com.lt.win.service.cache.KeyConstant;
import com.lt.win.service.cache.redis.DictionaryCache;
import com.lt.win.service.io.bo.DictionaryBo;
import com.lt.win.service.io.dto.Dictionary;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static com.lt.win.service.common.Constant.GAME_ID_FIELD;
import static com.lt.win.service.io.constant.ConstData.BET_PREFIX;
import static com.lt.win.service.io.constant.ConstData.DIC_PREFIX;

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
                .isShowList(List.of(0, 2))
                .category(category)
                // 查询backend(后台)字典
                .dictHashKey(KeyConstant.DICTIONARY_BACKEND_HASH)
                .build();
    }


    /**
     * 获取游戏名称列表
     *
     * @param model model
     * @return GameName
     */
    public Map<String, String> getDictItemMapByModel(String model) {
        Map<String, String> map = null;
        if (StringUtils.isNotBlank(model)) {
            var json = JSON.parseObject(model);
            if (!json.isEmpty()) {
                var gameIdField = json.getString(GAME_ID_FIELD);
                var businessName = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, json.getString(BET_PREFIX));
                var dicKey = DIC_PREFIX + "_" + BET_PREFIX + "_" + businessName + "_" + gameIdField;
                map = getCategoryMap(dicKey);
            }
        }
        return map;
    }
}
