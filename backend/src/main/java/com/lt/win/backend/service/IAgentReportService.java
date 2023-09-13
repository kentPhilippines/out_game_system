package com.lt.win.backend.service;

import com.lt.win.backend.io.bo.user.AgentCenterParameter;
import com.lt.win.service.io.bo.AgentReportBo;
import com.lt.win.service.io.bo.SuperAgentBo;
import com.lt.win.utils.components.pagination.ReqPage;
import com.lt.win.utils.components.pagination.ResPage;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * <p>
 * 会员管理 接口
 * </p>
 *
 * @author andy
 * @since 2020/3/12
 */
public interface IAgentReportService {
    /**
     * 报表管理 -> 代理报表 -> 列表
     *
     * @param dto dto
     * @return bo
     */
    ResPage<SuperAgentBo.AgentListResDto> agent(ReqPage<AgentReportBo.AgentReqDto> dto);

    /**
     * 报表管理 -> 代理报表 ->  转移
     *
     * @param dto dto
     */
    void agentTransfer(AgentReportBo.AgentTransferReqDto dto);

    /**
     * 报表管理 -> 代理报表 -> 团队详情 -> 列表
     *
     * @param dto dto
     * @return bo
     */
    ResPage<AgentReportBo.AgentTeamResDto> agentTeam(ReqPage<AgentReportBo.AgentTeamReqDto> dto);

    /**
     * 报表管理 -> 代理报表 -> 团队详情 -> 汇总
     *
     * @param dto dto
     * @return bo
     */
    AgentReportBo.MemberOrAgentTeamSummaryResDto agentTeamSummary(@Valid @RequestBody AgentReportBo.AgentTeamReqDto dto);

    /**
     * 代理报表 -> 佣金收益
     *
     * @param dto dto
     * @return res
     */
    AgentCenterParameter.AgentCommissionResBody agentCommissionProfit(ReqPage<AgentCenterParameter.AgentCommissionReqBody> dto);

}
