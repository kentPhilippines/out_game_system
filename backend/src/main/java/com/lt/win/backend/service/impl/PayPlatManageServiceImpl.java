package com.lt.win.backend.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lt.win.backend.io.dto.PayPlatParams;
import com.lt.win.backend.io.dto.PayPlatParams.*;
import com.lt.win.backend.service.PayPlatManageService;
import com.lt.win.dao.generator.po.CoinRate;
import com.lt.win.dao.generator.po.PayChannel;
import com.lt.win.dao.generator.po.PayPlatConfig;
import com.lt.win.dao.generator.service.CoinRateService;
import com.lt.win.dao.generator.service.PayChannelService;
import com.lt.win.dao.generator.service.PayPlatConfigService;
import com.lt.win.service.cache.KeyConstant;
import com.lt.win.utils.BeanConvertUtils;
import com.lt.win.utils.DateUtils;
import com.lt.win.utils.JedisUtil;
import com.lt.win.utils.components.pagination.ReqPage;
import com.lt.win.utils.components.pagination.ResPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static java.util.Objects.nonNull;

/**
 * @Auther: wells
 * @Date: 2022/8/29 02:44
 * @Description:
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PayPlatManageServiceImpl implements PayPlatManageService {
    private final PayPlatConfigService payPlatConfigServiceImpl;
    private final JedisUtil jedisUtil;
    private final CoinRateService coinRateServiceImpl;
    private final PayChannelService payChannelServiceImpl;

    @Override
    public ResPage<PayPlatListRes> payPlatList(ReqPage<PayPlatListReq> reqBody) {
        PayPlatListReq data = reqBody.getData();
        Page<PayPlatConfig> payPlatConfigList = payPlatConfigServiceImpl.lambdaQuery()
                .eq(nonNull(data.getPlatName()), PayPlatConfig::getPlatName, data.getPlatName())
                .eq(nonNull(data.getMerchantId()), PayPlatConfig::getMerchantId, data.getMerchantId())
                .eq(nonNull(data.getStatus()), PayPlatConfig::getStatus, data.getStatus())
                .page(reqBody.getPage());
        Page<PayPlatListRes> payPlatListResPage = BeanConvertUtils.copyPageProperties(payPlatConfigList, PayPlatListRes::new);
        return ResPage.get(payPlatListResPage);
    }


    @Override
    public Boolean updatePlatList(UpdatePayPlatReq reqBody) {
        Integer now = DateUtils.getCurrentTime();
        PayPlatConfig config = BeanConvertUtils.copyProperties(reqBody, PayPlatConfig::new);
        config.setUpdatedAt(now);
        boolean updateFlag = payPlatConfigServiceImpl.updateById(config);
        if (updateFlag) {
            payChannelServiceImpl.lambdaUpdate()
                    .set(PayChannel::getStatus, 0)
                    .eq(PayChannel::getPlatId, reqBody.getId())
                    .update();
            jedisUtil.del(KeyConstant.PAY_PLAT_CONFIG_LIST);
            jedisUtil.del(KeyConstant.PAY_CHANNEL_LIST_);
        }
        return updateFlag;
    }

    @Override
    public ResPage<CoinRateListRes> coinListList(ReqPage<PayPlatParams.CoinRateListReq> reqBody) {
        CoinRateListReq data = reqBody.getData();
        Page<CoinRate> page = coinRateServiceImpl.lambdaQuery()
                .eq(nonNull(data.getOriginalCurrency()), CoinRate::getOriginalCurrency, data.getOriginalCurrency())
                .eq(nonNull(data.getTransferCurrency()), CoinRate::getTransferCurrency, data.getTransferCurrency())
                .orderByAsc(CoinRate::getCreatedAt)
                .page(reqBody.getPage());
        Page<CoinRateListRes> coinRateListResPage = BeanConvertUtils.copyPageProperties(page, CoinRateListRes::new);
        return ResPage.get(coinRateListResPage);
    }

    @Override
    public Boolean addCoinRate(AddCoinRateReq reqBody) {
        Integer now = DateUtils.getCurrentTime();
        CoinRate coinRate = BeanConvertUtils.copyProperties(reqBody, CoinRate::new);
        coinRate.setCreatedAt(now);
        coinRate.setUpdatedAt(now);
        boolean reFlag = coinRateServiceImpl.save(coinRate);
        if (reFlag) {
            jedisUtil.del(KeyConstant.COIN_RATE_HASH);
        }
        return reFlag;
    }

    @Override
    public Boolean updateCoinRate(UpdateCoinRateReq reqBody) {
        Integer now = DateUtils.getCurrentTime();
        CoinRate coinRate = BeanConvertUtils.copyProperties(reqBody, CoinRate::new);
        coinRate.setUpdatedAt(now);
        boolean reFlag = coinRateServiceImpl.updateById(coinRate);
        if (reFlag) {
            jedisUtil.del(KeyConstant.COIN_RATE_HASH);
        }
        return reFlag;
    }

    @Override
    public Boolean deleteCoinRate(DeleteCoinRateReq reqBody) {
        boolean reFlag = coinRateServiceImpl.removeById(reqBody.getId());
        if (reFlag) {
            jedisUtil.del(KeyConstant.COIN_RATE_HASH);
        }
        return reFlag;
    }

    @Override
    public ResPage<PayChannelListRes> payChannelList(ReqPage<PayChannelListReq> reqBody) {
        PayChannelListReq data = reqBody.getData();
        Page<PayChannel> payChannelPage = payChannelServiceImpl.lambdaQuery()
                .eq(nonNull(data.getPlatName()), PayChannel::getPlatName, data.getPlatName())
                .eq(nonNull(data.getCategory()), PayChannel::getCategory, data.getCategory())
                .eq(nonNull(data.getStatus()), PayChannel::getStatus, data.getStatus())
                .page(reqBody.getPage());
        Page<PayChannelListRes> page = BeanConvertUtils.copyPageProperties(payChannelPage, PayChannelListRes::new);
        return ResPage.get(page);
    }

    @Override
    public Boolean updateChannel(UpdateChannelReq reqBody) {
        Integer now = DateUtils.getCurrentTime();
        PayChannel payChannel = BeanConvertUtils.copyProperties(reqBody, PayChannel::new);
        payChannel.setUpdatedAt(now);
        boolean updateFlag = payChannelServiceImpl.updateById(payChannel);
        if (updateFlag) {
            jedisUtil.del(KeyConstant.PAY_CHANNEL_LIST_);
        }
        return updateFlag;
    }
}
