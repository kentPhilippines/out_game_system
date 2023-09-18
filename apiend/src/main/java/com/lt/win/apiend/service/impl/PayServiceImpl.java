package com.lt.win.apiend.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lt.win.apiend.base.DictionaryBase;
import com.lt.win.apiend.io.dto.wallet.WalletParams.*;
import com.lt.win.apiend.service.PayService;
import com.lt.win.dao.generator.po.*;
import com.lt.win.dao.generator.service.*;
import com.lt.win.service.base.AuditCoinBase;
import com.lt.win.service.base.NoticeBase;
import com.lt.win.service.base.UserCoinBase;
import com.lt.win.service.cache.KeyConstant;
import com.lt.win.service.cache.redis.*;
import com.lt.win.service.common.Constant;
import com.lt.win.service.exception.BusinessException;
import com.lt.win.service.io.dto.CoinLog;
import com.lt.win.service.io.dto.BaseParams;
import com.lt.win.service.io.dto.UserCoinChangeParams;
import com.lt.win.service.io.enums.CategoryCurrencyEnum;
import com.lt.win.service.thread.ThreadHeaderLocalData;
import com.lt.win.utils.*;
import com.lt.win.utils.components.pagination.ReqPage;
import com.lt.win.utils.components.pagination.ResPage;
import com.lt.win.utils.components.response.CodeInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.logging.log4j.util.Strings;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
//import org.zxp.esclientrhl.repository.ElasticsearchTemplate;
import org.zxp.esclientrhl.repository.PageList;
import org.zxp.esclientrhl.repository.PageSortHighLight;
import org.zxp.esclientrhl.repository.Sort;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.alibaba.fastjson.JSON.parseObject;
import static com.lt.win.service.cache.KeyConstant.*;
import static com.lt.win.service.common.Constant.*;

/**
 * @Auther: wells
 * @Date: 2022/8/1 00:00
 * @Description:
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PayServiceImpl implements PayService {
    private final JedisUtil jedisUtil;
    private final WithdrawalAddressCache withdrawalAddressCache;
    private final PayConfigCache payConfigCache;
    private final WithdrawalAddressService withdrawalAddressServiceImpl;
    private final UserCoinBase userCoinBase;
    private final CoinDepositRecordService coinDepositRecordServiceImpl;
    private final CoinWithdrawalRecordService coinWithdrawalRecordServiceImpl;
    private final UserService userServiceImpl;
//    private final ElasticsearchTemplate<CoinLog, String> elasticsearchTemplate;
    private final AuditCoinBase auditCoinBase;
    private final ConfigCache configCache;
    private final CoinRateCache coinRateCache;
    private final NoticeBase noticeBase;
    private final DictionaryBase dictionaryBase;


    @Override
    public List<WithdrawalAddressListRes> getWithdrawalAddressList() {
        BaseParams.HeaderInfo headerInfo = ThreadHeaderLocalData.HEADER_INFO_THREAD_LOCAL.get();
        List<WithdrawalAddress> withdrawalAddressCacheList = withdrawalAddressCache.getWithdrawalAddressList(headerInfo.getId().toString());
        return BeanConvertUtils.copyListProperties(withdrawalAddressCacheList, WithdrawalAddressListRes::new,
                (source, target) ->
                        target.setCategoryTransferName(CategoryCurrencyEnum.getName(source.getCategoryTransfer()))
        );
    }

    @Override
    public List<WithdrawalAddressTypeRes> getWithdrawalAddressType() {
        List<PayChannel> payChannelList = payConfigCache.getPayChannelByCategory(CHANNEL_OUT);
        Map<Integer, PayChannel> map = payChannelList.stream().collect(Collectors.toMap(PayChannel::getCategoryTransfer, a -> a, (v1, v2) -> v1));
        var resList = new ArrayList<WithdrawalAddressTypeRes>();
        map.forEach((categoryTransfer, payChannel) -> {
            var addressTypeRes = new WithdrawalAddressTypeRes();
            if (categoryTransfer.equals(CategoryCurrencyEnum.PIX.getCode())) {
                var dicMap = dictionaryBase.getCategoryMap("dic_withdrawal_address_account_type");
                List<AddressType> addressTypeList = new ArrayList<>();
                dicMap.forEach((k, v) -> {
                    var addressType = new AddressType();
                    addressType.setName(v);
                    addressType.setType(k);
                    addressTypeList.add(addressType);
                });
                addressTypeRes.setTypeList(addressTypeList);
            }
            addressTypeRes.setCategoryCurrency(payChannel.getCategoryCurrency());
            addressTypeRes.setCategoryTransfer(categoryTransfer);
            addressTypeRes.setCategoryTransferName(CategoryCurrencyEnum.getName(categoryTransfer));
            resList.add(addressTypeRes);
        });
        return resList;
    }

    @Override
    public Boolean addWithdrawalAddress(AddWithdrawalAddressReq req) {
        BaseParams.HeaderInfo headerInfo = ThreadHeaderLocalData.HEADER_INFO_THREAD_LOCAL.get();
        List<WithdrawalAddress> withdrawalAddressCacheList = withdrawalAddressCache.getWithdrawalAddressList(headerInfo.getId().toString());
        withdrawalAddressCacheList = withdrawalAddressCacheList.stream()
                .filter(withdrawalAddress -> withdrawalAddress.getCategoryTransfer().equals(req.getCategoryTransfer()))
                .collect(Collectors.toList());
        int count = withdrawalAddressCacheList.size();
        //最多设置3条收款地址
        if (count >= 3) {
            throw new BusinessException(CodeInfo.WITHDRAWAL_ADDRESS_COUNT_EXCEPTION);
        }
        long repeatCount;
        if (req.getCategoryCurrency().equals(CategoryCurrencyEnum.PIX.getCode())) {
            JSONObject newAddressJson = JSONObject.parseObject(req.getAddress());
            repeatCount = withdrawalAddressCacheList.stream()
                    .filter(address -> {
                        JSONObject addressJson = JSONObject.parseObject(address.getAddress());
                        boolean f1 = newAddressJson.getString("accountType").equals(addressJson.getString("accountType"));
                        boolean f2 = newAddressJson.getString("accountNo").equals(addressJson.getString("accountNo"));
                        return f1 && f2;
                    }).count();
        } else if (req.getCategoryCurrency().equals(CategoryCurrencyEnum.GCASH.getCode())) {
            JSONObject newAddressJson = JSONObject.parseObject(req.getAddress());
            repeatCount = withdrawalAddressCacheList.stream()
                    .filter(address -> {
                        JSONObject addressJson = JSONObject.parseObject(address.getAddress());
                        return newAddressJson.getString("accountNumber").equals(addressJson.getString("accountNumber"));
                    }).count();
        } else {
            repeatCount = withdrawalAddressCacheList.stream()
                    .filter(address -> address.getAddress()
                            .equals(req.getAddress()))
                    .count();
        }

        //验证地址是否重复
        if (repeatCount > 0) {
            throw new BusinessException(CodeInfo.WITHDRAWAL_ADDRESS_REPEAT);
        }
        WithdrawalAddress withdrawalAddress = new WithdrawalAddress();
        //首张卡为默认地址
        if (count == 0) {
            withdrawalAddress.setStatus(1);
        }
        withdrawalAddress.setUid(headerInfo.id);
        withdrawalAddress.setUsername(headerInfo.getUsername());
        withdrawalAddress.setCategoryTransfer(req.getCategoryTransfer());
        withdrawalAddress.setCategoryCurrency(req.getCategoryCurrency());
        withdrawalAddress.setAddress(req.getAddress());
        withdrawalAddress.setCreatedAt(DateUtils.getCurrentTime());
        withdrawalAddress.setUpdatedAt(DateUtils.getCurrentTime());
        boolean flag = withdrawalAddressServiceImpl.save(withdrawalAddress);
        if (flag) {
            jedisUtil.hdel(KeyConstant.WITHDRAWAL_ADDRESS_HASH, headerInfo.getId().toString());
        }
        //用户是否绑定USDT地址
        userServiceImpl.update(new LambdaUpdateWrapper<User>()
                .set(User::getBindBank, 1)
                .eq(User::getId, headerInfo.getId()));
        return flag;
    }

    @Override
    public Boolean updateWithdrawalAddress(UpdateWithdrawalAddressReq updateWithdrawalAddressReq) {
        BaseParams.HeaderInfo headerInfo = ThreadHeaderLocalData.HEADER_INFO_THREAD_LOCAL.get();
        boolean flag = withdrawalAddressServiceImpl.lambdaUpdate()
                .set(WithdrawalAddress::getStatus, Constant.WITHDRAWAL_ADDRESS_STATUS_DEFAULT)
                .eq(WithdrawalAddress::getId, updateWithdrawalAddressReq.getId())
                .update();
        LambdaUpdateWrapper<WithdrawalAddress> withdrawalAddressWrapper = new LambdaUpdateWrapper<>();
        WithdrawalAddress withdrawalAddress = withdrawalAddressServiceImpl.getById(updateWithdrawalAddressReq.getId());
        withdrawalAddressWrapper.set(WithdrawalAddress::getStatus, Constant.WITHDRAWAL_ADDRESS_STATUS_NORMAL)
                .eq(WithdrawalAddress::getUid, headerInfo.id)
                .eq(WithdrawalAddress::getCategoryTransfer, withdrawalAddress.getCategoryTransfer())
                .ne(WithdrawalAddress::getStatus, Constant.WITHDRAWAL_ADDRESS_STATUS_DELETE)
                .ne(WithdrawalAddress::getId, updateWithdrawalAddressReq.getId());
        withdrawalAddressServiceImpl.update(withdrawalAddressWrapper);
        if (flag) {
            jedisUtil.hdel(KeyConstant.WITHDRAWAL_ADDRESS_HASH, headerInfo.getId().toString());
        }
        return flag;
    }

    @Override
    public Boolean deleteWithdrawalAddress(DeleteWithdrawalAddressReq deleteWithdrawalAddressReq) {
        BaseParams.HeaderInfo headerInfo = ThreadHeaderLocalData.HEADER_INFO_THREAD_LOCAL.get();
        boolean flag = withdrawalAddressServiceImpl.lambdaUpdate()
                .set(WithdrawalAddress::getStatus, Constant.WITHDRAWAL_ADDRESS_STATUS_DELETE)
                .eq(WithdrawalAddress::getId, deleteWithdrawalAddressReq.getId())
                .update();
        if (flag) {
            jedisUtil.hdel(KeyConstant.WITHDRAWAL_ADDRESS_HASH, headerInfo.getId().toString());
        }
        List<WithdrawalAddress> withdrawalAddressList = withdrawalAddressCache.getWithdrawalAddressList(headerInfo.id.toString());
        if (CollectionUtils.isEmpty(withdrawalAddressList)) {
            //用户是否绑定USDT地址
            userServiceImpl.update(new LambdaUpdateWrapper<User>()
                    .set(User::getBindBank, 0)
                    .eq(User::getId, headerInfo.getId()));
        }
        return flag;
    }

    @Override
    public List<PayChannelRes> getChannelList(PayChannelReq req) {
        List<PayChannel> payChannelList = payConfigCache.getPayChannelByCategory(req.getCategory());
        return BeanConvertUtils.copyListProperties(payChannelList, PayChannelRes::new);
    }

    @Override
    public CurrencyCoinRes getCurrencyCoin() {
        BaseParams.HeaderInfo headerInfo = ThreadHeaderLocalData.HEADER_INFO_THREAD_LOCAL.get();
        Integer uid = headerInfo.id;
        Object data = jedisUtil.get(KeyConstant.COIN_REWARD_HASH + ":" + uid);
        BigDecimal bonus = BigDecimal.ZERO;
        if (Objects.nonNull(data)) {
            CoinRewards coinRewards = parseObject(data.toString()).toJavaObject(CoinRewards.class);
            bonus = coinRewards.getCoin();
        }
        CurrencyCoinRes currencyCoinRes = new CurrencyCoinRes();
        BigDecimal coin = userCoinBase.getUserCoin(uid);
        currencyCoinRes.setReelCoin(coin);
        currencyCoinRes.setBonus(bonus);
        //判断稽核逻辑
        boolean isAudit = auditCoinBase.audit(uid);
        BigDecimal withdrawalCoin = isAudit ? coin : BigDecimal.ZERO;
        currencyCoinRes.setAllWithdrawalCoin(withdrawalCoin);
        currencyCoinRes.setMainCurrency(configCache.getConfigByTitle("mainCurrency"));
        BigDecimal needCodeCoin = auditCoinBase.getNeedCodeCoin(uid);
        currencyCoinRes.setNeedCodeCoin(needCodeCoin);
        return currencyCoinRes;
    }

    @Override
    public List<WithdrawalPlatRes> getWithdrawalPlat(WithdrawalPlatReq req) {
        List<PayChannel> payChannelList = payConfigCache.getPayChannelByCategory(CHANNEL_OUT);
        payChannelList = payChannelList.stream().filter(payChannel -> Objects.isNull(req.getCurrency()) || payChannel.getCurrency().equals(req.getCurrency()))
                .collect(Collectors.toList());
        return BeanConvertUtils.copyListProperties(payChannelList, WithdrawalPlatRes::new);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean withdrawal(WithdrawalRes withdrawalRes) {
        BaseParams.HeaderInfo headerInfo = ThreadHeaderLocalData.HEADER_INFO_THREAD_LOCAL.get();
        //是否还有未处理的提款记录
        long count = coinWithdrawalRecordServiceImpl.lambdaQuery()
                .eq(CoinWithdrawalRecord::getUid, headerInfo.getId())
                .in(CoinWithdrawalRecord::getStatus, 0, 3).count();
        if (count > 0) {
            throw new BusinessException(CodeInfo.WITHDRAWAL_ORDERS_NO_PROCESS);
        }
        int i = RandomUtils.nextInt(10000, 99999);
        String orderNo = "O" + DateNewUtils.getNowFormatTime(DateNewUtils.Format.yyyyMMddHHmmss) + i;
        BigDecimal mainNetFrees = configCache.getFees();
        mainNetFrees = Objects.isNull(mainNetFrees) ? BigDecimal.ZERO : mainNetFrees;
        BigDecimal userCoin = userCoinBase.getUserCoin(headerInfo.getId());
        BigDecimal withdrawalAmount = withdrawalRes.getWithdrawalAmount();
        BigDecimal realAmount = withdrawalRes.getWithdrawalAmount();
        //var currency = payConfigCache.getWithdrawalCurrency(withdrawalRes.getCategoryCurrency(), withdrawalRes.getCategoryTransfer());
        CoinWithdrawalRecord coinWithdrawalRecord = new CoinWithdrawalRecord();
        //最低提款判断
        BigDecimal minCoin = new BigDecimal(configCache.getConfigByTitle(MIN_COIN));
        BigDecimal maxCoin = new BigDecimal(configCache.getConfigByTitle(MAX_COIN));
        if (withdrawalAmount.compareTo(minCoin) < 0 || withdrawalAmount.compareTo(maxCoin) > 0) {
            throw new BusinessException(CodeInfo.WITHDRAWAL_AMOUNT_ERROR);
        }
//        if (COMM.equals(withdrawalRes.getCategory())) {
//            //提款佣金无需稽核
//            coinWithdrawalRecord.setStatus(3);
//            User user = userServiceImpl.getById(headerInfo.getId());
//            userCoin = user.getCoinCommission();
//        }
        if (realAmount.compareTo(BigDecimal.ZERO) <= 0 || realAmount.compareTo(userCoin) > 0) {
            throw new BusinessException(CodeInfo.COIN_NOT_ENOUGH);
        }
       //String mainCurrency = configCache.getCurrency();
        //提款率处理
//        BigDecimal rate = BigDecimal.ONE;
//        if (!currency.equals(mainCurrency)) {
//            rate = coinRateCache.getCoinRate(currency, mainCurrency);
//        }
        //减除手续费
        BigDecimal fees = new BigDecimal(configCache.getConfigByTitle(FEES));
        realAmount = realAmount.subtract(fees);
        if (realAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException(CodeInfo.WITHDRAWAL_AMOUNT_ERROR);
        }
        coinWithdrawalRecord.setOrderId(orderNo);
        coinWithdrawalRecord.setCategoryCurrency(1);
        coinWithdrawalRecord.setCategoryTransfer(3);
        coinWithdrawalRecord.setUid(headerInfo.getId());
        coinWithdrawalRecord.setUsername(headerInfo.getUsername());
        coinWithdrawalRecord.setExchangeRate(BigDecimal.ONE);
        coinWithdrawalRecord.setWithdrawalAddress(withdrawalRes.getWithdrawalAddress());
        coinWithdrawalRecord.setWithdrawalAmount(withdrawalAmount);
        coinWithdrawalRecord.setRealAmount(realAmount);
        coinWithdrawalRecord.setCoinBefore(userCoin);
        coinWithdrawalRecord.setMainNetFees(mainNetFrees);
        coinWithdrawalRecord.setCurrency("RMB");
        coinWithdrawalRecord.setCreatedAt(DateUtils.getCurrentTime());
        coinWithdrawalRecord.setUpdatedAt(DateUtils.getCurrentTime());
        coinWithdrawalRecordServiceImpl.save(coinWithdrawalRecord);
        //提款用户扣金额
        UserCoinChangeParams.UserCoinChangeReq userCoinChangeReq = new UserCoinChangeParams.UserCoinChangeReq();
        userCoinChangeReq.setUid(headerInfo.getId());
        userCoinChangeReq.setCoin(withdrawalRes.getWithdrawalAmount());
        userCoinChangeReq.setCoinReal(realAmount);
        userCoinChangeReq.setCategory(UserCoinChangeParams.FlowCategoryTypeEnum.WITHDRAWAL.getCode());
        userCoinChangeReq.setOutIn(0);
        userCoinChangeReq.setReferId(coinWithdrawalRecord.getId());
        //发送提款消息
        noticeBase.writeDepositAndWithdrawalCount(Constant.PUSH_WN);
        boolean reFlag;
//        if (COMM.equals(withdrawalRes.getCategory())) {
//            reFlag = userCoinBase.changeCommCoin(userCoinChangeReq);
//        } else {
            reFlag = userCoinBase.userCoinChange(userCoinChangeReq);
      //  }
        return reFlag;
    }


    @Override
    public ResPage<DepositRecordRes> getDepositRecord(ReqPage<DepositRecordReq> req) {
        BaseParams.HeaderInfo headerInfo = ThreadHeaderLocalData.HEADER_INFO_THREAD_LOCAL.get();
        Page<CoinDepositRecord> page = coinDepositRecordServiceImpl.lambdaQuery()
                .eq(CoinDepositRecord::getUid, headerInfo.id)
                .eq(Objects.nonNull(req.getData().getStatus()), CoinDepositRecord::getStatus, req.getData().getStatus())
                .ge(Objects.nonNull(req.getData().getStartTime()), CoinDepositRecord::getCreatedAt, req.getData().getStartTime())
                .le(Objects.nonNull(req.getData().getEndTime()), CoinDepositRecord::getCreatedAt, req.getData().getEndTime())
                .orderByDesc(CoinDepositRecord::getCreatedAt)
                .page(req.getPage());
        String mainCurrency = configCache.getCurrency();
        Page<DepositRecordRes> depositRecordResPage = BeanConvertUtils.copyPageProperties(page, DepositRecordRes::new,
                (source, target) -> {
                    if (COMM_TRANSFER.equals(source.getCode())) {
                        target.setPayAmount(source.getPayAmount().setScale(2, RoundingMode.DOWN) + mainCurrency);
                    } else {
                        PayChannel payChannel = payConfigCache.getNoFilterPayChannel(source.getCode(), CHANNEL_IN);
                        target.setPayAmount(source.getPayAmount().setScale(2, RoundingMode.DOWN) + payChannel.getCurrency());
                    }
                    target.setRealAmount(source.getRealAmount().setScale(2, RoundingMode.DOWN) + mainCurrency);
                    if (source.getCurrency().equals(mainCurrency)) {
                        target.setExchangeRate("--");
                    } else {
                        target.setExchangeRate(source.getExchangeRate().toString());
                    }
                });
        return ResPage.get(depositRecordResPage);
    }

    @Override
    public ResPage<WithdrawalRecordRes> getWithdrawalRecord(ReqPage<WithdrawalRecordReq> req) {
        BaseParams.HeaderInfo headerInfo = ThreadHeaderLocalData.HEADER_INFO_THREAD_LOCAL.get();
        Page<CoinWithdrawalRecord> page = coinWithdrawalRecordServiceImpl.lambdaQuery()
                .eq(CoinWithdrawalRecord::getUid, headerInfo.id)
                .eq(Objects.nonNull(req.getData().getStatus()), CoinWithdrawalRecord::getStatus, req.getData().getStatus())
                .eq(Strings.isNotEmpty(req.getData().getPlatName()), CoinWithdrawalRecord::getPlatName, req.getData().getPlatName())
                .ge(Objects.nonNull(req.getData().getStartTime()), CoinWithdrawalRecord::getCreatedAt, req.getData().getStartTime())
                .le(Objects.nonNull(req.getData().getEndTime()), CoinWithdrawalRecord::getCreatedAt, req.getData().getEndTime())
                .orderByDesc(CoinWithdrawalRecord::getCreatedAt)
                .page(req.getPage());
        String mainCurrency = configCache.getCurrency();
        Page<WithdrawalRecordRes> withdrawalRecordResPage = BeanConvertUtils.copyPageProperties(page, WithdrawalRecordRes::new,
                (source, target) -> {
                    if (COMM_TRANSFER.equals(source.getCode())) {
                        target.setRealAmount(source.getRealAmount().setScale(2, RoundingMode.DOWN) + mainCurrency);
                    } else {
                        var currency = payConfigCache.getWithdrawalCurrency(source.getCategoryCurrency(), source.getCategoryTransfer());
                        target.setRealAmount(source.getRealAmount().setScale(2, RoundingMode.DOWN) + currency);
                    }
                    target.setWithdrawalAmount(source.getWithdrawalAmount().setScale(2, RoundingMode.DOWN) + mainCurrency);
                    if (source.getCurrency().equals(mainCurrency)) {
                        target.setExchangeRate("--");
                    } else {
                        target.setExchangeRate(source.getExchangeRate().toString());
                    }
                });
        return ResPage.get(withdrawalRecordResPage);


    }

    @Override
    public ResPage<CoinLogListRes> getCoinLogList(ReqPage<CoinLogListReq> req) {
        ResPage<CoinLogListRes> result = new ResPage<>();
        try {
            // 获取Header信息 Token、lang、device
            BaseParams.HeaderInfo headerInfo = ThreadHeaderLocalData.HEADER_INFO_THREAD_LOCAL.get();
            PageSortHighLight psh = new PageSortHighLight((int) req.getCurrent(), (int) req.getSize());
            psh.setSort(new Sort(new Sort.Order(SortOrder.DESC, "createdAt")));
            BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
            queryBuilder.must(QueryBuilders.termQuery("uid", headerInfo.getId()));

            if (Objects.nonNull(req.getData().getOutIn())) {
                queryBuilder.must(QueryBuilders.termQuery("outIn", req.getData().getOutIn()));
            }
            if (Objects.nonNull(req.getData().getStartTime()) && Objects.nonNull(req.getData().getEndTime())) {
                queryBuilder.must(QueryBuilders.rangeQuery("createdAt").from(req.getData().getStartTime()).to(req.getData().getEndTime()).includeLower(true).includeUpper(true));
            } else if (Objects.nonNull(req.getData().getStartTime())) {
                queryBuilder.must(QueryBuilders.rangeQuery("createdAt").from(req.getData().getStartTime()).includeLower(true));
            } else if (Objects.nonNull(req.getData().getEndTime())) {
                queryBuilder.must(QueryBuilders.rangeQuery("createdAt").to(req.getData().getEndTime()).includeUpper(true));
            }
            if (Objects.nonNull(req.getData().getCategory())) {
                queryBuilder.must(QueryBuilders.termQuery("category", req.getData().getCategory()));
            }
         //   PageList<CoinLog> pageList = elasticsearchTemplate.search(queryBuilder, psh, CoinLog.class);
//            result.setCurrent(pageList.getCurrentPage());
//            result.setSize(pageList.getPageSize());
//            result.setTotal(pageList.getTotalElements());
//            List<CoinLogListRes> coinLogListRes = BeanConvertUtils.copyListProperties(pageList.getList(), CoinLogListRes::new);
//            result.setList(coinLogListRes);
//            result.setPages(pageList.getTotalPages());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }

    @Override
    public List<DepositChannelListRes> getDepositChannelList() {
        List<PayChannel> payChannelList = payConfigCache.getPayChannelByCategory(CHANNEL_IN);
        return BeanConvertUtils.copyListProperties(payChannelList, DepositChannelListRes::new,
                (source, target) -> {
                    JSONObject jsonObject = JSONObject.parseObject(source.getChannelConfig());
                    target.setRate(coinRateCache.getCoinRate(source.getCurrency()));
                    target.setChips(jsonObject.getString("chips"));
                    target.setSkipCategory(jsonObject.getInteger("skipCategory"));
                });
    }

    @Override
    public List<WithdrawalChannelListRes> getWithdrawalChannelList(WithdrawalChannelListReq req) {
        BaseParams.HeaderInfo headerInfo = ThreadHeaderLocalData.HEADER_INFO_THREAD_LOCAL.get();
        BigDecimal coin = userCoinBase.getUserCoin(headerInfo.id);
        //判断稽核逻辑
        BigDecimal withdrawalCoin = BigDecimal.ZERO;
        if (req.getWithdrawalType() == 0) {
            User user = userServiceImpl.getById(headerInfo.getId());
            withdrawalCoin = user.getCoinCommission();
        } else {
            boolean isAudit = auditCoinBase.audit(headerInfo.id);
            withdrawalCoin = isAudit ? coin : withdrawalCoin;
        }
        List<PayChannel> payChannelList = payConfigCache.getPayChannelByCategory(CHANNEL_OUT);
        Map<Integer, PayChannel> map = payChannelList.stream().collect(Collectors.toMap(PayChannel::getCategoryTransfer, a -> a, (v1, v2) -> v1));


        String mainCurrency = configCache.getCurrency();
        BigDecimal minWithdrawalCoin = new BigDecimal(configCache.getConfigByTitle(MIN_COIN));
        BigDecimal maxWithdrawalCoin = new BigDecimal(configCache.getConfigByTitle(MAX_COIN));
        BigDecimal mainNetFees = new BigDecimal(configCache.getConfigByTitle(FEES));
        BigDecimal finalWithdrawalCoin = withdrawalCoin;
        var reList = new ArrayList<WithdrawalChannelListRes>();
        map.forEach((categoryTransfer, payChannel) -> {
            var target = BeanConvertUtils.copyProperties(payChannel, WithdrawalChannelListRes::new);
            target.setMinWithdrawalCoin(minWithdrawalCoin);
            target.setMaxWithdrawalCoin(maxWithdrawalCoin);
            target.setAllWithdrawalCoin(finalWithdrawalCoin);
            target.setCoin(coin);
            BigDecimal rate = BigDecimal.ONE;
            if (!payChannel.getCurrency().equals(mainCurrency)) {
                rate = coinRateCache.getCoinRate(payChannel.getCurrency(), mainCurrency);
            }
            target.setWithdrawalRate(rate);
            target.setMainNetFees(mainNetFees);
            target.setCurrency(payChannel.getCurrency());
            reList.add(target);
        });
        return reList;
    }

    /**
     * @return com.lt.win.apiend.io.dto.wallet.WalletParams.CheckPixDepositRes
     * @Description PIX充值验证
     * @Param []
     **/
    @Override
    public CheckDepositAddressRes checkDeposit(CheckDepositAddressReq req) {
        String address = "";
        BaseParams.HeaderInfo headerInfo = ThreadHeaderLocalData.HEADER_INFO_THREAD_LOCAL.get();
        address = jedisUtil.get(DEPOSIT_ORDER + ":" + headerInfo.getId() + ":" + req.getCode());
        return CheckDepositAddressRes.builder().address(address).build();
    }
}
