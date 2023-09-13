package com.lt.win.service.cache.redis;

import com.alibaba.fastjson.JSON;
import com.lt.win.dao.generator.po.Promotions;
import com.lt.win.dao.generator.service.PromotionsService;
import com.lt.win.service.cache.KeyConstant;
import com.lt.win.service.exception.BusinessException;
import com.lt.win.service.io.bo.PromotionsBo;
import com.lt.win.service.io.dto.BaseParams;
import com.lt.win.service.io.enums.LangEnum;
import com.lt.win.service.thread.ThreadHeaderLocalData;
import com.lt.win.utils.JedisUtil;
import com.lt.win.utils.components.response.CodeInfo;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.alibaba.fastjson.JSON.parseObject;

/**
 * @author: wells
 * @date: 2020/8/14
 * @description:
 */
@Component
public class PromotionsCache {
    @Autowired
    private PromotionsService promotionsServiceImpl;
    @Autowired
    private JedisUtil jedisUtil;
    @Autowired
    private ConfigCache configCache;

    /**
     * 获取活动列表
     */
    public Promotions getPromotionsCache(Integer promotionId) {
        var promotionsStr = jedisUtil.hget(KeyConstant.PROMOTIONS_HASH, String.valueOf(promotionId));
        var promotions = new Promotions();
        if (Objects.isNull(promotionsStr)) {
            List<Promotions> promotionsList = promotionsServiceImpl.lambdaQuery()
                    .eq(Promotions::getStatus, 1)
                    .list();
            if (!CollectionUtils.isEmpty(promotionsList)) {
                for (Promotions obj : promotionsList) {
                    if (promotionId != null && promotionId.equals(obj.getId())) {
                        promotions = obj;
                    }
                    jedisUtil.hset(KeyConstant.PROMOTIONS_HASH, String.valueOf(obj.getId()), JSON.toJSONString(obj));
                }
            }
        } else {
            promotions = parseObject(promotionsStr, Promotions.class);
        }
        BaseParams.HeaderInfo currentLoginUser = ThreadHeaderLocalData.HEADER_INFO_THREAD_LOCAL.get();
        var lang = Objects.nonNull(currentLoginUser) ? currentLoginUser.getLang() : LangEnum.EN.getValue();
        lang = !StringUtils.isEmpty(lang) ? lang : Optional.ofNullable(configCache.getConfigByTitle("lang")).orElse("").split(",")[0];
        promotions.setCodeZh(Optional.ofNullable(parseObject(promotions.getCodeZh()).getString(lang)).orElse(""));
        promotions.setImg(Optional.ofNullable(parseObject(promotions.getImg()).getString(lang)).orElse(""));
        promotions.setDescript(Optional.ofNullable(parseObject(promotions.getDescript()).getString(lang)).orElse(""));
        return promotions;
    }

    /**
     * 获取活动列表
     */
    public List<Promotions> getPromotionsListCache() {
        var currentLoginUser = ThreadHeaderLocalData.HEADER_INFO_THREAD_LOCAL.get();
        var finalLang = Objects.nonNull(currentLoginUser) ? currentLoginUser.getLang() : LangEnum.EN.getValue();

        var value = jedisUtil.hget(KeyConstant.PROMOTIONS_HASH, finalLang);
        if (Optional.ofNullable(value).isPresent()) {
            return JSON.parseArray(value, Promotions.class);
        }

        var list = promotionsServiceImpl.lambdaQuery()
                .eq(Promotions::getStatus, 1)
                .orderByDesc(Promotions::getSort, Promotions::getId)
                .list()
                .stream()
                .peek(promotion -> {
                    promotion.setCodeZh(Optional.ofNullable(parseObject(promotion.getCodeZh()).getString(finalLang)).orElse(""));
                    promotion.setImg(Optional.ofNullable(parseObject(promotion.getImg()).getString(finalLang)).orElse(""));
                    var description = Optional.ofNullable(parseObject(promotion.getDescript()).getString(finalLang)).orElse("");
                    // 全局替换货币符号
                    description = description.replace("{currency}", configCache.getCurrency());
                    var info = JSON.parseObject(promotion.getInfo());
                    // 快乐周五特殊处理
                    if (promotion.getId() == 4) {
                        var weekdayNum = info.toJavaObject(PromotionsBo.Config.class).getWeekdays();
                        var weekday = EnumUtils.getEnum(PromotionsBo.WeekDay.class, finalLang.toUpperCase() + "_" + weekdayNum);
                        description = description.replace("{weekdays}", weekday.getValue());
                    }

                    for (Map.Entry<String, Object> entry : info.entrySet()) {
                        var configValue = "rate".equalsIgnoreCase(entry.getKey()) ?
                                new BigDecimal(entry.getValue().toString()).multiply(new BigDecimal(100)).intValue() + "" : entry.getValue().toString();
                        description = description.replace("{" + entry.getKey() + "}", configValue);
                    }
                    promotion.setDescript(description);
                })
                .collect(Collectors.toList());

        jedisUtil.hset(KeyConstant.PROMOTIONS_HASH, finalLang, JSON.toJSONString(list));
        return list;
    }

    /**
     * 刷新活动列表
     */
    public void refreshPromotions() {
        jedisUtil.del(KeyConstant.PROMOTIONS_HASH);
        getPromotionsListCache();
    }

    /**
     * 根据活动ID获取活动配置信息
     */
    public PromotionsBo.Config getPromotionsConfig(Integer id) {
        var config = jedisUtil.hget(KeyConstant.PROMOTIONS_HASH, KeyConstant.PROMOTIONS_HASH_CONFIG);

        if (Optional.ofNullable(config).isPresent()) {
            return JSON.parseObject(config).toJavaObject(PromotionsBo.Config.class);
        }

        var promotion = promotionsServiceImpl.getById(id);
        if (Optional.ofNullable(promotion).isEmpty()) {
            throw new BusinessException(CodeInfo.ACTIVE_APPLY_ID_INVALID);
        }

        return JSON.parseObject(promotion.getInfo()).toJavaObject(PromotionsBo.Config.class);
    }
}
