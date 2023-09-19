package com.lt.win.backend.service;

import com.lt.win.backend.io.dto.LotteryManagerParams;
import com.lt.win.backend.io.dto.LotteryManagerParams.*;
import com.lt.win.utils.components.pagination.ReqPage;
import com.lt.win.utils.components.pagination.ResPage;

/**
 * @Auther: Jess
 * @Date: 2023/9/19 11:00
 * @Description:
 */
public interface LotteryManagerService {
/**
 * @Description 彩种列表
 * @Param [reqBody]
 * @return com.lt.win.utils.components.pagination.ResPage<com.lt.win.backend.io.dto.LotteryManagerParams.LotteryTypePageRes>
 **/
    ResPage<LotteryTypePageRes> lotteryTypePage(ReqPage<LotteryTypePageRes> reqBody);
}
