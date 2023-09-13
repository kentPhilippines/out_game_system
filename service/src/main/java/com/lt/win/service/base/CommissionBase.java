package com.lt.win.service.base;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.lt.win.dao.generator.po.CoinCommission;
import com.lt.win.dao.generator.po.User;
import com.lt.win.dao.generator.service.CoinCommissionService;
import com.lt.win.dao.generator.service.UserService;
import com.lt.win.service.exception.BusinessException;
import com.lt.win.service.io.dto.CoinLog;
import com.lt.win.service.io.dto.UserCoinChangeParams;
import com.lt.win.service.server.CoinLogService;
import com.lt.win.utils.DateNewUtils;
import com.lt.win.utils.components.response.CodeInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


/**
 * 代理佣金
 *
 * @author david
 * @since 2022-11-11
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CommissionBase {
    private final CoinCommissionService coinCommissionServiceImpl;
    private final UserService userServiceImpl;
    private final CoinLogService coinLogServiceImpl;
   // private final EsSaveBase esSaveBase;


    /**
     * 佣金发放
     *
     * @param comm dto
     */
    @Transactional(rollbackFor = Exception.class)
    public void setCommission(CoinCommission comm) {
        log.info("===============>佣金发放: 会员{}获取佣金{},下{}级{}->投注金额{}", comm.getUsername(), comm.getCoin(), comm.getAgentLevel(), comm.getSubUsername(), comm.getSubBetTrunover());
        var now = DateNewUtils.now();
        var flag = coinCommissionServiceImpl.save(comm);
        if (!flag) {
            throw new BusinessException(CodeInfo.UPDATE_ERROR);
        }

        var user = userServiceImpl.getById(comm.getUid());
        var coinBefore = user.getCoinCommission();
        var coinAfter = coinBefore.add(comm.getCoin());

        flag = userServiceImpl.update(
                new LambdaUpdateWrapper<User>()
                        .setSql("coin_commission = coin_commission + " + comm.getCoin())
                        .set(User::getUpdatedAt, now)
                        .eq(User::getId, comm.getUid())
        );
        if (!flag) {
            throw new BusinessException(CodeInfo.UPDATE_ERROR);
        }

        //日志入库
        CoinLog coinLog = new CoinLog();
        coinLog.setUid(comm.getUid());
        coinLog.setUsername(comm.getUsername());
        coinLog.setCategory(UserCoinChangeParams.FlowCategoryTypeEnum.BROKERAGE.getCode());
        coinLog.setReferId(comm.getId());
        coinLog.setCoin(comm.getCoin());
        coinLog.setCoinBefore(coinBefore);
        coinLog.setCoinAfter(coinAfter);
        coinLog.setStatus(1);
        coinLog.setOutIn(1);
        coinLog.setCreatedAt(now);
        coinLog.setUpdatedAt(now);
        flag = coinLogServiceImpl.save(coinLog);
        if (!flag) {
            throw new BusinessException(CodeInfo.UPDATE_ERROR);
        }

      //  esSaveBase.saveAndES(coinLog, coinAfter);
    }
}
