package com.lt.win.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lt.win.backend.io.dto.System.*;
import com.lt.win.backend.service.DailyReportService;
import com.lt.win.backend.service.ISystemService;
import com.lt.win.dao.generator.po.*;
import com.lt.win.dao.generator.service.*;
import com.lt.win.service.cache.KeyConstant;
import com.lt.win.service.cache.redis.BannerCache;
import com.lt.win.service.cache.redis.ConfigCache;
import com.lt.win.service.common.Constant;
import com.lt.win.service.io.dto.CoinLog;
import com.lt.win.service.io.dto.UserCoinChangeParams;
import com.lt.win.service.server.CoinLogService;
import com.lt.win.utils.BeanConvertUtils;
import com.lt.win.utils.DateNewUtils;
import com.lt.win.utils.JedisUtil;
import com.lt.win.utils.components.pagination.ReqPage;
import com.lt.win.utils.components.pagination.ResPage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.alibaba.fastjson.JSON.parseObject;
import static com.alibaba.fastjson.JSON.toJSONString;
import static com.lt.win.service.common.Constant.*;
import static java.util.Objects.nonNull;

/**
 * <p>
 * 系统设置
 * </p>
 *
 * @author andy
 * @since 2020/10/5
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SystemServiceImpl implements ISystemService {
    private final ConfigCache configCache;
    private final ConfigService configServiceImpl;
    private final JedisUtil jedisUtil;
    private final AgentCommissionRateService agentCommissionRateServiceImpl;
    private final DailyReportService dailyReportServiceImpl;
    private final CoinLogService coinLogServiceImpl;
    private final CoinCommissionService coinCommissionServiceImpl;
    private final UserService userServiceImpl;
    private final BannerCache bannerCache;
    private final BannerService bannerServiceImpl;


    /**
     * Banner图 -> 列表查询
     *
     * @param reqBody reqBody
     * @return Banner图列表
     */
    @Override
    public ResPage<BannerListResBody> bannerList(ReqPage<BannerListReqBody> reqBody) {
        var reqData = reqBody.getData();
        var page = bannerServiceImpl.page(reqBody.getPage(), new LambdaQueryWrapper<Banner>()
                .eq(Objects.nonNull(reqData.getDevice()), Banner::getDevice, reqData.getDevice())
                .eq(Objects.nonNull(reqData.getLanguage()), Banner::getLanguage, reqData.getLanguage())
        );
        var rePageResult = BeanConvertUtils.copyPageProperties(page, BannerListResBody::new);
        return ResPage.get(rePageResult);
    }

    /**
     * Banner图 -> 新增
     *
     * @param reqBody reqBody
     * @return true or false
     */
    @Override
    public boolean addBanner(AddBannerReqBody reqBody) {
        var banner = BeanConvertUtils.beanCopy(reqBody, Banner::new);
        var reFlag = bannerServiceImpl.save(banner);
        if (reFlag) {
            bannerCache.delBanner();
        }
        return reFlag;
    }

    /**
     * Banner图 -> 修改
     *
     * @param reqBody reqBody
     * @return true or false
     */
    @Override
    public boolean updateBanner(UpdateBannerReqBody reqBody) {
        var banner = BeanConvertUtils.beanCopy(reqBody, Banner::new);
        var reFlag = bannerServiceImpl.updateById(banner);
        if (reFlag) {
            bannerCache.delBanner();
        }
        return reFlag;
    }

    /**
     * Banner图 -> 删除
     *
     * @param reqBody reqBody
     * @return true or false
     */
    @Override
    public boolean delBanner(DelBannerReqBody reqBody) {
        var reFlag = bannerServiceImpl.removeById(reqBody.getId());
        if (reFlag) {
            bannerCache.delBanner();
        }
        return reFlag;
    }

    @Override
    public List<InitResDto> init() {
        // 类型: 1-前端 2-后台
        return configCache.getConfigCacheByShowApp(2)
                .stream()
                .map(o -> BeanConvertUtils.beanCopy(o, InitResDto::new))
                .collect(Collectors.toList());
    }

    /**
     * 查询佣金规则
     *
     * @return CommissionRule
     */
    @Override
    public CommissionRuleDto getCommissionRule() {
        var agentList = agentCommissionRateServiceImpl.list();
        var commissionRateList = BeanConvertUtils.copyListProperties(agentList, CommissionRateDto::new, (source, target) ->
                target.setAgentLevelRate(source.getAgentLevelRate().multiply(new BigDecimal(100)))
        );
        var commissionRule = parseObject(configCache.getConfigByTitle(Constant.COMMISSION_RULE)).toJavaObject(CommissionRuleDto.class);
        commissionRule.setCommissionRateList(commissionRateList);
        commissionRule.setDepositMultiple(new BigDecimal(configCache.getConfigByTitle(DEPOSIT_MULTIPLE)));
        commissionRule.setTransferMultiple(new BigDecimal(configCache.getConfigByTitle(TRANSFER_MULTIPLE)));
        commissionRule.setMinCoin(new BigDecimal(configCache.getConfigByTitle(MIN_COIN)));
        commissionRule.setMaxCoin(new BigDecimal(configCache.getConfigByTitle(MAX_COIN)));
        commissionRule.setFees(new BigDecimal(configCache.getConfigByTitle(FEES)));
        return commissionRule;
    }


    /**
     * 修改佣金规则
     *
     * @param reqDto 请求参数
     * @return Boolean
     */

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateCommissionRule(CommissionRuleDto reqDto) {
        var ruleJson = parseObject(configCache.getConfigByTitle(Constant.COMMISSION_RULE));
        var configJson = parseObject(toJSONString(reqDto));
        configJson.put(Constant.OLD_SETTLE_TYPE, ruleJson.getInteger(Constant.SETTLE_TYPE));
        configJson.put(Constant.OLD_EFFECT_TYPE, ruleJson.getInteger(Constant.EFFECT_TYPE));
        //生成生效日期
        ZonedDateTime zoneNow = DateNewUtils.utc8Zoned(DateNewUtils.now());
        var effectDate = DateNewUtils.utc8Str(zoneNow.plusDays(1), DateNewUtils.Format.yyyy_MM_dd);
        if (Constant.EFFECT_MONTH.equals(reqDto.getEffectType())) {
            var firstDay = zoneNow.plusMonths(1).with(TemporalAdjusters.firstDayOfMonth());
            effectDate = DateNewUtils.utc8Str(firstDay, DateNewUtils.Format.yyyy_MM_dd);
        }
        configJson.put(Constant.EFFECT_DATE, effectDate);
        var updateFlag = configServiceImpl.lambdaUpdate()
                .set(Config::getValue, configJson.toJSONString())
                .set(Config::getUpdatedAt, DateNewUtils.now())
                .eq(Config::getTitle, Constant.COMMISSION_RULE)
                .update();
        //修改佣金比例
        var commissionFlag = true;
        var commissionRateList = reqDto.getCommissionRateList();
        if (!CollectionUtils.isEmpty(commissionRateList)) {
            var agentRateList = BeanConvertUtils.copyListProperties(commissionRateList, AgentCommissionRate::new, (source, target) ->
                    target.setAgentLevelRate(source.getAgentLevelRate().divide(new BigDecimal(100), 4, RoundingMode.DOWN))
            );
            commissionFlag = agentCommissionRateServiceImpl.updateBatchById(agentRateList);
        }
        if (updateFlag) {
            jedisUtil.hdel(KeyConstant.CONFIG_HASH, Constant.COMMISSION_RULE);
        }
        //修改提款配置
        configServiceImpl.lambdaUpdate()
                .set(Config::getValue, reqDto.getMinCoin())
                .set(Config::getUpdatedAt, DateNewUtils.now())
                .eq(Config::getTitle, MIN_COIN)
                .update();
        configServiceImpl.lambdaUpdate()
                .set(Config::getValue, reqDto.getMaxCoin())
                .set(Config::getUpdatedAt, DateNewUtils.now())
                .eq(Config::getTitle, MAX_COIN)
                .update();
        configServiceImpl.lambdaUpdate()
                .set(Config::getValue, reqDto.getFees())
                .set(Config::getUpdatedAt, DateNewUtils.now())
                .eq(Config::getTitle, FEES)
                .update();
        //修改充值流水倍数
        var dFlag = configServiceImpl.lambdaUpdate()
                .set(Config::getValue, reqDto.getDepositMultiple())
                .set(Config::getUpdatedAt, DateNewUtils.now())
                .eq(Config::getTitle, Constant.DEPOSIT_MULTIPLE)
                .update();
        // 系统调账流水倍数
        var tFlag = configServiceImpl.lambdaUpdate()
                .set(Config::getValue, reqDto.getTransferMultiple())
                .set(Config::getUpdatedAt, DateNewUtils.now())
                .eq(Config::getTitle, Constant.TRANSFER_MULTIPLE)
                .update();
        jedisUtil.del(KeyConstant.CONFIG_HASH);
        return updateFlag && commissionFlag && dFlag && tFlag;
    }

    /**
     * 查询平台配置
     *
     * @return RegisterLoginConfigDto
     */
    @Override
    public PlatConfigDto getPlatConfig() {
        var platConfigDto = new PlatConfigDto();
        platConfigDto.setRegisterMobile(Integer.valueOf(configCache.getConfigByTitle(REGISTER_MOBILE)));
        platConfigDto.setRegisterInviteCode(Integer.valueOf(configCache.getConfigByTitle(REGISTER_INVITE_CODE)));
        platConfigDto.setLoginType(configCache.getConfigByTitle(LOGIN_TYPE));
        var verificationOfGoogle = configCache.getConfigByTitle(Constant.VERIFICATION_OF_GOOGLE);
        platConfigDto.setVerificationOfGoogle(verificationOfGoogle);
        platConfigDto.setTitle(configCache.getConfigByTitle(TITLE));
        platConfigDto.setPlatLogo(configCache.getConfigByTitle(PLAT_LOGO));
        platConfigDto.setWsServer(configCache.getConfigByTitle(WS_SERVER));
        platConfigDto.setSms(configCache.getConfigByTitle(SMS));
        return platConfigDto;
    }

    /**
     * 修改平台配置
     *
     * @param reqDto 修改参数
     * @return boolean
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updatePlatConfig(PlatConfigDto reqDto) {
        var updateMap = new HashMap<String, String>(16);
        updateMap.put(REGISTER_MOBILE, reqDto.getRegisterMobile().toString());
        updateMap.put(REGISTER_INVITE_CODE, reqDto.getRegisterInviteCode().toString());
        updateMap.put(LOGIN_TYPE, reqDto.getLoginType());
        updateMap.put(TITLE, reqDto.getTitle());
        updateMap.put(PLAT_LOGO, reqDto.getPlatLogo());
        updateMap.put(WS_SERVER, reqDto.getWsServer());
        updateMap.put(SMS, reqDto.getSms());
        updateMap.put(VERIFICATION_OF_GOOGLE, reqDto.getVerificationOfGoogle());
        //循环修改配置
        updateMap.forEach((key, value) ->
                configServiceImpl.lambdaUpdate()
                        .set(nonNull(value), Config::getValue, value)
                        .set(Config::getUpdatedAt, DateNewUtils.now())
                        .eq(Config::getTitle, key)
                        .update()
        );
        jedisUtil.del(KeyConstant.CONFIG_HASH);
        return true;
    }

    /**
     * 删除历史佣金记录-重新统计今日佣金记录
     */
    @Override
    public void resetCommission() {
        // 删除帐变表佣金记录
        coinLogServiceImpl.lambdaUpdate().eq(CoinLog::getCategory, UserCoinChangeParams.FlowCategoryTypeEnum.BROKERAGE.getCode()).remove();
        coinCommissionServiceImpl.lambdaUpdate().remove();
        // 清空佣金账户余额
        userServiceImpl.lambdaUpdate().set(User::getCoinCommission, BigDecimal.ZERO).update();
        // 重新生成今日佣金
        dailyReportServiceImpl.agentCommission(DateNewUtils.todayStart(), DateNewUtils.todayEnd());
    }
}
