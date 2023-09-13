package com.lt.win.apiend.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.lt.win.apiend.aop.annotation.UnCheckToken;
import com.lt.win.apiend.io.bo.UserParams;
import com.lt.win.apiend.io.dto.mapper.UserInfoResDto;
import com.lt.win.apiend.service.IAgentSuperService;
import com.lt.win.apiend.service.IUserInfoService;
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
@RequestMapping("/v1/agentSuper")
@Api(tags = "超级代理管理")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@ApiSort(6)
public class AgentSuperController {
    private final IAgentSuperService agentSuperServiceImpl;
    private final IUserInfoService userInfoServiceImpl;

    @UnCheckToken
    @ApiOperationSupport(author = "David", order = 10)
    @ApiOperation(value = "代理线-登陆", notes = "代理线-登陆")
    @PostMapping("/login")
    public Result<UserInfoResDto> login(@Valid @RequestBody UserParams.LoginReqDto dto) {
        return Result.ok(agentSuperServiceImpl.login(dto));
    }

    @PostMapping(value = "/refreshProfile")
    @ApiOperation(value = "代理线-获取个人信息", notes = "用户-获取个人信息")
    @ApiOperationSupport(author = "david", order = 11)
    public Result<UserInfoResDto> refreshProfile() {
        return Result.ok(userInfoServiceImpl.getProfile());
    }

    @PostMapping(value = "/resetPassword")
    @ApiOperation(value = "代理线-修改密码", notes = "用户-修改密码")
    @ApiOperationSupport(author = "david", order = 12)
    @UnCheckToken
    public Result<UserInfoResDto> resetPassword(@Valid @RequestBody SuperAgentBo.ResetPasswordReqDto dto) {
        return Result.ok(agentSuperServiceImpl.resetPassword(dto));
    }

    @ApiOperationSupport(author = "David", order = 20)
    @ApiOperation(value = "代理线-代理中心", notes = "总后台-报表中心")
    @PostMapping("/report")
    public Result<SuperAgentBo.WebAgentReportResDto> report(@Valid @RequestBody SuperAgentBo.WebAgentReportReqDto dto) {
        return Result.ok(agentSuperServiceImpl.report(dto));
    }

    @ApiOperationSupport(author = "David", order = 30)
    @ApiOperation(value = "代理线-团队详情-列表", notes = "代理线-团队详情-列表")
    @PostMapping("/teamList")
    public Result<ResPage<SuperAgentBo.AgentTeamListResDto>> teamList(@Valid @RequestBody ReqPage<SuperAgentBo.AgentTeamListReqDto> dto) {
        return Result.ok(agentSuperServiceImpl.teamList(dto));
    }

    @ApiOperationSupport(author = "David", order = 31)
    @ApiOperation(value = "代理线-团队详情--汇总", notes = "总后台-团队详情-汇总")
    @PostMapping("/teamSummary")
    public Result<SuperAgentBo.AgentReportResDto> teamSummary(@Valid @RequestBody SuperAgentBo.AgentReportReqDto dto) {
        return Result.ok(agentSuperServiceImpl.teamSummary(dto));
    }

    @ApiOperationSupport(author = "David", order = 40)
    @ApiOperation(value = "代理线-财务管理-投注记录", notes = "代理线-财务管理-投注记录")
    @PostMapping("/betList")
    public Result<ResPage<SuperAgentBo.BetListResDto>> betList(@Valid @RequestBody ReqPage<SuperAgentBo.BetListReqDto> dto) {
        return Result.ok(agentSuperServiceImpl.betList(dto));
    }

    @ApiOperationSupport(author = "David", order = 41)
    @ApiOperation(value = "代理线-财务管理-充值记录", notes = "代理线-财务管理-充值记录")
    @PostMapping("/depositList")
    public Result<ResPage<SuperAgentBo.DepositListResDto>> depositList(@Valid @RequestBody ReqPage<SuperAgentBo.DepositListReqDto> dto) {
        return Result.ok(agentSuperServiceImpl.depositList(dto));
    }

    @ApiOperationSupport(author = "David", order = 42)
    @ApiOperation(value = "代理线-财务管理-提现记录", notes = "代理线-财务管理-提现记录")
    @PostMapping("/withdrawalList")
    public Result<ResPage<SuperAgentBo.WithdrawalListResDto>> withdrawalList(@Valid @RequestBody ReqPage<SuperAgentBo.WithdrawalListReqDto> dto) {
        return Result.ok(agentSuperServiceImpl.withdrawalList(dto));
    }
}