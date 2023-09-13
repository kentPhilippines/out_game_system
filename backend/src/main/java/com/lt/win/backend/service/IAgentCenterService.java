package com.lt.win.backend.service;

import com.lt.win.backend.io.bo.user.AgentCenterParameter.*;
import com.lt.win.service.io.bo.AgentReportBo;
import com.lt.win.utils.components.pagination.ReqPage;
import com.lt.win.utils.components.pagination.ResPage;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

/**
 * @author: wells
 * @date: 2020/8/27
 * @description:
 */

public interface IAgentCenterService {
    /**
     * 代理中心-会员列表
     *
     * @param reqBody
     * @return
     */
    ResPage<AgentUserListResBody> agentUserList(ReqPage<AgentUserListReqBody> reqBody);
}
