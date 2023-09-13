package com.lt.win.backend.service;

import com.lt.win.backend.io.dto.System.*;
import com.lt.win.utils.components.pagination.ReqPage;
import com.lt.win.utils.components.pagination.ResPage;

import java.util.List;

/**
 * <p>
 * 系统设置
 * </p>
 *
 * @author andy
 * @since 2020/10/5
 */
public interface ISystemService {
    /**
     * Banner图 -> 列表查询
     *
     * @param reqBody reqBody
     * @return Banner图列表
     */
    ResPage<BannerListResBody> bannerList(ReqPage<BannerListReqBody> reqBody);

    /**
     * Banner图 -> 新增
     *
     * @param reqBody reqBody
     * @return true or false
     */
    boolean addBanner(AddBannerReqBody reqBody);

    /**
     * Banner图 -> 修改
     *
     * @param reqBody reqBody
     * @return true or false
     */
    boolean updateBanner(UpdateBannerReqBody reqBody);

    /**
     * Banner图 -> 删除
     *
     * @param reqBody reqBody
     * @return true or false
     */
    boolean delBanner(DelBannerReqBody reqBody);

    /**
     * Config init
     *
     * @return 初始化win_config表数据
     */
    List<InitResDto> init();

    /**
     * 查询佣金规则
     *
     * @return CommissionRule
     */
    CommissionRuleDto getCommissionRule();

    /**
     * 修改佣金规则
     *
     * @param reqDto 请求参数
     * @return Boolean
     */
    Boolean updateCommissionRule(CommissionRuleDto reqDto);

    /**
     * 查询平台配置
     *
     * @return RegisterLoginConfigDto
     */
    PlatConfigDto getPlatConfig();

    /**
     * 修改平台配置
     *
     * @param reqDto 修改参数
     * @return boolean
     */
    Boolean updatePlatConfig(PlatConfigDto reqDto);

    /**
     * 删除历史佣金记录-重新统计今日佣金记录
     */
    void resetCommission();
}
