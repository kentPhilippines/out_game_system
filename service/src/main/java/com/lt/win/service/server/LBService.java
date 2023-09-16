package com.lt.win.service.server;

import com.lt.win.dao.generator.po.LotteryBetslips;
import com.lt.win.service.io.dto.BaseParams;
import com.lt.win.service.io.qo.Betslip;
import com.lt.win.utils.components.pagination.ReqPage;
import com.lt.win.utils.components.pagination.ResPage;

public interface LBService {
    ResPage<LotteryBetslips> queryBets(ReqPage<Betslip.BetslipsDto> reqPage, BaseParams.HeaderInfo userInfo);

}
