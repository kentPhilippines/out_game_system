package com.lt.win.backend.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lt.win.backend.base.DictionaryBase;
import com.lt.win.backend.base.FinanceManageBase;
import com.lt.win.backend.io.dto.FinanceManageParams.*;
import com.lt.win.backend.service.FinanceManageService;
import com.lt.win.dao.generator.po.*;
import com.lt.win.dao.generator.service.*;
import com.lt.win.service.base.AuditCoinBase;
import com.lt.win.service.base.NoticeBase;
import com.lt.win.service.base.UserCoinBase;
import com.lt.win.service.cache.redis.ConfigCache;
import com.lt.win.service.cache.redis.PayConfigCache;
import com.lt.win.service.impl.PayDatabaseServiceImpl;
import com.lt.win.service.io.dto.BaseParams;
import com.lt.win.service.io.dto.UserCoinChangeParams;
import com.lt.win.service.io.enums.SystemMessageEnum;
import com.lt.win.service.thread.ThreadHeaderLocalData;
import com.lt.win.utils.BeanConvertUtils;
import com.lt.win.utils.DateUtils;
import com.lt.win.utils.components.pagination.ReqPage;
import com.lt.win.utils.components.pagination.ResPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import static com.lt.win.service.common.Constant.*;
import static java.util.Objects.nonNull;

/**
 * @Auther: wells
 * @Date: 2022/8/24 01:47
 * @Description:
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FinanceManageServiceImpl implements FinanceManageService {
    private final CoinDepositRecordService coinDepositRecordServiceImpl;
    private final CoinWithdrawalRecordService coinWithdrawalRecordServiceImpl;
    private final FinanceManageBase financeManageBase;
    private final AuditCoinBase auditCoinBase;
    private final DictionaryBase dictionaryBase;
    private final UserWalletService userWalletServiceImpl;
    private final CodeAuditService codeAuditServiceImpl;
    private final CodeRecordsService codeRecordsServiceImpl;
    private final PayConfigCache payConfigCache;
    private final UserCoinBase userCoinBase;
    private final ConfigCache configCache;
    private final NoticeBase noticeBase;
    private final PayDatabaseServiceImpl payDatabaseServiceImpl;


    @Override
    public ResPage<DepositRecordRes> depositRecordList(ReqPage<DepositRecordReq> req) {
        var params = req.getData();
        var queryWrapper = getDepositListQuery(params);
        //无排序启动默认排序
        queryWrapper.orderByDesc(req.getSortField().length == 0, CoinDepositRecord::getUpdatedAt);
        Page<CoinDepositRecord> page = coinDepositRecordServiceImpl.page(req.getPage(), queryWrapper);
        String mainCurrency = configCache.getCurrency();
        Page<DepositRecordRes> depositRecordResPage = BeanConvertUtils.copyPageProperties(page, DepositRecordRes::new,
                ((source, target) -> {
                    if (COMM_TRANSFER.equals(source.getCode())) {
                        target.setPayAmount(source.getPayAmount().setScale(2, RoundingMode.DOWN) + mainCurrency);
                    } else {
                        PayChannel payChannel = payConfigCache.getNoFilterPayChannel(source.getCode(), CHANNEL_IN);
                        target.setPayAmount(source.getPayAmount().setScale(2, RoundingMode.DOWN) + payChannel.getCurrency());
                    }
                    Integer updateAt = source.getStatus() == 0 ? 0 : source.getUpdatedAt();
                    target.setUpdatedAt(updateAt);
                    target.setRealAmount(source.getRealAmount().setScale(2, RoundingMode.DOWN) + mainCurrency);
                }));
        return ResPage.get(depositRecordResPage);
    }


    /**
     * 充值列表汇总
     *
     * @param reqDto
     * @return
     */
    @Override
    public DepositSumResDto depositSum(DepositRecordReq reqDto) {
        var queryWrapper = getDepositListQuery(reqDto).select(CoinDepositRecord::getRealAmount);
        queryWrapper.eq(CoinDepositRecord::getStatus, 1);
        var list = coinDepositRecordServiceImpl.list(queryWrapper);
        var totalCoin = list.stream().map(CoinDepositRecord::getRealAmount).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        return DepositSumResDto.builder().count(list.size()).totalCoin(totalCoin).build();
    }

    @Override
    public UpdateDepositStatusResDto updateDepositStatus(UpdateDepositStatusReqDto reqDto) {
        return null;
    }

    @Override
    public DepositDetailResDto depositDetail(DepositDetailReqDto reqDto) {
        CoinDepositRecord coinDepositRecord = coinDepositRecordServiceImpl.getById(reqDto.getId());
        String mainCurrency = configCache.getCurrency();
        return BeanConvertUtils.copyProperties(coinDepositRecord, DepositDetailResDto::new,
                (source, target) -> {
                    if (COMM_TRANSFER.equals(source.getCode())) {
                        target.setPayAmount(source.getPayAmount().setScale(2, RoundingMode.DOWN) + mainCurrency);
                    } else {
                        PayChannel payChannel = payConfigCache.getNoFilterPayChannel(source.getCode(), CHANNEL_IN);
                        target.setPayAmount(source.getPayAmount().setScale(2, RoundingMode.DOWN) + payChannel.getCurrency());
                    }
                    Integer updateAt = source.getStatus() == 0 ? 0 : source.getUpdatedAt();
                    target.setUpdatedAt(updateAt);
                    target.setRealAmount(source.getRealAmount().setScale(2, RoundingMode.DOWN) + mainCurrency);
                });
    }


    @Override
    public ResPage<WithdrawalRecordRes> withdrawalRecordList(ReqPage<WithdrawalRecordReq> req) {
        LambdaQueryWrapper<CoinWithdrawalRecord> withdrawalListQuery = getWithdrawalListQuery(req.getData());
        //无排序启动默认排序
        withdrawalListQuery.orderByDesc(req.getSortField().length == 0, CoinWithdrawalRecord::getUpdatedAt);
        Page<CoinWithdrawalRecord> page = coinWithdrawalRecordServiceImpl.page(req.getPage(), withdrawalListQuery);
        String mainCurrency = configCache.getCurrency();
        Page<WithdrawalRecordRes> withdrawalRecordResPage = BeanConvertUtils.copyPageProperties(page, WithdrawalRecordRes::new,
                ((source, target) -> {
                    String currency = payConfigCache.getWithdrawalCurrency(source.getCategoryCurrency(), source.getCategoryTransfer());
                    Integer updateAt = source.getStatus() == 0 ? 0 : source.getUpdatedAt();
                    target.setUpdatedAt(updateAt);
                    target.setWithdrawalAmount(source.getWithdrawalAmount().setScale(2, RoundingMode.DOWN) + mainCurrency);
                    target.setRealAmount(source.getRealAmount().setScale(2, RoundingMode.DOWN) + currency);
                    target.setExchangeRate("1:" + source.getExchangeRate());
                    target.setMainNetFees(source.getMainNetFees().setScale(2, RoundingMode.DOWN) + source.getCurrency());
                    //通道列表
                    List<PayChannel> withdrawalChannelList = payConfigCache.getWithdrawalChannelList(source.getCategoryTransfer());
                    if (CollectionUtil.isNotEmpty(withdrawalChannelList)) {
                        List<WithdrawalChannelList> wChannelList = BeanConvertUtils.copyListProperties(withdrawalChannelList, WithdrawalChannelList::new);
                        target.setWithdrawalChannelList(wChannelList);
                    } else {
                        target.setWithdrawalChannelList(new ArrayList<>());
                    }
                }));
        return ResPage.get(withdrawalRecordResPage);
    }

    /**
     * 提款列表汇总
     */
    @Override
    public WithdrawalSumResDto withdrawalSum(WithdrawalRecordReq reqDto) {
        var queryWrapper = getWithdrawalListQuery(reqDto).select(CoinWithdrawalRecord::getWithdrawalAmount);
        queryWrapper.eq(CoinWithdrawalRecord::getStatus, 1);
        var list = coinWithdrawalRecordServiceImpl.list(queryWrapper);
        var totalCoin = list.stream().map(CoinWithdrawalRecord::getWithdrawalAmount).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        return WithdrawalSumResDto.builder().count(list.size()).totalCoin(totalCoin).build();
    }

    /**
     * 修改提款记录
     *
     * @param reqDto
     * @return
     */
    @Override
    public UpdateWithdrawalStatusResDto updateWithdrawalStatus(UpdateWithdrawalStatusReqDto reqDto) {
        var dicMap = dictionaryBase.getCategoryMap("dic_audit_status");
        BaseParams.HeaderInfo currentLoginUser = ThreadHeaderLocalData.HEADER_INFO_THREAD_LOCAL.get();
        var reFlag = coinWithdrawalRecordServiceImpl.update(new LambdaUpdateWrapper<CoinWithdrawalRecord>()
                .set(CoinWithdrawalRecord::getStatus, reqDto.getStatus())
                .set(CoinWithdrawalRecord::getMark, reqDto.getMark())
                .set(CoinWithdrawalRecord::getAdminUid, currentLoginUser.getId())
                .set(CoinWithdrawalRecord::getUpdatedAt, DateUtils.getCurrentTime())
                .eq(CoinWithdrawalRecord::getId, reqDto.getId()));
        //提款失败退还用户金额
        var auditMsg = "";
        if (reqDto.getStatus() == 2) {
            CoinWithdrawalRecord coinWithdrawalRecord = coinWithdrawalRecordServiceImpl.getById(reqDto.getId());
            UserCoinChangeParams.UserCoinChangeReq userCoinChangeReq = new UserCoinChangeParams.UserCoinChangeReq();
            userCoinChangeReq.setUid(coinWithdrawalRecord.getUid());
            userCoinChangeReq.setCoin(coinWithdrawalRecord.getWithdrawalAmount());
            userCoinChangeReq.setCategory(UserCoinChangeParams.FlowCategoryTypeEnum.WITHDRAWAL_REFUND.getCode());
            userCoinChangeReq.setOutIn(1);
            userCoinChangeReq.setReferId(coinWithdrawalRecord.getId());
            userCoinBase.userCoinChange(userCoinChangeReq);
            //保存系统消息
            String coinStr = coinWithdrawalRecord.getWithdrawalAmount() + coinWithdrawalRecord.getCurrency();
            noticeBase.saveSystemMessage(SystemMessageEnum.WITHDRAWAL_FAIL, coinStr, null, coinWithdrawalRecord.getUid());
            auditMsg = dicMap.get(WITHDRAWAL_FAIL);
        }
        if (reqDto.getStatus() == 1) {
            auditMsg = "提款成功";
        }
        return UpdateWithdrawalStatusResDto.builder().auditFlag(reFlag).auditMsg(auditMsg).build();
    }

    /**
     * 提款记录详情
     *
     * @return
     */
    @Override
    public WithdrawalDetailResDto withdrawalDetail(WithdrawalDetailReqDto reqDto) {
        CoinWithdrawalRecord coinWithdrawalRecord = coinWithdrawalRecordServiceImpl.getById(reqDto.getId());
        String mainCurrency = configCache.getCurrency();
        return BeanConvertUtils.copyProperties(coinWithdrawalRecord, WithdrawalDetailResDto::new,
                (source, target) -> {
                    String currency = payConfigCache.getWithdrawalCurrency(source.getCategoryCurrency(), source.getCategoryTransfer());
                    Integer updateAt = source.getStatus() == 0 ? 0 : source.getUpdatedAt();
                    target.setUpdatedAt(updateAt);
                    target.setWithdrawalAmount(source.getWithdrawalAmount().setScale(2, RoundingMode.DOWN) + mainCurrency);
                    target.setRealAmount(source.getRealAmount().setScale(2, RoundingMode.DOWN) + currency);
                    target.setExchangeRate("1:" + source.getExchangeRate());
                    target.setMainNetFees(source.getMainNetFees().setScale(2, RoundingMode.DOWN) + source.getCurrency());
                });
    }

    /**
     * 稽核
     *
     * @param reqDto
     * @return
     */
    @Override
    public UpdateWithdrawalStatusResDto isAudit(AuditReqDto reqDto) {
        BaseParams.HeaderInfo currentLoginUser = ThreadHeaderLocalData.HEADER_INFO_THREAD_LOCAL.get();
        boolean auditFlag = auditCoinBase.audit(reqDto.getId(), currentLoginUser.getId());
        var dicMap = dictionaryBase.getCategoryMap("dic_audit_status");
        String auditStatus = auditFlag ? AUDIT_SUCCESS : AUDIT_FAIL;
        if (!auditFlag) {
            //稽核失败退还玩家金额
            CoinWithdrawalRecord coinWithdrawalRecord = coinWithdrawalRecordServiceImpl.getById(reqDto.getId());
            UserCoinChangeParams.UserCoinChangeReq userCoinChangeReq = new UserCoinChangeParams.UserCoinChangeReq();
            userCoinChangeReq.setUid(coinWithdrawalRecord.getUid());
            userCoinChangeReq.setCoin(coinWithdrawalRecord.getWithdrawalAmount());
            userCoinChangeReq.setCategory(UserCoinChangeParams.FlowCategoryTypeEnum.WITHDRAWAL_REFUND.getCode());
            userCoinChangeReq.setOutIn(1);
            userCoinChangeReq.setReferId(coinWithdrawalRecord.getId());
            userCoinBase.userCoinChange(userCoinChangeReq);
            //保存系统消息
            String coinStr = coinWithdrawalRecord.getWithdrawalAmount() + coinWithdrawalRecord.getCurrency();
            noticeBase.saveSystemMessage(SystemMessageEnum.WITHDRAWAL_FAIL, coinStr, null, coinWithdrawalRecord.getUid());
        }
        var auditMsg = dicMap.get(auditStatus);
        return UpdateWithdrawalStatusResDto.builder()
                .auditMsg(auditMsg)
                .auditFlag(auditFlag)
                .build();
    }

    /**
     * @param pageReqDto
     * @return AuditDetailResDto
     * @Description 稽核详情
     * @Param [reqDto]
     */
    @Override
    public AuditDetailResDto auditDetail(ReqPage<AuditReqDto> pageReqDto) {
        var reqDto = pageReqDto.getData();
        var audit = codeAuditServiceImpl.getOne(new LambdaQueryWrapper<CodeAudit>()
                        .eq(CodeAudit::getReferId, reqDto.getId())
                , false);
        var auditStatus = Optional.ofNullable(audit).map(CodeAudit::getStatus).orElse(2);
        var codeRequire = Optional.ofNullable(audit).map(CodeAudit::getCodeRequire).orElse(BigDecimal.ZERO);
        var codeReal = Optional.ofNullable(audit).map(CodeAudit::getCodeReal).orElse(BigDecimal.ZERO);
        var queryWrapper = new LambdaQueryWrapper<CodeRecords>()
                .eq(CodeRecords::getReferWithdrawalId, reqDto.getId());
        var page = codeRecordsServiceImpl.page(pageReqDto.getPage(), queryWrapper);
        var resPage = BeanConvertUtils.copyPageProperties(page, CodeRecordsResDto::new);
        return AuditDetailResDto.builder()
                .id(reqDto.getId())
                .codeRequire(codeRequire)
                .codeReal(codeReal)
                .auditStatus(auditStatus)
                .codeRecordsResDtoList(ResPage.get(resPage))
                .build();
    }

    /**
     * 人工调账
     *
     * @param reqDto
     * @return
     */
    @Override
    public boolean adminTransfer(AdminTransferReqDto reqDto) {
        return financeManageBase.transferPersistence(reqDto);
    }

    /**
     * @param reqDto
     * @return java.util.List<com.lt.win.backend.io.dto.FinanceManageParams.UserCoinRes>
     * @Description 获取用户余额
     * @Param [reqDto]
     */
    @Override
    public UserCoinRes getUserCoin(UserCoinReq reqDto) {
        UserWallet userWallet = userWalletServiceImpl.lambdaQuery()
                .eq(nonNull(reqDto.getUsername()), UserWallet::getUsername, reqDto.getUsername())
                .one();
        return BeanConvertUtils.copyProperties(userWallet, UserCoinRes::new, (source, target) ->
                target.setUid(source.getId())
        );
    }


    @Override
    public List<PayChannelRes> getChannelList(PayChannelReq req) {
        List<PayChannel> payChannelList = payConfigCache.getPayChannelByCategory(req.getCategory());
        return BeanConvertUtils.copyListProperties(payChannelList, PayChannelRes::new);
    }

    @Override
    public Boolean replenishmentOrder(ReplenishmentOrderReq req) {
        BaseParams.HeaderInfo currentLoginUser = ThreadHeaderLocalData.HEADER_INFO_THREAD_LOCAL.get();
        //补单成功
        if (req.getStatus() == 1) {
            var record = coinDepositRecordServiceImpl.getById(req.getId());
            payDatabaseServiceImpl.depositUpdate(record.getOrderId(), record.getPayAmount(), currentLoginUser.id);
            return true;
        }
        var coinDeposit = BeanConvertUtils.copyProperties(req, CoinDepositRecord::new);
        coinDeposit.setAdminUid(currentLoginUser.id);
        coinDeposit.setUpdatedAt(DateUtils.getCurrentTime());
        return coinDepositRecordServiceImpl.updateById(coinDeposit);
    }


    /**
     * 获取充值列表的查询条件
     *
     * @param params
     * @return
     */
    public LambdaQueryWrapper<CoinDepositRecord> getDepositListQuery(DepositRecordReq params) {
        return new QueryWrapper<CoinDepositRecord>().lambda()
                //用户Id
                .eq(nonNull(params.getUid()), CoinDepositRecord::getUid, params.getUid())
                //用户名
                .eq(nonNull(params.getCode()), CoinDepositRecord::getCode, params.getCode())
                //用户名
                .eq(nonNull(params.getUsername()), CoinDepositRecord::getUsername, params.getUsername())
                //订单号/ID (三方平台用)
                .and(nonNull(params.getOrderId()), x -> x.eq(CoinDepositRecord::getOrderId, params.getOrderId())
                        .or()
                        .eq(CoinDepositRecord::getId, params.getOrderId())
                )
                //类型:0-钱包充值 1-佣金钱包转账充值
                .eq(nonNull(params.getCategory()), CoinDepositRecord::getCategory, params.getCategory())
                //:状态: 0-申请中 1-成功
                .eq(nonNull(params.getStatus()), CoinDepositRecord::getStatus, params.getStatus())
                //开始时间
                .ge(nonNull(params.getStartTime()), CoinDepositRecord::getUpdatedAt, params.getStartTime())
                //结束时间
                .le(nonNull(params.getEndTime()), CoinDepositRecord::getUpdatedAt, params.getEndTime());
    }

    /**
     * 获取提款列表的查询条件
     */
    public LambdaQueryWrapper<CoinWithdrawalRecord> getWithdrawalListQuery(WithdrawalRecordReq params) {
        return new LambdaQueryWrapper<CoinWithdrawalRecord>()
                //用户Id
                .eq(nonNull(params.getUid()), CoinWithdrawalRecord::getUid, params.getUid())
                //用户名
                .eq(nonNull(params.getUsername()), CoinWithdrawalRecord::getUsername, params.getUsername())
                //订单号
                .and(nonNull(params.getId()), x -> x.eq(CoinWithdrawalRecord::getOrderId, params.getId())
                        .or()
                        .eq(CoinWithdrawalRecord::getId, params.getId())
                )
                .eq(nonNull(params.getId()), CoinWithdrawalRecord::getId, params.getId())
                //类型:0-申请中，1-成功，2-失败
                .eq(nonNull(params.getStatus()), CoinWithdrawalRecord::getStatus, params.getStatus())
                //审核人UID
                .eq(nonNull(params.getAdminUid()), CoinWithdrawalRecord::getAdminUid, params.getAdminUid())
                //开始时间
                .ge(nonNull(params.getStartTime()), CoinWithdrawalRecord::getUpdatedAt, params.getStartTime())
                //结束时间
                .le(nonNull(params.getEndTime()), CoinWithdrawalRecord::getUpdatedAt, params.getEndTime());
    }
}
