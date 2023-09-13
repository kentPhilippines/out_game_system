package com.lt.win.service.cache.redis;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.lt.win.dao.generator.po.WithdrawalAddress;
import com.lt.win.dao.generator.service.WithdrawalAddressService;
import com.lt.win.service.cache.KeyConstant;
import com.lt.win.service.common.Constant;
import com.lt.win.utils.JedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Auther: wells
 * @Date: 2022/7/30 18:48
 * @Description:
 */
@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WithdrawalAddressCache {
    private final JedisUtil jedisUtil;
    private final WithdrawalAddressService withdrawalAddressServiceImpl;

    /**
     * @Description 获取用户提款地址
     * @Param [uid, payCode]
     * @return java.util.List<com.lt.win.dao.generator.po.WithdrawalAddress>
     **/
    public List<WithdrawalAddress> getWithdrawalAddressList(String uid) {
        String data = jedisUtil.hget(KeyConstant.WITHDRAWAL_ADDRESS_HASH, uid);
        if (!Strings.isEmpty(data)) {
            return JSON.parseArray(data, WithdrawalAddress.class);
        }
        List<WithdrawalAddress> list = withdrawalAddressServiceImpl.lambdaQuery()
                .in(WithdrawalAddress::getStatus, Constant.WITHDRAWAL_ADDRESS_STATUS_DEFAULT, Constant.WITHDRAWAL_ADDRESS_STATUS_NORMAL)
                .eq(WithdrawalAddress::getUid, uid)
                .orderByAsc(WithdrawalAddress::getStatus)
                .list();
        if (!CollectionUtil.isNotEmpty(list)) {
            jedisUtil.hset(KeyConstant.WITHDRAWAL_ADDRESS_HASH, uid, JSON.toJSONString(list));
        }
        return list;
    }
}
