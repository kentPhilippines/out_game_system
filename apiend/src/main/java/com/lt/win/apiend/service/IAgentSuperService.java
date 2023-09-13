package com.lt.win.apiend.service;

import com.lt.win.apiend.io.bo.UserParams;
import com.lt.win.apiend.io.dto.mapper.UserInfoResDto;
import com.lt.win.service.io.bo.SuperAgentBo;
import com.lt.win.utils.components.pagination.ReqPage;
import com.lt.win.utils.components.pagination.ResPage;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * 超级代理-后台直接添加
 *
 * @author David
 * @since 2022/11/11
 */
public interface IAgentSuperService {

    /**
     * 代理线-登陆
     *
     * @param dto dto
     * @return res
     */
    UserInfoResDto login(@Valid @RequestBody UserParams.LoginReqDto dto);

    /**
     * 代理线-重置密码
     *
     * @param dto dto
     * @return res
     */
    UserInfoResDto resetPassword(SuperAgentBo.ResetPasswordReqDto dto);

    /**
     * 代理线-代理中心-数据展示
     *
     * @param dto dto
     * @return res
     */
    SuperAgentBo.WebAgentReportResDto report(SuperAgentBo.WebAgentReportReqDto dto);

    /**
     * 代理线-团队详情-列表
     *
     * @param dto dto
     * @return res
     */
    ResPage<SuperAgentBo.AgentTeamListResDto> teamList(@Valid @RequestBody ReqPage<SuperAgentBo.AgentTeamListReqDto> dto);

    /**
     * 代理线-团队详情-汇总
     *
     * @param dto dto
     * @return res
     */
    SuperAgentBo.AgentReportResDto teamSummary(@Valid @RequestBody SuperAgentBo.AgentReportReqDto dto);

    /**
     * 代理线-财务管理-投注记录
     *
     * @param dto dto
     * @return res
     */
    ResPage<SuperAgentBo.BetListResDto> betList(@Valid @RequestBody ReqPage<SuperAgentBo.BetListReqDto> dto);

    /**
     * 代理线-财务管理-充值记录
     *
     * @param dto dto
     * @return res
     */
    ResPage<SuperAgentBo.DepositListResDto> depositList(@Valid @RequestBody ReqPage<SuperAgentBo.DepositListReqDto> dto);

    /**
     * 代理线-财务管理-提现记录
     *
     * @param dto dto
     * @return res
     */
    ResPage<SuperAgentBo.WithdrawalListResDto> withdrawalList(@Valid @RequestBody ReqPage<SuperAgentBo.WithdrawalListReqDto> dto);
}
