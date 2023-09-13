package com.lt.win.apiend.service;

import com.lt.win.apiend.io.bo.HelpParams;
import com.lt.win.utils.components.pagination.ReqPage;
import com.lt.win.utils.components.pagination.ResPage;

public interface IHelpCenterService {

    /**
     * 帮助类型分页
     */
    ResPage<HelpParams.HelpTypeRespBody> pageHelpType(ReqPage reqBody);

    /**
     * 帮助详情分页
     */
    ResPage<HelpParams.HelpInfoRespBody> pageHelpInfo(ReqPage<HelpParams.HelpInfoPageReqDto> reqBody);

    /**
     * 帮助详情内容
     */
    HelpParams.HelpInfoRespBody helpInfoById(HelpParams.HelpInfoReqDto reqBody);


}
