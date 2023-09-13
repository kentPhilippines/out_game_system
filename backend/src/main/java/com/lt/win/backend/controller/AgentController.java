package com.lt.win.backend.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lt.win.backend.io.bo.user.AgentCenterParameter;
import com.lt.win.backend.service.IAgentReportService;
import com.lt.win.service.io.bo.AgentReportBo;
import com.lt.win.service.io.bo.SuperAgentBo;
import com.lt.win.utils.components.pagination.ReqPage;
import com.lt.win.utils.components.pagination.ResPage;
import com.lt.win.utils.components.response.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 代理管理
 *
 * @author David
 * @since 2022/8/26
 */
@Slf4j
@RestController
@RequestMapping("/v1/agent")
@Api(tags = "代理管理")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AgentController {
    private final IAgentReportService agentReportServiceImpl;

    @ApiOperationSupport(author = "David", order = 10)
    @ApiOperation(value = "代理报表-列表", notes = "代理报表-列表")
    @PostMapping("/agent")
    public Result<ResPage<SuperAgentBo.AgentListResDto>> agent(@Valid @RequestBody ReqPage<AgentReportBo.AgentReqDto> dto) {
        return Result.ok(agentReportServiceImpl.agent(dto));
    }

    @ApiOperationSupport(author = "David", order = 12)
    @ApiOperation(value = "代理报表-代理转移", notes = "代理报表-代理转移")
    @PostMapping("/agentTransfer")
    public Result<String> agentTransfer(@Valid @RequestBody AgentReportBo.AgentTransferReqDto dto) {
        agentReportServiceImpl.agentTransfer(dto);
        return Result.ok();
    }

    @ApiOperationSupport(author = "David", order = 13)
    @ApiOperation(value = "代理报表-团队详情-列表", notes = "代理报表-团队详情-列表")
    @PostMapping("/agentTeam")
    public Result<ResPage<AgentReportBo.AgentTeamResDto>> agentTeam(@Valid @RequestBody ReqPage<AgentReportBo.AgentTeamReqDto> dto) {
        return Result.ok(agentReportServiceImpl.agentTeam(dto));
    }

    @ApiOperationSupport(author = "David", order = 15)
    @ApiOperation(value = "代理报表-团队详情-汇总", notes = "代理报表-团队详情-汇总")
    @PostMapping("/agentTeamSummary")
    public Result<AgentReportBo.MemberOrAgentTeamSummaryResDto> agentTeamSummary(@Valid @RequestBody AgentReportBo.AgentTeamReqDto dto) {
        return Result.ok(agentReportServiceImpl.agentTeamSummary(dto));
    }

    @ApiOperationSupport(author = "David", order = 21)
    @ApiOperation(value = "代理中心-佣金收益", notes = "代理中心-佣金收益")
    @PostMapping("/agentCommissionProfit")
    public Result<AgentCenterParameter.AgentCommissionResBody> agentCommissionProfit(@Valid @RequestBody ReqPage<AgentCenterParameter.AgentCommissionReqBody> dto) {
        return Result.ok(agentReportServiceImpl.agentCommissionProfit(dto));
    }

}