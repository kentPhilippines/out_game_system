package com.lt.win.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lt.win.backend.io.dto.FinanceRecordsParams.*;
import com.lt.win.backend.service.FinanceRecordService;
import com.lt.win.dao.generator.po.*;
import com.lt.win.dao.generator.service.CoinAdminTransferService;
import com.lt.win.dao.generator.service.CoinDepositRecordService;
import com.lt.win.dao.generator.service.CoinWithdrawalRecordService;
import com.lt.win.dao.generator.service.UserLevelService;
import com.lt.win.service.cache.redis.AdminCache;
import com.lt.win.service.cache.redis.ConfigCache;
import com.lt.win.service.cache.redis.PayConfigCache;
import com.lt.win.service.cache.redis.UserCache;
import com.lt.win.service.common.Constant;
import com.lt.win.service.exception.BusinessException;
import com.lt.win.utils.BeanConvertUtils;
import com.lt.win.utils.components.pagination.ReqPage;
import com.lt.win.utils.components.pagination.ResPage;
import com.lt.win.utils.components.response.CodeInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.lt.win.service.common.Constant.*;
import static java.util.Objects.nonNull;

/**
 * @Auther: wells
 * @Date: 2022/8/29 00:07
 * @Description:
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FinanceRecordServiceImpl implements FinanceRecordService {
    private final CoinDepositRecordService coinDepositRecordServiceImpl;
    private final CoinWithdrawalRecordService coinWithdrawalRecordServiceImpl;
    private final CoinAdminTransferService coinAdminTransferServiceImpl;
    private final UserCache userCache;
    private final UserLevelService userLevelServiceImpl;
    private final AdminCache adminCache;
    private final ConfigCache configCache;
    private final PayConfigCache payConfigCache;

    /**
     * 出款记录-列表
     *
     * @param reqBody reqBody
     * @return 列表
     */
    @Override
    public ResPage<WithdrawalListResBody> withdrawalList(ReqPage<WithdrawalListReqBody> reqBody) {
        if (null == reqBody) {
            throw new BusinessException(CodeInfo.PARAMETERS_INVALID);
        }
        Map<Integer, String> levelMap = userLevelServiceImpl.list().stream()
                .collect(Collectors.toMap(UserLevel::getId, UserLevel::getCode));
        QueryWrapper<CoinWithdrawalRecord> wrapper;
        if (Objects.nonNull(reqBody.getData().getStatus())) {
            wrapper = whereWithdrawalListOrStatistics(reqBody.getData(), 1);
        } else {
            wrapper = whereWithdrawalListOrStatistics(reqBody.getData(), 1, 2);
        }
        Page<CoinWithdrawalRecord> page = coinWithdrawalRecordServiceImpl.page(reqBody.getPage(), wrapper);
        Page<WithdrawalListResBody> tmpPage = BeanConvertUtils.copyPageProperties(page, WithdrawalListResBody::new,
                ((source, target) -> {
                    //会员等级
                    Integer userLevelId = userCache.getUserInfo(source.getUid()).getLevelId();
                    target.setUserLevel(levelMap.get(userLevelId));
                    target.setWithdrawalAmount(source.getWithdrawalAmount().setScale(2, RoundingMode.DOWN) + "");
                    target.setRealAmount(source.getRealAmount().setScale(2, RoundingMode.DOWN) + "");
                }));
        return ResPage.get(tmpPage);

    }

    /**
     * 出款记录-统计
     *
     * @param reqBody reqBody
     * @return 统计金额
     */
    @Override
    public CommonCoinResBody withdrawalStatistics(WithdrawalListReqBody reqBody) {
        if (null == reqBody) {
            throw new BusinessException(CodeInfo.PARAMETERS_INVALID);
        }
        QueryWrapper<CoinWithdrawalRecord> wrapper = whereWithdrawalListOrStatistics(reqBody, 1);
        wrapper.select("ifnull(sum(real_amount),0) as coin");
        Map<String, Object> map = coinWithdrawalRecordServiceImpl.getMap(wrapper);
        BigDecimal coin = BigDecimal.ZERO;
        if (null != map) {
            coin = (BigDecimal) map.get("coin");
        }
        return CommonCoinResBody.builder().coin(coin).build();
    }

    /**
     * 入款记录-列表
     *
     * @param reqBody reqBody
     * @return 列表
     */
    @Override
    public ResPage<DepositListResBody> depositList(ReqPage<DepositListReqBody> reqBody) {
        if (null == reqBody) {
            throw new BusinessException(CodeInfo.PARAMETERS_INVALID);
        }
        QueryWrapper<CoinDepositRecord> wrapper = whereDepositListOrStatistics(reqBody.getData(), 1, 2);
        Page<CoinDepositRecord> page = coinDepositRecordServiceImpl.page(reqBody.getPage(), wrapper);
        String mainCurrency = configCache.getCurrency();
        Page<DepositListResBody> tmpPage = BeanConvertUtils.copyPageProperties(page, DepositListResBody::new,
                (source, target) -> {
                    if (COMM_TRANSFER.equals(source.getCode())) {
                        target.setPayAmount(source.getPayAmount().setScale(2, RoundingMode.DOWN) + mainCurrency);
                    } else {
                        PayChannel payChannel = payConfigCache.getNoFilterPayChannel(source.getCode(), CHANNEL_IN);
                        target.setPayAmount(source.getPayAmount().setScale(2, RoundingMode.DOWN) + payChannel.getCurrency());
                    }
                    target.setRealAmount(source.getRealAmount().setScale(2, RoundingMode.DOWN) + mainCurrency);

                });
        return ResPage.get(tmpPage);
    }

    /**
     * 入款记录-统计
     *
     * @param reqBody reqBody
     * @return 统计金额
     */
    @Override
    public CommonCoinResBody depositStatistics(DepositListReqBody reqBody) {
        if (null == reqBody) {
            throw new BusinessException(CodeInfo.PARAMETERS_INVALID);
        }
        QueryWrapper<CoinDepositRecord> wrapper = whereDepositListOrStatistics(reqBody, 1);
        wrapper.select("ifnull(sum(real_amount),0) as coin");
        Map<String, Object> map = coinDepositRecordServiceImpl.getMap(wrapper);
        BigDecimal coin = BigDecimal.ZERO;
        if (null != map) {
            coin = (BigDecimal) map.get("coin");
        }
        return CommonCoinResBody.builder().coin(coin).build();

    }

    /**
     * 人工调账-列表
     *
     * @param reqBody reqBody
     * @return 列表
     */
    @Override
    public ResPage<AdminTransferListResBody> adminTransferList(ReqPage<AdminTransferListReqBody> reqBody) {
        if (null == reqBody) {
            throw new BusinessException(CodeInfo.PARAMETERS_INVALID);
        }
        QueryWrapper<CoinAdminTransfer> wrapper = whereAdminTransferListOrStatistics(reqBody.getData());
        Page<CoinAdminTransfer> page = coinAdminTransferServiceImpl.page(reqBody.getPage(), wrapper);
        Page<AdminTransferListResBody> tmpPage = BeanConvertUtils.copyPageProperties(page, AdminTransferListResBody::new,
                (source, target) -> {
                    Admin admin = adminCache.getAdminInfoById(source.getAdminId());
                    target.setAdminName(admin.getUsername());
                });
        return ResPage.get(tmpPage);
    }

    /**
     * 人工调账-统计
     *
     * @param reqBody reqBody
     * @return 统计金额
     */
    @Override
    public CommonCoinResBody adminTransferStatistics(AdminTransferListReqBody reqBody) {
        if (null == reqBody) {
            throw new BusinessException(CodeInfo.PARAMETERS_INVALID);
        }
        QueryWrapper<CoinAdminTransfer> wrapper = whereAdminTransferListOrStatistics(reqBody);
        wrapper.select("ifnull(sum(coin),0) as coin");
        Map<String, Object> map = coinAdminTransferServiceImpl.getMap(wrapper);
        BigDecimal coin = BigDecimal.ZERO;
        if (null != map) {
            coin = (BigDecimal) map.get("coin");
        }
        return CommonCoinResBody.builder().coin(coin).build();
    }

    /**
     * 出款记录-列表与统计:构造Where查询条件
     *
     * @param data 请求参数
     * @return Where查询条件
     */
    private QueryWrapper<CoinWithdrawalRecord> whereWithdrawalListOrStatistics(WithdrawalListReqBody data, Integer... status) {
        QueryWrapper<CoinWithdrawalRecord> wrapper = null;
        if (null != data) {
            wrapper = Wrappers.query();
            wrapper.eq(null != data.getId(), Constant.ID, data.getId());
            wrapper.eq(null != data.getUid(), Constant.UID, data.getUid());
            wrapper.eq(null != data.getCode(), "code", data.getCode());
            wrapper.eq(null != data.getUsername(), Constant.USERNAME, data.getUsername());
            wrapper.eq(null != data.getAdminUid(), "admin_uid", data.getAdminUid());
            wrapper.in(null != status, Constant.STATUS, status);
            wrapper.ge(null != data.getStartTime(), Constant.UPDATED_AT, data.getStartTime());
            wrapper.le(null != data.getEndTime(), Constant.UPDATED_AT, data.getEndTime());
        }
        return wrapper;
    }

    /**
     * 入款记录-列表与统计:构造Where查询条件
     *
     * @param data 请求参数
     * @return Where查询条件
     */
    private QueryWrapper<CoinDepositRecord> whereDepositListOrStatistics(DepositListReqBody data, Integer... status) {
        QueryWrapper<CoinDepositRecord> wrapper = null;
        if (null != data) {
            wrapper = Wrappers.query();
            //订单号/ID (三方平台用)
            wrapper.and(nonNull(data.getId()), x -> x.eq(Constant.ORDER_ID, data.getId())
                    .or()
                    .eq(ID, data.getId())
            );
            wrapper.eq(null != data.getUsername(), Constant.USERNAME, data.getUsername());
            // 0-申请中，1-提款成功，2-提款失败，3-稽核成功
            wrapper.in(null != status, Constant.STATUS, status);
            wrapper.eq(null != data.getCategory(), Constant.CATEGORY, data.getCategory());
            wrapper.ge(null != data.getStartTime(), Constant.UPDATED_AT, data.getStartTime());
            wrapper.le(null != data.getEndTime(), Constant.UPDATED_AT, data.getEndTime());
        }
        return wrapper;
    }

    /**
     * 调账记录-列表与统计:构造Where查询条件
     *
     * @param data 请求参数
     * @return Where查询条件
     */
    private QueryWrapper<CoinAdminTransfer> whereAdminTransferListOrStatistics(AdminTransferListReqBody data) {
        QueryWrapper<CoinAdminTransfer> wrapper = null;
        if (null != data) {
            wrapper = Wrappers.query();
            wrapper.eq(null != data.getId(), Constant.ID, data.getId());
            wrapper.eq(null != data.getUid(), Constant.UID, data.getUid());
            wrapper.eq(null != data.getUsername(), Constant.USERNAME, data.getUsername());
            wrapper.eq(null != data.getAdminId(), "admin_id", data.getAdminId());
            wrapper.eq(null != data.getCategory(), Constant.CATEGORY, data.getCategory());
            wrapper.ge(null != data.getStartTime(), Constant.UPDATED_AT, data.getStartTime());
            wrapper.le(null != data.getEndTime(), Constant.UPDATED_AT, data.getEndTime());
        }
        return wrapper;
    }
}


