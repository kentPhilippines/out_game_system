package com.lt.win.backend.service.impl;

import com.lt.win.backend.io.dto.LotteryManagerParams.*;
import com.lt.win.backend.service.LotteryManagerService;
import com.lt.win.utils.components.pagination.ReqPage;
import com.lt.win.utils.components.pagination.ResPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: Jess
 * @Date: 2023/9/19 11:00
 * @Description:
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LotteryManagerServiceImpl implements LotteryManagerService {

    /**
     * @param reqBody
     * @return com.lt.win.utils.components.pagination.ResPage<com.lt.win.backend.io.dto.LotteryManagerParams.LotteryTypePageRes>
     * @Description 彩种列表
     * @Param [reqBody]
     */
    @Override
    public ResPage<LotteryTypePageRes> lotteryTypePage(ReqPage<LotteryTypePageRes> reqBody) {
        return null;
    }
}
