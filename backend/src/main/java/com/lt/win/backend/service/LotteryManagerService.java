package com.lt.win.backend.service;

import com.lt.win.backend.io.dto.LotteryManagerParams.*;
import com.lt.win.utils.components.pagination.ReqPage;
import com.lt.win.utils.components.pagination.ResPage;
import com.lt.win.utils.components.response.CodeInfo;

import java.util.List;

/**
 * @Auther: Jess
 * @Date: 2023/9/19 11:00
 * @Description:
 */
public interface LotteryManagerService {
    /**
     * @return com.lt.win.utils.components.pagination.ResPage<com.lt.win.backend.io.dto.LotteryManagerParams.LotteryTypePageRes>
     * @Description 彩种列表
     * @Param [reqBody]
     **/
    ResPage<LotteryTypePageRes> lotteryTypePage(ReqPage<LotteryTypePageReq> reqBody);

    /**
     * @return java.lang.Boolean
     * @Description 新增彩种
     * @Param [reqBody]
     **/
    Boolean addLotteryType(AddLotteryTypeReq reqBody);

    /**
     * @return java.lang.Boolean
     * @Description 修改彩种
     * @Param [reqBody]
     **/
    Boolean updateLotteryType(UpdateLotteryTypeReq reqBody);

    /**
     * @return java.lang.Boolean
     * @Description 删除彩种
     * @Param [reqBody]
     **/
    Boolean deleteLotteryType(DeleteLotteryTypeReq reqBody);

    /**
     * @return com.lt.win.utils.components.pagination.ResPage<com.lt.win.backend.io.dto.LotteryManagerParams.LotteryTypePageRes>
     * @Description 查询主板列表
     * @Param [reqBody]
     **/
    ResPage<MainPlatePageRes> mainPlatePage(ReqPage<MainPlatePageReq> reqBody);

    /***
     * @Description
     * @Param [reqBody]新增主板列表
     * @return java.lang.Boolean
     **/
    Boolean addMainPlate(AddMainPlateReq reqBody);

    /***
     * @Description
     * @Param [reqBody]修改主板列表
     * @return java.lang.Boolean
     **/
    Boolean updateMainPlate(UpdateMainPlateReq reqBody);

    /***
     * @Description
     * @Param [reqBody]删除主板列表
     * @return java.lang.Boolean
     **/
    Boolean deleteMainPlate(DeleteMainPlateReq reqBody);

    /**
     * @return com.lt.win.utils.components.pagination.ResPage<com.lt.win.backend.io.dto.LotteryManagerParams.PlatePageRes>
     * @Description
     * @Param [reqBody]
     **/
    ResPage<PlatePageRes> platePage(ReqPage<PlatePageReq> reqBody);

    /**
     * @return java.lang.Boolean
     * @Description
     * @Param [reqBody]
     **/
    Boolean addPlate(AddPlateReq reqBody);

    /**
     * @return java.lang.Boolean
     * @Description
     * @Param [reqBody]
     **/
    Boolean updatePlate(UpdatePlateReq reqBody);

    /***
     * @Description
     * @Param [reqBody]
     * @return java.lang.Boolean
     **/
    Boolean deletePlate(DeletePlateReq reqBody);

    /**
     * @return com.lt.win.utils.components.pagination.ResPage<com.lt.win.backend.io.dto.LotteryManagerParams.OpenPageRes>
     * @Description 开奖结果列表
     * @Param [reqBody]
     **/
    ResPage<OpenPageRes> openPage(ReqPage<OpenPageReq> reqBody);

    /**
     * @return java.lang.Boolean
     * @Description 新增开奖结果
     * @Param [reqBody]
     **/
    Boolean addOpen(AddOpenReq reqBody);

    /**
     * @return java.lang.Boolean
     * @Description 修改开奖结果
     * @Param [reqBody]
     **/
    Boolean updateOpen(UpdateOpenReq reqBody);

    /***
     * @Description 删除开奖结果
     * @Param [reqBody]
     * @return java.lang.Boolean
     **/
    Boolean deleteOpen(DeleteOpenReq reqBody);

    /**
     * @return com.lt.win.utils.components.pagination.ResPage<com.lt.win.backend.io.dto.LotteryManagerParams.BetRecordRes>
     * @Description 注单列表
     * @Param [reqPage]
     **/
    ResPage<BetRecordRes> betPage(ReqPage<BetRecordReq> reqPage);

    /**
     * @return java.util.List<com.lt.win.backend.io.dto.LotteryManagerParams.MainPlateRes>
     * @Description 查询主板记录
     * @Param []
     **/
    List<MainPlateRes> queryMainPlateList();

    /**
     * @return java.util.List<com.lt.win.backend.io.dto.LotteryManagerParams.PlateRes>
     * @Description 查询板块记录
     * @Param [req]
     **/
    List<PlateRes> queryPlateList(PlateReq req);

    /**
     * @return com.lt.win.backend.io.dto.LotteryManagerParams.PlateBetStatisticsRes
     * @Description 每期板块投注统计
     * @Param [req]
     **/
    PlateBetStatisticsRes plateBetStatistics(PlateBetStatisticsReq req);

    /**
     * @return java.lang.Boolean
     * @Description 开奖结果-结算
     * @Param [req]
     **/
    Boolean openSettle(OpenSettleReq req);

    /**
     * @return java.lang.Boolean
     * @Description 注单记录-结算
     * @Param [req]
     **/
    Boolean betSettle(BetSettleReq req);
}
