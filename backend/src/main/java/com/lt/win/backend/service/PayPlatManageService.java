package com.lt.win.backend.service;

import com.lt.win.backend.io.dto.PayPlatParams.*;
import com.lt.win.utils.components.pagination.ReqPage;
import com.lt.win.utils.components.pagination.ResPage;
import com.lt.win.utils.components.response.CodeInfo;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * @Auther: wells
 * @Date: 2022/8/29 02:44
 * @Description:
 */
public interface PayPlatManageService {

    ResPage<PayPlatListRes> payPlatList(ReqPage<PayPlatListReq> reqBody);


    Boolean updatePlatList(UpdatePayPlatReq reqBody);

    ResPage<CoinRateListRes> coinListList(ReqPage<CoinRateListReq> reqBody);

    Boolean addCoinRate(AddCoinRateReq reqBody);

    Boolean updateCoinRate(UpdateCoinRateReq reqBody);

    Boolean deleteCoinRate(DeleteCoinRateReq reqBody);

    ResPage<PayChannelListRes> payChannelList(ReqPage<PayChannelListReq> reqBody);

    Boolean updateChannel(UpdateChannelReq reqBody);
}
