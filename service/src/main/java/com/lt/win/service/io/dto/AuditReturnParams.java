package com.lt.win.service.io.dto;

import com.lt.win.dao.generator.po.CoinAdminTransfer;
import com.lt.win.dao.generator.po.CoinDepositRecord;
import com.lt.win.dao.generator.po.CoinRewards;
import com.lt.win.service.io.bo.AgentReportBo;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Auther: wells
 * @Date: 2022/8/29 21:22
 * @Description:
 */
@Data
public class AuditReturnParams {

    private BigDecimal codeRequire = BigDecimal.ZERO;

    private BigDecimal codeReal = BigDecimal.ZERO;

    private List<CoinDepositRecord> coinDepositRecordList;

    private List<CoinRewards> coinRewardsList;

    private List<CoinAdminTransfer> coinAdminTransferList;

    boolean reFlag = false;
}
