package com.lt.win.apiend.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.lt.win.apiend.aop.annotation.UnCheckToken;
import com.lt.win.apiend.io.dto.StartEndTime;
import com.lt.win.apiend.service.impl.AgentServiceImpl;
import com.lt.win.service.io.bo.AgentReportBo;
import com.lt.win.service.io.bo.UserBo;
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
import java.util.List;

/**
 * * 代理中心
 *
 * @author david
 */
@Slf4j
@RestController
@RequestMapping("/v1/agent")
@Api(tags = "代理中心")
@ApiSort(6)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AgentController {
    private final AgentServiceImpl centerAgentStatisticsServiceImpl;

    @ApiOperationSupport(author = "David", order = 1)
    @ApiOperation(value = "代理中心-TeamBenefits", notes = "代理中心-TeamBenefits")
    @PostMapping("/teamBenefits")
    public Result<AgentReportBo.TeamBenefitsResDto> teamBenefits() {
        return Result.ok(centerAgentStatisticsServiceImpl.teamBenefits());
    }


    @ApiOperationSupport(author = "David", order = 2)
    @ApiOperation(value = "代理中心-佣金明细", notes = "代理中心-佣金明细")
    @PostMapping("/subordinateDetails")
    public Result<AgentReportBo.SubordinateDetailsResDto> subordinateDetails() {
        return Result.ok(centerAgentStatisticsServiceImpl.subordinateDetails());
    }

    @ApiOperationSupport(author = "David", order = 3)
    @ApiOperation(value = "代理中心-佣金流水", notes = "代理中心-佣金流水")
    @PostMapping("/commissionRecords")
    public Result<ResPage<AgentReportBo.CommissionRecordsResDto>> commissionRecords(@RequestBody @Valid ReqPage<StartEndTime> reqPage) {
        return Result.ok(centerAgentStatisticsServiceImpl.commissionRecords(reqPage));
    }

    @ApiOperationSupport(author = "David", order = 3)
    @ApiOperation(value = "代理中心-转账", notes = "代理中心-转账")
    @PostMapping("/coinTransfer")
    public Result<String> coinTransfer(@RequestBody @Valid AgentReportBo.CoinTransfer dto) {
        centerAgentStatisticsServiceImpl.coinTransfer(dto);
        return Result.ok();
    }

    @ApiOperationSupport(author = "David", order = 3)
    @ApiOperation(value = "代理中心-佣金列表", notes = "代理中心-佣金列表")
    @PostMapping("/commissionRateList")
    @UnCheckToken
    public Result<List<UserBo.AgentCommissionRateDto>> commissionRateList() {
        return Result.ok(centerAgentStatisticsServiceImpl.commissionRateList());
    }
}
