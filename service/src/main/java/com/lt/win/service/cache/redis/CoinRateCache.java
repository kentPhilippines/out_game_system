package com.lt.win.service.cache.redis;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lt.win.dao.generator.po.CoinRate;
import com.lt.win.dao.generator.po.PayPlatConfig;
import com.lt.win.dao.generator.service.CoinRateService;
import com.lt.win.service.cache.KeyConstant;
import com.lt.win.service.exception.BusinessException;
import com.lt.win.utils.JedisUtil;
import com.lt.win.utils.components.response.CodeInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.lt.win.service.common.Constant.PLAT_CONFIG_ENABLE;

/**
 * @Auther: wells
 * @Date: 2022/9/10 00:45
 * @Description:
 */
@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CoinRateCache {
    private final JedisUtil jedisUtil;
    private final CoinRateService coinRateServiceImpl;
    private final ConfigCache configCache;

    /**
     * @return java.math.BigDecimal
     * @Description 获取转换汇率
     * @Param [originalCurrency, transferCurrency]
     **/
    public BigDecimal getCoinRate(String originalCurrency, String transferCurrency) {
        BigDecimal rate = BigDecimal.ZERO;
        String data = jedisUtil.hget(KeyConstant.COIN_RATE_HASH, originalCurrency + "_" + transferCurrency);
        if (Strings.isNotEmpty(data)) {
            CoinRate coinRate = JSONObject.parseObject(data, CoinRate.class);
            rate = coinRate.getRate();
        } else {
            CoinRate coinRate = coinRateServiceImpl.lambdaQuery()
                    .eq(CoinRate::getOriginalCurrency, originalCurrency)
                    .eq(CoinRate::getTransferCurrency, transferCurrency)
                    .eq(CoinRate::getStatus, 1)
                    .one();
            if (Objects.isNull(coinRate)) {
                throw new BusinessException(CodeInfo.COIN_RATE_EXCEPTION);
            }
            rate = coinRate.getRate();
            jedisUtil.hset(KeyConstant.COIN_RATE_HASH,
                    originalCurrency + "_" + transferCurrency,
                    JSONObject.toJSONString(coinRate));
        }
        return rate;
    }

    /**
     * @return java.math.BigDecimal
     * @Description 货币汇率
     * @Param [originalCurrency]
     **/
    public BigDecimal getCoinRate(String originalCurrency) {
        String mainCurrency = configCache.getCurrency();
        if (originalCurrency.equals(mainCurrency)) {
            return BigDecimal.ONE;
        }
        return getCoinRate(originalCurrency, mainCurrency);
    }
}
