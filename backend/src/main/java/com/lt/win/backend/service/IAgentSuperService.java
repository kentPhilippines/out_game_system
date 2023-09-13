package com.lt.win.backend.service;

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
     * 总后台-会员管理-代理列表
     *
     * @param dto dto
     * @return bo
     */
    ResPage<SuperAgentBo.ListResDto> list(ReqPage<SuperAgentBo.ListReqDto> dto);

    /**
     * 总后台-会员管理-代理列表-新增或修改
     *
     * @param dto dto
     */
    void saveOrUpdate(SuperAgentBo.SaveOrUpdateReqDto dto);

    /**
     * 总后台-报表中心-代理报表-列表
     *
     * @param dto dto
     * @return res
     */
    ResPage<SuperAgentBo.AgentListResDto> agentList(ReqPage<SuperAgentBo.AgentReportReqDto> dto);

    /**
     * 总后台-报表中心-代理报表-汇总
     *
     * @param dto dto
     * @return res
     */
    SuperAgentBo.AgentReportResDto agentReport(SuperAgentBo.AgentReportReqDto dto);

    /**
     * 总后台-报表中心-代理报表-代理转移
     *
     * @param dto dto
     */
    void agentTransfer(@Valid @RequestBody SuperAgentBo.AgentTransferReqDto dto);
}
