package com.lt.win.apiend.service;

import com.lt.win.apiend.io.dto.StartEndTime;
import com.lt.win.service.io.bo.AgentReportBo;
import com.lt.win.service.io.bo.UserBo;
import com.lt.win.utils.components.pagination.ReqPage;
import com.lt.win.utils.components.pagination.ResPage;

import java.util.List;

/**
 * 代理中心业务处理接口
 *
 * @author David
 * @since 2022/8/22
 */
public interface IAgentService {

    /**
     * 代理中心-Benefits
     *
     * @return 报表总额统计实体
     */
    AgentReportBo.TeamBenefitsResDto teamBenefits();

    /**
     * 代理中心-下六级代理明细详情
     *
     * @return res
     */
    AgentReportBo.SubordinateDetailsResDto subordinateDetails();

    /**
     * 代理流水记录表
     *
     * @param reqPage dto
     * @return ResPage<AgentReportBo.TeamBenefitsResDto>>
     */
    ResPage<AgentReportBo.CommissionRecordsResDto> commissionRecords(ReqPage<StartEndTime> reqPage);

    /**
     * 佣金可提现金额转账
     *
     * @param dto dto
     */
    void coinTransfer(AgentReportBo.CoinTransfer dto);

    /**
     * 佣金层级列表
     *
     * @return res
     */
    List<UserBo.AgentCommissionRateDto> commissionRateList();
}
