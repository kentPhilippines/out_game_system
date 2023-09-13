package com.lt.win.backend.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lt.win.backend.base.DictionaryBase;
import com.lt.win.backend.io.dto.PromotionsParameter.*;
import com.lt.win.backend.service.IAdminInfoBase;
import com.lt.win.backend.service.IPromoService;
import com.lt.win.dao.generator.po.Blog;
import com.lt.win.dao.generator.po.CoinRewards;
import com.lt.win.dao.generator.po.Promotions;
import com.lt.win.dao.generator.service.BlogService;
import com.lt.win.dao.generator.service.CoinRewardsService;
import com.lt.win.dao.generator.service.PromotionsService;
import com.lt.win.service.cache.redis.ConfigCache;
import com.lt.win.service.cache.redis.PromotionsCache;
import com.lt.win.service.exception.BusinessException;
import com.lt.win.service.io.dto.BaseParams;
import com.lt.win.service.io.enums.LangEnum;
import com.lt.win.utils.BeanConvertUtils;
import com.lt.win.utils.DateUtils;
import com.lt.win.utils.components.pagination.ReqPage;
import com.lt.win.utils.components.pagination.ResPage;
import com.lt.win.utils.components.response.CodeInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;

import static com.alibaba.fastjson.JSON.parseObject;
import static java.util.Objects.nonNull;

/**
 * @author: David
 * @date: 2022/8/31
 * @description: 活动配置
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PromoServiceImpl implements IPromoService {
    //开始时间
    private final static String CREATED_AT = "created_at";
    private final PromotionsService promotionsServiceImpl;
    private final CoinRewardsService coinRewardsServiceImpl;
    private final PromotionsCache promotionsCache;
    private final ConfigCache configCache;
    private final DictionaryBase dictionaryBase;
    private final BlogService blogServiceImpl;

    /**
     * 活动列表
     *
     * @param dto 请求实体类
     * @return 响应实体类
     */
    @Override
    public ResPage<ListResDto> promotionsList(ReqPage<ListReqDto> dto) {
        var listReqDto = dto.getData();
        var lang = Optional.ofNullable(listReqDto.getLang()).orElse(LangEnum.EN.getValue());

        var page = promotionsServiceImpl.page(
                dto.getPage(),
                new LambdaQueryWrapper<Promotions>()
                        //活动标题
                        .like(nonNull(listReqDto.getCodeZh()), Promotions::getCodeZh, listReqDto.getCodeZh())
                        //活动类型
                        .eq(nonNull(listReqDto.getCategory()), Promotions::getCategory, listReqDto.getCategory())
                        //状态
                        .eq(nonNull(listReqDto.getStatus()), Promotions::getStatus, listReqDto.getStatus())
                        //开始时间
                        .ge(nonNull(listReqDto.getStartTime()), Promotions::getStartedAt, listReqDto.getStartTime())
                        //结束时间
                        .le(nonNull(listReqDto.getEndTime()), Promotions::getEndedAt, listReqDto.getEndTime())
                        //ID排序
                        .orderByAsc(Promotions::getId)
        );
        var resPage = BeanConvertUtils.copyPageProperties(page, ListResDto::new, (source, listResDto) -> {
                    listResDto.setCodeZh(parseObject(source.getCodeZh()).getString(lang));
                    listResDto.setImg(parseObject(source.getImg()).getString(lang));
                    listResDto.setDescription(parseObject(source.getDescript()).getString(lang));
                    JSONObject jsonObject = parseObject(source.getInfo());
                    listResDto.setFlowClaim(jsonObject.getInteger("flowClaim"));
                    listResDto.setMinCoin(jsonObject.getBigDecimal("minCoin"));
                    listResDto.setMaxCoin(jsonObject.getBigDecimal("maxCoin"));
                    listResDto.setRate(jsonObject.getBigDecimal("rate"));
                    listResDto.setDepositNums(jsonObject.getInteger("depositNums"));
                    listResDto.setSubCategory(jsonObject.getInteger("category"));
                    listResDto.setWeekdays(jsonObject.getInteger("weekdays"));
                }
        );
        return ResPage.get(resPage);
    }

    /**
     * 修改活动
     *
     * @param dto 请求参数实体类
     */
    @Override
    public void saveOrUpdatePromotions(SavaOrUpdateReqDto dto) {
        var now = DateUtils.getCurrentTime();
        var promotions = BeanConvertUtils.copyProperties(dto, Promotions::new, (source, promo) -> {
            promo.setDescript(source.getDescription());
            promo.setStartedAt(source.getStartTime());
            promo.setEndedAt(source.getEndTime());
        });
        promotions.setUpdatedAt(now);
        promotions.setCategory(dto.getCategory().toString());

        //新增重复判断
        if (Optional.ofNullable(dto.getId()).isEmpty()) {
            var codeCount = promotionsServiceImpl.lambdaQuery().eq(Promotions::getCode, dto.getCode()).count();
            if (codeCount > 0) {
                throw new BusinessException(CodeInfo.ACTIVE_CODE_REPEAT);
            }
            promotions.setCreatedAt(now);
            BinaryOperator<String> function = (key, value) -> {
                var json = new JSONObject();
                json.put(key, value);
                return json.toJSONString();
            };
            promotions.setCodeZh(function.apply(dto.getLang(), dto.getCodeZh()));
            promotions.setImg(function.apply(dto.getLang(), dto.getImg()));
            promotions.setDescript(function.apply(dto.getLang(), dto.getDescription()));
            JSONObject infoJson = new JSONObject();
            infoJson.put("flowClaim", dto.getFlowClaim());
            infoJson.put("rate", dto.getRate());
            promotions.setInfo(infoJson.toJSONString());
            promotionsServiceImpl.save(promotions);
            //修改重复判断
        } else {
            var codeZhCount = promotionsServiceImpl.lambdaQuery()
                    .like(Promotions::getCodeZh, dto.getCodeZh())
                    .ne(Promotions::getId, dto.getId())
                    .count();
            if (codeZhCount > 0) {
                throw new BusinessException(CodeInfo.ACTIVE_CODE_REPEAT);
            }
            var originPromotions = promotionsServiceImpl.getById(dto.getId());
            var codeZhJson = parseObject(originPromotions.getCodeZh());
            codeZhJson.put(dto.getLang(), dto.getCodeZh());
            var imgJson = parseObject(originPromotions.getImg());
            imgJson.put(dto.getLang(), dto.getImg());
            var descriptionJson = parseObject(originPromotions.getDescript());
            descriptionJson.put(dto.getLang(), dto.getDescription());

            promotions.setImg(JSON.toJSONString(imgJson));
            promotions.setCodeZh(JSON.toJSONString(codeZhJson));
            promotions.setDescript(JSON.toJSONString(descriptionJson));
            JSONObject infoJson = new JSONObject();
            infoJson.put("flowClaim", dto.getFlowClaim());
            infoJson.put("rate", dto.getRate());
            infoJson.put("validDays", 14);
            if (dto.getId() == 1) {
                infoJson.put("minCoin", dto.getMinCoin().intValue());
                infoJson.put("maxCoin", dto.getMaxCoin().intValue());
            } else if (dto.getId() == 2) {
                infoJson.put("minCoin", dto.getMinCoin().intValue());
                infoJson.put("maxCoin", dto.getMaxCoin().intValue());
                infoJson.put("depositNums", dto.getDepositNums());
            } else if (dto.getId() == 3) {
                infoJson.put("maxCoin", dto.getMaxCoin().intValue());
                infoJson.put("category", dto.getSubCategory());
            } else if (dto.getId() == 4) {
                infoJson.put("minCoin", dto.getMinCoin().intValue());
                infoJson.put("maxCoin", dto.getMaxCoin().intValue());
                infoJson.put("weekdays", dto.getWeekdays());
                infoJson.put("validDays", 7);
            }
            promotions.setInfo(infoJson.toJSONString());
            promotionsServiceImpl.updateById(promotions);
        }
        promotionsCache.refreshPromotions();
    }

    /**
     * 删除活动
     *
     * @param reqDto 请求参数实体类
     * @return 响应实体类
     */
    @Override
    public boolean deletePromotions(DeleteReqDto reqDto) {
        boolean b = promotionsServiceImpl.removeById(reqDto.getId());
        promotionsCache.refreshPromotions();
        return b;
    }

    /**
     * 根据语言获取活动信息
     *
     * @param reqDto :
     * @Return com.lt.win.backend.io.dto.PromotionsParameter.PromotionsByLangReqDto
     **/
    @Override
    public PromotionsByLangResDto getPromotionsByLang(PromotionsByLangReqDto reqDto) {
        var promotions = promotionsServiceImpl.getById(reqDto.getId());
        var langMsg = dictionaryBase.getCategoryMap("dic_accept_language").get(reqDto.getLang());
        var codeZh = parseObject(promotions.getCodeZh()).getString(reqDto.getLang());
        var img = parseObject(promotions.getImg()).getString(reqDto.getLang());
        var descript = parseObject(promotions.getDescript()).getString(reqDto.getLang());
        return PromotionsByLangResDto.builder().codeZh(codeZh).langMsg(langMsg).img(img).descript(descript).build();
    }

    /**
     * x
     * 平台语言列表
     *
     * @Return java.util.List<com.lt.win.backend.io.dto.PromotionsParameter.PlatLangResDto>
     **/
//    @Override
//    public List<PlatLangResDto> getPlatLang() {
//        var platLang = Optional.ofNullable(configCache.getConfigByTitle("lang")).orElse("");
//        var langArr = platLang.split(",");
//        var list = new ArrayList<PlatLangResDto>();
//        var langMap = dictionaryBase.getCategoryMap("dic_accept_language");
//        for (String lang : langArr) {
//            var langMsg = langMap.get(lang);
//            list.add(PlatLangResDto.builder().lang(lang).langMsg(langMsg).build());
//        }
//        return list;
//    }

    /**
     * 注册彩金列表
     *
     * @param reqDto 注册彩金列表请求实体类
     * @return registerRewardsListResDto
     */
    @Override
    public ResPage<RewardsListResDto> rewardsList(ReqPage<RewardsListReqDto> reqDto) {
        var reqData = reqDto.getData();
        var page = coinRewardsServiceImpl.page(
                reqDto.getPage(),
                new LambdaQueryWrapper<CoinRewards>()
                        // ID
                        .eq(nonNull(reqData.getId()), CoinRewards::getId, reqData.getId())
                        // 用户名
                        .eq(nonNull(reqData.getUsername()), CoinRewards::getUsername, reqData.getUsername())
                        // 状态
                        .eq(nonNull(reqData.getStatus()), CoinRewards::getStatus, reqData.getStatus())
                        // 开始时间
                        .ge(nonNull(reqData.getStartTime()), CoinRewards::getCreatedAt, reqData.getStartTime())
                        // 结束时间
                        .le(nonNull(reqData.getEndTime()), CoinRewards::getCreatedAt, reqData.getEndTime())
        );
        var resPage = BeanConvertUtils.copyPageProperties(page, RewardsListResDto::new);
        return ResPage.get(resPage);
    }

    /**
     * 博客列表
     *
     * @param dto dto
     * @return res
     */
    @Override
    public ResPage<Blog> blogList(ReqPage<BlogListReqDto> dto) {
        var data = dto.getData();
        return ResPage.get(blogServiceImpl.page(
                dto.getPage(),
                new LambdaQueryWrapper<Blog>()
                        .eq(Optional.ofNullable(data.getLang()).isPresent(), Blog::getLang, data.getLang())
                        .eq(Optional.ofNullable(data.getCategory()).isPresent(), Blog::getCategory, data.getCategory())
                        .eq(Optional.ofNullable(data.getStatus()).isPresent(), Blog::getStatus, data.getStatus())
                        .ge(Optional.ofNullable(data.getStartTime()).isPresent(), Blog::getUpdatedAt, data.getStartTime())
                        .le(Optional.ofNullable(data.getEndTime()).isPresent(), Blog::getUpdatedAt, data.getEndTime())
        ));
    }

    /**
     * 博客新增或修改
     *
     * @param dto dto
     */
    @Override
    public void blogSaveOrUpdate(BlogSaveOrUpdateReqDto dto) {
        var blog = BeanConvertUtils.beanCopy(dto, Blog::new);
        var now = DateUtils.getCurrentTime();
        blog.setCreatedAt(now);
        blog.setUpdatedAt(now);

        var header = IAdminInfoBase.getHeadLocalData();
        if (Optional.ofNullable(dto.getId()).isPresent()) {
            blog.setUpdateBy(header.getUsername());
        } else {
            blog.setCreateBy(header.getUsername());
        }
        blogServiceImpl.saveOrUpdate(blog);
    }

    /**
     * 博客删除
     *
     * @param dto dto
     */
    @Override
    public void blogDelete(BaseParams.IdParams dto) {
        blogServiceImpl.removeById(dto.getId());
    }

    /**
     * 获取活动总金额与列表
     *
     * @param reqDto 请求实体类
     * @return 响应实体类
     */
    public Pair<BigDecimal, Page<CoinRewards>> getCoinRewardPage(ReqPage<RewardsReqDto> reqDto) {
        var rewardsReqDto = reqDto.getData();
        var wrapper = new QueryWrapper<CoinRewards>()
                //ID
                .eq(nonNull(rewardsReqDto.getId()), "id", rewardsReqDto.getId())
                //UID
                .eq(nonNull(rewardsReqDto.getUid()), "uid", rewardsReqDto.getUid())
                //用户名
                .eq(nonNull(rewardsReqDto.getUsername()), "username", rewardsReqDto.getUsername())
                //关联ID
                .eq(nonNull(rewardsReqDto.getReferId()), "refer_id", rewardsReqDto.getReferId())
                //开始时间
                .ge(nonNull(rewardsReqDto.getStartTime()), CREATED_AT, rewardsReqDto.getStartTime())
                //结束时间
                .le(nonNull(rewardsReqDto.getEndTime()), CREATED_AT, rewardsReqDto.getEndTime());
        if (reqDto.getSortKey() != null) {
            wrapper.orderBy(reqDto.getSortField() != null, reqDto.getSortKey().equals("ASC"), reqDto.getSortField());
        } else {
            //按创建时间倒序
            wrapper.orderByDesc(CREATED_AT);
        }

        //求领取彩金
        List<CoinRewards> totalOne = coinRewardsServiceImpl.list(wrapper);
        var totalRewardsCoin = CollectionUtils.isEmpty(totalOne) ? BigDecimal.ZERO :
                totalOne.stream().map(CoinRewards::getCoin).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        Page<CoinRewards> page = coinRewardsServiceImpl.page(reqDto.getPage(), wrapper);
        return Pair.of(totalRewardsCoin, page);
    }

}
