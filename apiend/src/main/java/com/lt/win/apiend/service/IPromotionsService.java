package com.lt.win.apiend.service;

import com.lt.win.apiend.io.dto.promotions.PromotionsApplyReqDto;
import com.lt.win.apiend.io.dto.promotions.PromotionsGroupResDto;

import java.util.List;

/**
 * @author: wells
 * @date: 2020/4/14
 * @description:优惠活动
 */

public interface IPromotionsService {
    /**
     * 优惠活动->列表
     *
     * @return res
     */
    List<PromotionsGroupResDto> promotionsList();

    /**
     * 优惠活动->详情
     *
     * @param reqDto dto
     */
    void promotionsApply(PromotionsApplyReqDto reqDto);

}
