package com.lt.win.apiend.service;

import com.lt.win.apiend.io.bo.HomeParams;
import com.lt.win.apiend.io.dto.mapper.BannerReqDto;
import com.lt.win.apiend.io.dto.mapper.BannerResDto;
import com.lt.win.utils.components.pagination.ReqPage;
import com.lt.win.utils.components.pagination.ResPage;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author: David
 * @date: 06/04/2020
 * @description:
 */
public interface IHomeService {
    /**
     * Init 获取系统配置相关信息
     *
     * @return 出参
     */
    List<HomeParams.InitResDto> init(HttpServletRequest request);

    List<HomeParams.BannerListRes> getBannerList(HttpServletRequest request);
}
