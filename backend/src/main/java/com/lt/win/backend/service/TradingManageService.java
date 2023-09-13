package com.lt.win.backend.service;

import com.lt.win.backend.io.dto.record.TradingManageParams;
import com.lt.win.backend.io.dto.record.TradingManageParams.*;
import com.lt.win.utils.components.pagination.ReqPage;
import com.lt.win.utils.components.pagination.ResPage;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * @Auther: wells
 * @Date: 2022/8/31 00:40
 * @Description:
 */
public interface TradingManageService {

    /**
     * @return com.lt.win.utils.components.pagination.ResPage<com.lt.win.backend.io.dto.record.TradingManageParams.CoinLogListReq>
     * @Description 查询交易记录
     * @Param [reqBody]
     **/
    ResPage<CoinLogListRes> tradingManageList(ReqPage<CoinLogListReq> reqBody);

    /**
     * @return StatisticsTrading
     * @Description 统计交易记录类型
     * @Param
     **/
    StatisticsTrading statisticsTrading(CoinLogListReq reqBody);

}
