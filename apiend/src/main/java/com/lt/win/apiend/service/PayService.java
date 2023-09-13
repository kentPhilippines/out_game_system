package com.lt.win.apiend.service;

import com.lt.win.apiend.io.dto.wallet.WalletParams.*;
import com.lt.win.apiend.io.dto.wallet.*;
import com.lt.win.utils.components.pagination.ReqPage;
import com.lt.win.utils.components.pagination.ResPage;
import com.lt.win.utils.components.response.CodeInfo;
import com.lt.win.utils.components.response.Result;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

/**
 * @Auther: wells
 * @Date: 2022/8/1 00:00
 * @Description:
 */
public interface PayService {

    List<WithdrawalAddressListRes> getWithdrawalAddressList();

    List<WithdrawalAddressTypeRes> getWithdrawalAddressType();

    Boolean addWithdrawalAddress(AddWithdrawalAddressReq addWithdrawalAddressReq);

    Boolean updateWithdrawalAddress(UpdateWithdrawalAddressReq updateWithdrawalAddressReq);

    Boolean deleteWithdrawalAddress(DeleteWithdrawalAddressReq deleteWithdrawalAddressReq);

    List<PayChannelRes> getChannelList(PayChannelReq req);

    CurrencyCoinRes getCurrencyCoin();

//    CurrencyCoinRes updateMainWallet(UpdateMainCurrencyReq req);
//
//    List<DepositPlatRes> getDepositPlat(DepositPlatReq req);
//
   List<WithdrawalPlatRes> getWithdrawalPlat(WithdrawalPlatReq req);

    Boolean withdrawal(WithdrawalRes withdrawalRes);

    ResPage<DepositRecordRes> getDepositRecord(ReqPage<DepositRecordReq> req);

    ResPage<WithdrawalRecordRes> getWithdrawalRecord(ReqPage<WithdrawalRecordReq> req);

    ResPage<CoinLogListRes> getCoinLogList(ReqPage<CoinLogListReq> req);

    List<DepositChannelListRes> getDepositChannelList();

    List<WithdrawalChannelListRes> getWithdrawalChannelList(WithdrawalChannelListReq req);

    CheckDepositAddressRes checkDeposit(CheckDepositAddressReq req);
}
