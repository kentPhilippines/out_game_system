package com.lt.win.backend.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicParameters;
import com.lt.win.backend.io.bo.Log;
import com.lt.win.backend.io.bo.PayManager;
import com.lt.win.backend.io.bo.user.*;
import com.lt.win.backend.io.bo.user.AgentCenterParameter.*;
import com.lt.win.backend.service.*;
import com.lt.win.dao.generator.po.SesRecord;
import com.lt.win.service.cache.redis.UserCache;
import com.lt.win.service.io.bo.UserBo;
import com.lt.win.service.io.bo.UserCacheBo;
import com.lt.win.utils.BeanConvertUtils;
import com.lt.win.utils.components.pagination.ReqPage;
import com.lt.win.utils.components.pagination.ResPage;
import com.lt.win.utils.components.response.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 会员管理
 * </p>
 *
 * @author andy
 * @since 2020/3/12
 */
@Slf4j
@RestController
@RequestMapping("/v1/user")
@Api(tags = "会员管理")
public class UserController {
    @Resource
    private IUserManagerService userManagerServiceImpl;
    @Resource
    private IUserBankBusinessService userBankBusinessServiceImpl;
    @Resource
    private ILogManagerService logManagerServiceImpl;
    @Resource
    private IComprehensiveChartService comprehensiveChartServiceImpl;
    @Resource
    private UserCache userCache;
    @Resource
    private IAgentCenterService agentCenterServiceImpl;


    @ApiOperationSupport(author = "David", order = 11)
    @ApiOperation(value = "会员管理-会员列表", notes = "会员管理-会员列表")
    @PostMapping("/list")
    public Result<ResPage<ListResBody>> list(@Valid @RequestBody ReqPage<ListReqBody> reqBody) {
        return Result.ok(userManagerServiceImpl.list(reqBody));
    }

    @ApiOperationSupport(author = "David", order = 12)
    @ApiOperation(value = "会员管理-会员详情", notes = "会员管理-会员详情")
    @PostMapping("/detail")
    public Result<DetailResBody> detail(@Valid @RequestBody DetailReqBody reqBody) {
        return Result.ok(userManagerServiceImpl.detail(reqBody));
    }

    @ApiOperationSupport(author = "Andy", order = 3)
    @ApiOperation(value = "会员列表-新增会员", notes = "查询会员详情")
    @PostMapping("/addUser")
    public Result<AddUserResBody> addUser(@Valid @RequestBody AddUserReqBody reqBody) {
        return Result.ok(userManagerServiceImpl.addUser(reqBody));
    }

    @ApiOperationSupport(author = "Andy", order = 4)
    @ApiOperation(value = "会员列表-修改会员", notes = "修改会员")
    @PostMapping("/updateUser")
    public Result<Boolean> updateUser(@Valid @RequestBody UpdateUserReqBody reqBody) {
        return Result.ok(userManagerServiceImpl.updateUser(reqBody));
    }

    @ApiOperationSupport(author = "Andy", order = 5)
    @ApiOperation(value = "会员管理-批量修改会员等级", notes = "会员管理-批量修改会员等级")
    @PostMapping("/updateBatchLevel")
    public Result<Boolean> updateBatchLevel(@Valid @RequestBody UpdateBatchLevelReqBody reqBody) {
        userManagerServiceImpl.updateBatchLevel(reqBody);
        return Result.ok(true);
    }

    @ApiOperationSupport(author = "Andy", order = 7)
    @ApiOperation(value = "会员列表-上级代理", notes = "会员列表-上级代理")
    @PostMapping("/supProxyList")
    public Result<List<SupProxyListReqBody>> supProxyList(@Valid @RequestBody SupProxyListReqBody reqBody) {
        List<SupProxyListReqBody> list = new ArrayList<>();
        if (null != reqBody) {
            list = userManagerServiceImpl.supProxyList(reqBody.getUid(), reqBody.getUsername());
        }
        return Result.ok(list);
    }

    @ApiOperationSupport(author = "Andy", order = 8)
    @ApiOperation(value = "会员列表-团队在线-人数", notes = "会员列表-团队在线-人数")
    @PostMapping("/listOnlineCount")
    public Result<ListOnlineCountResBody> listOnlineCount(@Valid @RequestBody ListOnlineReqBody reqBody) {
        return Result.ok(userManagerServiceImpl.listOnlineCount(reqBody));
    }

    @ApiOperationSupport(author = "Andy", order = 10)
    @ApiOperation(value = "会员列表-下级列表", notes = "会员列表-下级列表")
    @PostMapping("/listChild")
    public Result<ResPage<ListOnlineResBody>> listChild(@Valid @RequestBody ListOnlineReqBody reqBody) {
        return Result.ok(userManagerServiceImpl.listChild(reqBody));
    }

    @ApiOperationSupport(author = "Andy", order = 14)
    @ApiOperation(value = "会员等级管理-下拉列表", notes = "会员等级管理-下拉列表")
    @PostMapping("/getUserLevelList")
    public Result<List<PayManager.LevelBitBo>> getUserLevelList() {
        return Result.ok(BeanConvertUtils.copyListProperties(userCache.getUserLevelList(), PayManager.LevelBitBo::new));
    }

    @ApiOperationSupport(author = "wells", order = 25)
    @ApiOperation(value = "会员列表-稽核明细", notes = "会员列表-稽核明细")
    @PostMapping("/auditDetails")
    public Result<ResPage<AuditDetailsResBody>> auditDetails(@Valid @RequestBody ReqPage<CodeUidReqBody> reqBody) {
        return Result.ok(userManagerServiceImpl.auditDetails(reqBody));
    }


    @ApiOperationSupport(author = "Andy", order = 27)
    @ApiOperation(value = "会员管理-资金统计", notes = "会员管理-资金统计")
    @PostMapping("/capitalStatistics/list")
    public Result<CapitalStatisticsListResBody> listCapitalStatistics(@Valid @RequestBody CapitalStatisticsListReqBody reqBody) {
        reqBody.setUidList(List.of(reqBody.getUid()));
        CapitalStatisticsListResBody resBody = comprehensiveChartServiceImpl.userZH(reqBody);
        return Result.ok(resBody);
    }

    @ApiOperationSupport(author = "Andy", order = 31)
    @ApiOperation(value = "会员管理-会员登录日志", notes = "会员管理-会员登录日志:查询会员登录日志")
    @PostMapping("/listUserLoginLog")
    public Result<ResPage<Log.UserLoginLogInfoResBody>> listUserLoginLog(@RequestBody ReqPage<Log.UserLoginLogInfoReqBody> reqBody) {
        return Result.ok(logManagerServiceImpl.listUserLoginLog(reqBody));
    }

    @ApiOperationSupport(author = "Andy", order = 32)
    @ApiOperation(value = "会员管理-提款消费明细", notes = "会员管理-提款消费明细")
    @PostMapping("/listCodeRecords")
    public Result<ResPage<ListCodeRecords>> listCodeRecords(@Valid @RequestBody ListCodeRecordsReqBody reqBody) {
        return Result.ok(userManagerServiceImpl.listCodeRecords(reqBody));
    }

    @ApiOperation(value = "会员管理-清空提款消费量", notes = "会员管理-清空提款消费量")
    @PostMapping("/clearCodeRecords")
    @ApiOperationSupport(author = "Andy", order = 33,
            params = @DynamicParameters(properties = {
                    @DynamicParameter(name = "uid", value = "UID", example = "1", required = true, dataTypeClass = Integer.class)
            }))
    public Result<Boolean> clearCodeRecords(@RequestBody JSONObject reqBody) {
        int uid = -1;
        if (Objects.nonNull(reqBody)) {
            uid = reqBody.getInteger("uid");
        }
        userManagerServiceImpl.clearCodeRecords(uid);
        return Result.ok(true);
    }

    @ApiOperationSupport(author = "wells", order = 34)
    @ApiOperation(value = "代理中心-会员列表", notes = "代理中心-会员列表")
    @PostMapping("/agentUserList")
    public Result<ResPage<AgentUserListResBody>> agentUserList(@Valid @RequestBody ReqPage<AgentUserListReqBody> reqBody) {
        return Result.ok(agentCenterServiceImpl.agentUserList(reqBody));
    }

    @ApiOperationSupport(author = "Andy", order = 37)
    @ApiOperation(value = "会员管理-会员登录日志-修改备注", notes = "会员管理-会员登录日志-修改备注")
    @PostMapping("/updateUserLoginLog")
    public Result<Boolean> updateUserLoginLog(@Valid @RequestBody Log.UpdateUserLoginLogReqBody reqBody) {
        return Result.ok(logManagerServiceImpl.updateUserLoginLog(reqBody));
    }

    @ApiOperationSupport(author = "Andy", order = 38)
    @ApiOperation(value = "会员管理-验证码管理", notes = "会员管理-验证码管理")
    @PostMapping("/getVerifyCodeList")
    public Result<ResPage<SesRecord>> getVerifyCodeList(@RequestBody ReqPage<SmsCodeReqDto> reqBody) {
        return Result.ok(userManagerServiceImpl.getVerifyCodeList(reqBody));
    }

    @ApiOperationSupport(author = "Andy", order = 39)
    @ApiOperation(value = "在线人数->列表查询(分页)", notes = "在线人数->列表查询(分页)")
    @PostMapping("/onlineUserCountList")
    public Result<ResPage<OnlineUserCountListResBody>> onlineUserCountList(@Valid @RequestBody ReqPage<OnlineUserCountListReqBody> reqBody) {
        return Result.ok(userManagerServiceImpl.onlineUserCountList(reqBody));
    }

    @ApiOperationSupport(author = "wells", order = 40)
    @ApiOperation(value = "清除用户token与手机验证次数", notes = "清除用户token与手机验证次数")
    @PostMapping("/clearTokenCode")
    public Result<Boolean> clearTokenCode(@Valid @RequestBody ClearTokenCodeReqBody reqBody) {
        return Result.ok(userManagerServiceImpl.clearTokenCode(reqBody));
    }

    @ApiOperationSupport(author = "David", order = 71)
    @ApiOperation(value = "会员管理-验证码管理", notes = "会员管理-验证码管理")
    @PostMapping("/smsList")
    public Result<ResPage<SesRecord>> smsList(@RequestBody ReqPage<SmsCodeReqDto> reqBody) {
        return Result.ok(userManagerServiceImpl.getVerifyCodeList(reqBody));
    }

    @ApiOperationSupport(author = "David", order = 80)
    @ApiOperation(value = "会员等级管理-等级列表", notes = "会员等级管理-等级列表")
    @PostMapping("/listLevel")
    public Result<List<UserBo.ListLevelDto>> listLevel() {
        return Result.ok(userManagerServiceImpl.listLevel());
    }

    @ApiOperationSupport(author = "David", order = 81)
    @ApiOperation(value = "会员等级管理-修改等级", notes = "会员等级管理-修改等级")
    @PostMapping("/updateLevel")
    public Result<String> updateLevel(@Valid @RequestBody UserBo.ListLevelDto dto) {
        userManagerServiceImpl.updateLevel(dto);
        return Result.ok();
    }

    @ApiOperationSupport(author = "David", order = 90)
    @ApiOperation(value = "提款地址列表", notes = "提款地址列表")
    @PostMapping("/withdrawalAddressList")
    public Result<List<UserCacheBo.WithdrawalAddressResDto>> withdrawalAddressList(@Valid @RequestBody UserCacheBo.WithdrawalAddressReqDto dto) {
        return Result.ok(userBankBusinessServiceImpl.withdrawalAddressList(dto));
    }

    @ApiOperationSupport(author = "David", order = 91)
    @ApiOperation(value = "新增提款地址", notes = "新增提款地址")
    @PostMapping("/addWithdrawalAddress")
    public Result<String> addWithdrawalAddress(@Valid @RequestBody UserCacheBo.AddWithdrawalAddressReqDto dto) {
        userBankBusinessServiceImpl.addWithdrawalAddress(dto);
        return Result.ok();
    }

    @ApiOperationSupport(author = "David", order = 92)
    @ApiOperation(value = "修改提款地址", notes = "修改提款地址")
    @PostMapping("/updateWithdrawalAddress")
    public Result<String> updateWithdrawalAddress(@Valid @RequestBody UserCacheBo.UpdateWithdrawalAddressReq dto) {
        userBankBusinessServiceImpl.updateWithdrawalAddress(dto);
        return Result.ok();
    }

    @ApiOperationSupport(author = "David", order = 93)
    @ApiOperation(value = "删除提款地址", notes = "删除提款地址")
    @PostMapping("/deleteWithdrawalAddress")
    public Result<String> deleteWithdrawalAddress(@Valid @RequestBody UserCacheBo.DeleteWithdrawalAddressReqDto dto) {
        userBankBusinessServiceImpl.deleteWithdrawalAddress(dto);
        return Result.ok();
    }
}
