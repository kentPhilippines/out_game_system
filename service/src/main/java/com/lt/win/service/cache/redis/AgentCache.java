package com.lt.win.service.cache.redis;

import com.lt.win.dao.generator.po.AgentCommissionRate;
import com.lt.win.dao.generator.service.AgentCommissionRateService;
import com.lt.win.service.cache.KeyConstant;
import com.lt.win.utils.JedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;


/**
 * 代理相关缓存
 *
 * @author David
 * @since 2022/10/29
 */
@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AgentCache {
    private final JedisUtil jedisUtil;
    private final AgentCommissionRateService agentCommissionRateServiceImpl;

    /**
     * 根据代理等级获取代理佣金
     *
     * @param levelId 代理等级
     * @return 费率
     */
    public BigDecimal commission(Integer levelId) {
        String data = jedisUtil.hget(KeyConstant.AGENT_COMMISSION_RATE, levelId.toString());
        if (StringUtils.isNotBlank(data)) {
            return new BigDecimal(data);
        }

        var commissionRate = agentCommissionRateServiceImpl.lambdaQuery()
                .eq(AgentCommissionRate::getAgentLevel, levelId)
                .one();
        if (Optional.ofNullable(commissionRate).isPresent()) {
            jedisUtil.hset(KeyConstant.AGENT_COMMISSION_RATE, levelId.toString(), commissionRate.getAgentLevelRate().toString());
            return commissionRate.getAgentLevelRate();
        }
        return BigDecimal.ZERO;
    }
}
