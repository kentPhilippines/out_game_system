package com.lt.win.backend.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lt.win.backend.service.IAgentSuperService;
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
 * 超级代理-后台直接添加
 *
 * @author David
 * @since 2022/11/11
 */
@Slf4j
@RestController
@RequestMapping("/v1/superAgent")
@Api(tags = "超级代理管理")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AgentSuperController {
    private final IAgentSuperService agentSuperServiceImpl;


    @ApiOperationSupport(author = "David", order = 10)
    @ApiOperation(value = "总后台-会员管理-代理列表", notes = "总后台-会员管理-代理列表")
    @PostMapping("/list")
    public Result<ResPage<SuperAgentBo.ListResDto>> list(@Valid @RequestBody ReqPage<SuperAgentBo.ListReqDto> dto) {
        return Result.ok(agentSuperServiceImpl.list(dto));
    }

    @ApiOperationSupport(author = "David", order = 11)
    @ApiOperation(value = "总后台-会员管理-代理列表-新增或修改", notes = "总后台-会员管理-代理列表-新增或修改")
    @PostMapping("/saveOrUpdate")
    public Result<String> saveOrUpdate(@Valid @RequestBody SuperAgentBo.SaveOrUpdateReqDto dto) {
        agentSuperServiceImpl.saveOrUpdate(dto);
        return Result.ok();
    }

    @ApiOperationSupport(author = "David", order = 20)
    @ApiOperation(value = "总后台-报表中心-代理报表-列表", notes = "总后台-报表中心-代理报表-列表")
    @PostMapping("/agentList")
    public Result<ResPage<SuperAgentBo.AgentListResDto>> agentList(@Valid @RequestBody ReqPage<SuperAgentBo.AgentReportReqDto> dto) {
        return Result.ok(agentSuperServiceImpl.agentList(dto));
    }

    @ApiOperationSupport(author = "David", order = 21)
    @ApiOperation(value = "总后台-报表中心-代理报表-汇总", notes = "总后台-报表中心-代理报表-汇总")
    @PostMapping("/agentReport")
    public Result<SuperAgentBo.AgentReportResDto> agentReport(@Valid @RequestBody SuperAgentBo.AgentReportReqDto dto) {
        return Result.ok(agentSuperServiceImpl.agentReport(dto));
    }

    @ApiOperationSupport(author = "David", order = 22)
    @ApiOperation(value = "总后台-报表中心-代理报表-代理转移", notes = "总后台-报表中心-代理报表-代理转移")
    @PostMapping("/agentTransfer")
    public Result<String> agentTransfer(@Valid @RequestBody SuperAgentBo.AgentTransferReqDto dto) {
        agentSuperServiceImpl.agentTransfer(dto);
        return Result.ok();
    }
}