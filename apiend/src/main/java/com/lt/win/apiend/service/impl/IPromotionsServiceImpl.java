package com.lt.win.apiend.service.impl;

import com.alibaba.fastjson.JSON;
import com.lt.win.apiend.base.DictionaryBase;
import com.lt.win.apiend.io.dto.promotions.PromotionsApplyReqDto;
import com.lt.win.apiend.io.dto.promotions.PromotionsGroupResDto;
import com.lt.win.apiend.io.dto.promotions.PromotionsResDto;
import com.lt.win.apiend.service.IPromotionsService;
import com.lt.win.apiend.service.IUserInfoService;
import com.lt.win.dao.generator.po.CoinDepositRecord;
import com.lt.win.dao.generator.po.CoinRewards;
import com.lt.win.dao.generator.service.CoinDepositRecordService;
import com.lt.win.dao.generator.service.CoinRewardsService;
import com.lt.win.dao.generator.service.PromotionsService;
import com.lt.win.service.base.UserCoinBase;
import com.lt.win.service.cache.redis.CoinRewardCache;
import com.lt.win.service.cache.redis.PromotionsCache;
import com.lt.win.service.exception.BusinessException;
import com.lt.win.service.io.bo.PromotionsBo;
import com.lt.win.service.io.constant.ConstData;
import com.lt.win.service.io.dto.Betslips;
import com.lt.win.service.io.dto.Dictionary;
import com.lt.win.utils.BeanConvertUtils;
import com.lt.win.utils.DateNewUtils;
import com.lt.win.utils.components.response.CodeInfo;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.stereotype.Service;
//import org.zxp.esclientrhl.repository.ElasticsearchTemplate;

import javax.annotation.Resource;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.lt.win.service.common.Constant.DIGI_SPORT;


/**
 * Author: David
 * Date: 2022/8/20
 * Description: 优惠活动实现类
 *
 * @author david
 */
@Slf4j
@Service("iPromotionsServiceImpl")
public class IPromotionsServiceImpl implements IPromotionsService {
    @Resource
    IUserInfoService userInfoServiceImpl;
//    @Resource
//    ElasticsearchTemplate<Betslips, String> betslipsElasticsearchTemplate;
    @Resource
    private CoinRewardsService coinRewardsServiceImpl;
    @Resource
    private CoinDepositRecordService coinDepositRecordServiceImpl;
    @Resource
    private PromotionsCache promotionsCache;
    @Resource
    private PromotionsService promotionsServiceImpl;
    @Resource
    private CoinRewardCache coinRewardCache;
    @Resource
    private DictionaryBase dictionaryBase;
    @Resource
    private UserCoinBase userCoinBase;

    /**
     * 优惠活动->列表
     *
     * @return res
     */
    @Override
    public List<PromotionsGroupResDto> promotionsList() {
        var group = dictionaryBase.getDictionary("dic_promotion_group")
                .getOrDefault("dic_promotion_group", List.of());

        /* 活动组列表*/
        var groupSet = group.stream().map(Dictionary.ResDto::getCode).collect(Collectors.toSet());
        var userInfo = userInfoServiceImpl.findIdentityByApiToken();
        // 快乐周五时间范围
        var range = happyWeekdayRange();
        /* 活动列表 */
        List<PromotionsResDto> promotionsResDtos = promotionsCache.getPromotionsListCache().stream()
                .filter(promotions -> groupSet.contains(promotions.getCategory()))
                .map(o -> {
                    PromotionsResDto promotionsResDto = BeanConvertUtils.beanCopy(o, PromotionsResDto::new);
                    promotionsResDto.setImg(promotionsResDto.getImg());
                    if (Optional.ofNullable(userInfo).isEmpty()) {
                        promotionsResDto.setIsActivated(1);
                    } else {
                        var count = coinRewardsServiceImpl.lambdaQuery()
                                .eq(CoinRewards::getUid, userInfo.getId())
                                .eq(CoinRewards::getReferId, o.getId())
                                .between(o.getId() == 4, CoinRewards::getCreatedAt, range.getStart(), range.getEnd())
                                .count();
                        promotionsResDto.setIsActivated(count == 0 ? 1 : 0);
                    }

                    return promotionsResDto;
                }).collect(Collectors.toList());
        //活动分组，用于管理活动组
        var promotionsMap = promotionsResDtos.stream()
                .collect(Collectors.groupingBy(PromotionsResDto::getCategory));

        return BeanConvertUtils.copyListProperties(
                group,
                PromotionsGroupResDto::new,
                (ori, dest) -> {
                    dest.setCodeZh(ori.getTitle());
                    dest.setPromotionsResDtoList(promotionsMap.get(ori.getCode()));
                }
        );
    }

    /**
     * 活动申请规则
     */
    @Override
    public void promotionsApply(PromotionsApplyReqDto reqDto) {
        var user = userInfoServiceImpl.findIdentityByApiToken();
        // 获取活动记录
        var promo = promotionsServiceImpl.getById(reqDto.getId());
        if (Optional.ofNullable(promo).isEmpty()) {
            throw new BusinessException(CodeInfo.ACTIVE_APPLY_ID_INVALID);
        }
        var config = JSON.parseObject(promo.getInfo()).toJavaObject(PromotionsBo.Config.class);

        // 快乐周五时间范围
        var range = happyWeekdayRange();
        // 重复申请判定
        var count = coinRewardsServiceImpl.lambdaQuery()
                .eq(CoinRewards::getUid, user.getId())
                .eq(CoinRewards::getReferId, reqDto.getId())
                .between(reqDto.getId() == 4, CoinRewards::getCreatedAt, range.getStart(), range.getEnd())
                .count();
        if (count != 0) {
            throw new BusinessException(CodeInfo.ACTIVE_REPEAT_APPLY);
        }

        // 判定有没有未完成的活动(有未完成获得活动不能参与下一个活动) 0,1不是最终状态
        var joinNums = coinRewardsServiceImpl.lambdaQuery()
                .eq(CoinRewards::getUid, user.getId())
                .in(CoinRewards::getStatus, List.of(0, 1))
                .count();
        if (joinNums > 0) {
            throw new BusinessException(CodeInfo.ACTIVE_ONE_TIME_ONLY_APPLY_1);
        }

        switch (reqDto.getId()) {
            case 1:
                // 首充优惠
                var deposit = coinDepositRecordServiceImpl.lambdaQuery()
                        .eq(CoinDepositRecord::getUid, user.getId())
                        .eq(CoinDepositRecord::getStatus, 1)
                        .count();
                if (deposit > ConstData.ZERO) {
                    throw new BusinessException(CodeInfo.ACTIVE_FIRST_DEPOSIT_ALREADY_DEPOSIT);
                }
                break;
            case 2:
                count = coinDepositRecordServiceImpl.lambdaQuery()
                        .eq(CoinDepositRecord::getUid, user.getId())
                        .eq(CoinDepositRecord::getStatus, 1)
                        .count();
                if (count >= config.getDepositNums()) {
                    throw new BusinessException(CodeInfo.ACTIVE_DEPOSIT_NUM_OUT_OF_LIMIT);
                }
                break;
            case 3:
                try {
                    //首单包赔 限制申请前投注判断
                    if (config.getCategory() == 1) {
                        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
                        queryBuilder.must(QueryBuilders.termQuery("uid", user.getId()));
                        queryBuilder.must(QueryBuilders.termQuery("gameId", DIGI_SPORT));
                        queryBuilder.mustNot(QueryBuilders.termQuery("xbStatus", 3));
                    //    long betCount = betslipsElasticsearchTemplate.count(queryBuilder, Betslips.class);
//                        if (betCount > 0) {
//                            throw new BusinessException(CodeInfo.ACTIVE_FIRST_ORDER_EXCEPTION);
//                        }
                    }
                } catch (Exception e) {
                    log.error("首单包赔异常", e);
                }
            case 4:
            default:
                break;
        }
        var now = DateNewUtils.now();
        var coinRewards = new CoinRewards();
        coinRewards.setUid(user.getId());
        coinRewards.setUsername(user.getUsername());
        coinRewards.setCoinBefore(userCoinBase.getUserCoin(user.getId()));
        coinRewards.setReferId(promo.getId());
        coinRewards.setReferCode(promo.getCode());
        coinRewards.setFlowClaim(config.getFlowClaim());
        coinRewards.setInfo(promo.getInfo());
        coinRewards.setStatus(0);
        coinRewards.setCreatedAt(now);
        coinRewards.setUpdatedAt(now);
        coinRewardsServiceImpl.save(coinRewards);
        // 写入缓存
        coinRewards.setStatus(0);
        coinRewardCache.setCoinReward(coinRewards);
    }


    /**
     * 获取快乐周5活动申请时间范围
     *
     * @return res
     */
    private PromotionsBo.Range happyWeekdayRange() {
        var config = promotionsCache.getPromotionsConfig(4);

        // 快乐周五
        var startTime = ConstData.ZERO;
        var endTime = ConstData.ZERO;
        var time = ZonedDateTime.now().withHour(0).withMinute(0).withSecond(0);
        var indexOfWeek = time.getDayOfWeek().getValue();
        if (indexOfWeek >= config.getWeekdays()) {
            // 开始时间
            startTime = (int) time.minusDays(indexOfWeek - config.getWeekdays()).toInstant().getEpochSecond();
            endTime = startTime + 3600 * 24 * 7 - 1;
        } else {
            endTime = (int) time.plusDays(config.getWeekdays() - indexOfWeek + 1).toInstant().getEpochSecond() - 1;
            startTime = endTime - 3600 * 24 * 7 + 1;
        }

        return PromotionsBo.Range.builder()
                .start(startTime)
                .end(endTime)
                .build();
    }
}
