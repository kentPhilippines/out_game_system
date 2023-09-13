package com.lt.win.apiend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lt.win.apiend.service.IUserInfoService;
import com.lt.win.apiend.service.IVipService;
import com.lt.win.dao.generator.po.CoinDepositRecord;
import com.lt.win.dao.generator.po.UserLevel;
import com.lt.win.dao.generator.service.CoinDepositRecordService;
import com.lt.win.dao.generator.service.UserLevelService;
import com.lt.win.service.io.bo.VipBo;
import com.lt.win.utils.BeanConvertUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * <p>
 * 代理中心业务处理接口实现类
 * </p>
 *
 * @author David
 * @since 2020/4/22
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VipServiceImpl implements IVipService {
    private final IUserInfoService userInfoServiceImpl;
    private final UserLevelService userLevelServiceImpl;
    private final CoinDepositRecordService coinDepositRecordServiceImpl;

    /**
     * Vip-membership benefits
     *
     * @return res
     */
    @Override
    public VipBo.MemberShipResDto memberShipDetails() {
        var userInfo = userInfoServiceImpl.findIdentityByApiToken();
        var level = userLevelServiceImpl.getById(userInfo.getLevelId());
        // 历史总充值金额
        var depositRecords = (BigDecimal) coinDepositRecordServiceImpl.getMap(
                new QueryWrapper<CoinDepositRecord>().select("ifNull(SUM(real_amount), 0) as coin")
                        .eq("uid", userInfo.getId())
                        .eq("status", 1)
        ).getOrDefault("coin", BigDecimal.ZERO);


        var response = VipBo.MemberShipResDto.builder()
                .code(level.getCode())
                .name(level.getName())
                .scoreUpgradeMin(level.getScoreUpgradeMin())
                .scoreUpgradeMax(level.getScoreUpgradeMax())
                .accumulatedDeposit(depositRecords)
                .score(userInfo.getScore())
                .build();
        // 设置下一级账号Code 名称
        var next = userLevelServiceImpl.getById(level.getId() + 1);
        var hasNextLevel = Optional.ofNullable(next).isPresent();
        if (hasNextLevel) {
            response.setNextCode(next.getCode());
            response.setNextName(next.getName());
        }

        return response;
    }

    /**
     * Vip-membership level benefits
     *
     * @return res
     */
    @Override
    public List<VipBo.MemberShipLevelResDto> memberShipLevelDetails() {
        var list = userLevelServiceImpl.lambdaQuery()
                .orderByAsc(UserLevel::getId)
                .list();
        var resData = new ArrayList<VipBo.MemberShipLevelResDto>();
        for (UserLevel level : list) {
            var resDto = BeanConvertUtils.beanCopy(level, VipBo.MemberShipLevelResDto::new);
            resDto.setPoints(level.getScoreUpgradeMin());
            resDto.setPointsMultiplier(level.getScoreUpgradeRate());
            resDto.setTierReward(level.getScoreUpgradeMax());
            resData.add(resDto);
        }
        return resData;
    }
}
