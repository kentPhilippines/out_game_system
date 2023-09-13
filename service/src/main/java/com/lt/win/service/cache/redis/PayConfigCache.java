package com.lt.win.service.cache.redis;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.lt.win.dao.generator.po.PayChannel;
import com.lt.win.dao.generator.po.PayPlatConfig;
import com.lt.win.dao.generator.service.PayChannelService;
import com.lt.win.dao.generator.service.PayPlatConfigService;
import com.lt.win.service.cache.KeyConstant;
import com.lt.win.service.exception.BusinessException;
import com.lt.win.utils.JedisUtil;
import com.lt.win.utils.components.response.CodeInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.lt.win.service.common.Constant.CHANNEL_OUT;
import static com.lt.win.service.common.Constant.PLAT_CONFIG_ENABLE;

/**
 * @Auther: wells
 * @Date: 2022/7/30 17:31
 * @Description:
 */
@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PayConfigCache {
    private final JedisUtil jedisUtil;
    private final PayPlatConfigService payPlatConfigServiceImpl;
    private final PayChannelService payChannelServiceImpl;


    /**
     * @return com.lt.win.dao.generator.po.PayPlatConfig
     * @Description 获取支付平台配置
     * @Param []
     **/
    public List<PayPlatConfig> getPayPlatConfigList() {
        String data = jedisUtil.get(KeyConstant.PAY_PLAT_CONFIG_LIST);
        if (StringUtils.isNotBlank(data)) {
            return JSON.parseArray(data, PayPlatConfig.class);
        }
        List<PayPlatConfig> payPlatConfigList = payPlatConfigServiceImpl.lambdaQuery()
                .list();
        if (CollectionUtil.isNotEmpty(payPlatConfigList)) {
            jedisUtil.set(KeyConstant.PAY_PLAT_CONFIG_LIST, JSON.toJSONString(payPlatConfigList));
        }
        return payPlatConfigList;
    }

    public List<PayPlatConfig> getFilterPayPlatConfigList() {
        return getPayPlatConfigList().stream().filter(payPlatConfig -> payPlatConfig.getStatus().equals(PLAT_CONFIG_ENABLE))
                .collect(Collectors.toList());
    }

    /**
     * @return java.util.List<com.lt.win.dao.generator.po.PayChannel>
     * @Description 获取通道列表
     * @Param []
     **/
    public List<PayChannel> getPayChannelList() {
        String data = jedisUtil.get(KeyConstant.PAY_CHANNEL_LIST_);
        if (StringUtils.isNotBlank(data)) {
            return JSON.parseArray(data, PayChannel.class);
        }
        List<PayChannel> payChannelList = payChannelServiceImpl.lambdaQuery().list();
        if (CollectionUtil.isNotEmpty(payChannelList)) {
            jedisUtil.set(KeyConstant.PAY_CHANNEL_LIST_, JSON.toJSONString(payChannelList));
        }
        return payChannelList;
    }


    public List<PayChannel> getFilterPayChannelList() {
        return getPayChannelList().stream().filter(payChannel -> payChannel.getStatus().equals(PLAT_CONFIG_ENABLE))
                .collect(Collectors.toList());
    }

    public PayChannel getNoFilterPayChannel(String code, Integer category) {
        List<PayChannel> payChannelList = getPayChannelList();
        Optional<PayChannel> payChannelOptional = payChannelList.stream().
                filter(channel -> channel.getCode().equals(code) && channel.getCategory().equals(category))
                .findFirst();
        return payChannelOptional.orElse(new PayChannel());
    }

    /**
     * @return com.lt.win.dao.generator.po.PayChannel
     * @Description 获取某个通道
     * @Param [code, category]
     **/
    public PayChannel getPayChannel(String code, Integer category) {
        List<PayChannel> payChannelList = getFilterPayChannelList();
        Optional<PayChannel> payChannelOptional = payChannelList.stream().
                filter(channel -> channel.getCode().equals(code) && channel.getCategory().equals(category))
                .findFirst();
        if (payChannelOptional.isEmpty()) {
            throw new BusinessException(CodeInfo.PAY_CHANNEL_IS_CLOSE);
        }
        return payChannelOptional.get();
    }

    /**
     * @return java.util.List<com.lt.win.dao.generator.po.PayChannel>
     * @Description 获取代付或代付通道
     * @Param [category]
     **/
    public List<PayChannel> getPayChannelByCategory(Integer category) {
        List<PayChannel> payChannelList = getFilterPayChannelList();
        return payChannelList.stream().
                filter(channel -> channel.getCategory().equals(category))
                .collect(Collectors.toList());
    }

    /**
     * @return java.util.List<com.lt.win.dao.generator.po.PayPlatConfig>
     * @Description 通过平台code获取平台
     * @Param [code]
     **/
    public PayPlatConfig getPayPlatConfig(Integer platId) {
        List<PayPlatConfig> payPlatConfigList = getFilterPayPlatConfigList();
        Optional<PayPlatConfig> payPlatConfigOptional = payPlatConfigList.stream().filter(payPlatConfig -> payPlatConfig.getId().equals(platId))
                .findFirst();
        if (payPlatConfigOptional.isEmpty()) {
            throw new BusinessException(CodeInfo.PAY_PLAT_IS_CLOSE);
        }
        return payPlatConfigOptional.get();

    }

    /**
     * @return java.lang.String
     * @Description 提款时获取转账类型的币种
     * @Param [categoryCurrency, categoryTransfer]
     **/
    public String getWithdrawalCurrency(Integer categoryCurrency, Integer categoryTransfer) {
        var channelList = getPayChannelList().stream().filter(payChannel ->
                payChannel.getCategoryCurrency().equals(categoryCurrency) &&
                        payChannel.getCategoryTransfer().equals(categoryTransfer)
        ).collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(channelList)) {
            return channelList.get(0).getCurrency();
        }
        return null;
    }

    /***
     * @Description 获取提款通道通过转账类型币种
     * @Param [categoryCurrency]
     * @return java.util.List<com.lt.win.dao.generator.po.PayChannel>
     **/
    public List<PayChannel> getWithdrawalChannelList(Integer categoryTransfer) {
        return getPayChannelByCategory(CHANNEL_OUT).stream()
                .filter(payChannel -> payChannel.getCategoryTransfer()
                        .equals(categoryTransfer)).collect(Collectors.toList());
    }
}
