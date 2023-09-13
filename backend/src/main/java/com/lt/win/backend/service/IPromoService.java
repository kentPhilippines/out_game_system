package com.lt.win.backend.service;

import com.lt.win.backend.io.dto.PromotionsParameter.*;
import com.lt.win.dao.generator.po.Blog;
import com.lt.win.service.io.dto.BaseParams;
import com.lt.win.utils.components.pagination.ReqPage;
import com.lt.win.utils.components.pagination.ResPage;

import java.util.List;


/**
 * @author: David
 * @date: 2022/8/31
 * @description: 活动配置
 */
public interface IPromoService {
    /**
     * 活动列表
     *
     * @param dto dto
     * @return res
     */
    ResPage<ListResDto> promotionsList(ReqPage<ListReqDto> dto);

    /**
     * 修改活动
     *
     * @param dto dto
     */
    void saveOrUpdatePromotions(SavaOrUpdateReqDto dto);

    /**
     * 删除活动
     *
     * @param reqDto
     * @return
     */
    boolean deletePromotions(DeleteReqDto reqDto);

    /**
     * 根据语言获取活动信息
     *
     * @param reqDto :
     * @Return com.lt.win.backend.io.dto.PromotionsParameter.PromotionsByLangReqDto
     **/
    PromotionsByLangResDto getPromotionsByLang(PromotionsByLangReqDto reqDto);

//    /**
//     * 平台语言列表
//     *
//     * @Return java.util.List<com.lt.win.backend.io.dto.PromotionsParameter.PlatLangResDto>
//     **/
//    List<PlatLangResDto> getPlatLang();

    /**
     * 活动彩金列表
     *
     * @param dto dto
     * @return res
     */
    ResPage<RewardsListResDto> rewardsList(ReqPage<RewardsListReqDto> dto);

    /**
     * 博客列表
     *
     * @param dto dto
     * @return res
     */
    ResPage<Blog> blogList(ReqPage<BlogListReqDto> dto);

    /**
     * 博客新增或修改
     *
     * @param dto dto
     */
    void blogSaveOrUpdate(BlogSaveOrUpdateReqDto dto);

    /**
     * 博客删除
     *
     * @param dto dto
     */
    void blogDelete(BaseParams.IdParams dto);
}