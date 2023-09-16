package com.lt.win.backend.base;

import com.lt.win.backend.io.dto.FinanceManageParams;
import com.lt.win.dao.generator.po.CoinAdminTransfer;
import com.lt.win.dao.generator.service.CoinAdminTransferService;
import com.lt.win.dao.generator.service.UserService;
import com.lt.win.service.base.UserCoinBase;
import com.lt.win.service.exception.BusinessException;
import com.lt.win.service.io.dto.UserCoinChangeParams;
import com.lt.win.service.thread.ThreadHeaderLocalData;
import com.lt.win.utils.DateUtils;
import com.lt.win.utils.components.response.CodeInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.stream.Collectors;

/**
 * @Auther: wells
 * @Date: 2022/8/28 23:08
 * @Description:
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FinanceManageBase {
    private final UserService userServiceImpl;
    private final CoinAdminTransferService coinAdminTransferServiceImpl;
    private final UserCoinBase userCoinBase;
    Integer[] OperatorType = {1, 2, 3};

    /**
     * 人工调整入库
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean transferPersistence(FinanceManageParams.AdminTransferReqDto reqDto) {
        var currentLoginUser = ThreadHeaderLocalData.HEADER_INFO_THREAD_LOCAL.get();
        var uid = reqDto.getUid();
        var user = userServiceImpl.getById(uid);
        var now = DateUtils.getCurrentTime();
        if (!Arrays.stream(OperatorType).collect(Collectors.toCollection(LinkedList::new)).contains(reqDto.getOperatorType())) {
           log.info("当前操作类型不支持");
            return false;
        };
        //收支类型:0-支出 1-收入
        var outIn = 0;
        var transferCoin = reqDto.getCoin().negate();
        //add：增加金额，pop：减少金额
        if ("add".equals(reqDto.getType())) {
            outIn = 1;
            transferCoin = reqDto.getCoin();
        }
        //验证余额是否满足支出
        var coin = userCoinBase.getUserCoin(user.getId());
        if (reqDto.getOperatorType() == 2 && coin.compareTo(reqDto.getCoin()) < 0) {
            throw new BusinessException(CodeInfo.COIN_NOT_ENOUGH);
        }
        //调账
        var coinAdminTransfer = new CoinAdminTransfer();
        coinAdminTransfer.setAdminId(currentLoginUser.getId());
        coinAdminTransfer.setCoin(transferCoin);
        coinAdminTransfer.setCoinBefore(coin);
        coinAdminTransfer.setCategory(reqDto.getCategory());
        coinAdminTransfer.setMark(reqDto.getMark());
        // reqDto.getCoinOperatorType()
        coinAdminTransfer.setUid(uid);
        coinAdminTransfer.setUsername(user.getUsername());
        coinAdminTransfer.setCreatedAt(now);
        coinAdminTransfer.setUpdatedAt(now);
        coinAdminTransferServiceImpl.save(coinAdminTransfer);
        //修改用户金额
        UserCoinChangeParams.UserCoinChangeReq userCoinChangeReq = new UserCoinChangeParams.UserCoinChangeReq();
        userCoinChangeReq.setUid(uid);
        userCoinChangeReq.setCoin(transferCoin);
        userCoinChangeReq.setCategory(UserCoinChangeParams.FlowCategoryTypeEnum.SYSTEM_RECONCILIATION.getCode());
        userCoinChangeReq.setOutIn(outIn);
        userCoinChangeReq.setReferId(coinAdminTransfer.getId());
        return userCoinBase.userCoinChange(userCoinChangeReq);
    }

}
