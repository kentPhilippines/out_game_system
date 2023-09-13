package com.lt.win.apiend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lt.win.apiend.io.bo.HelpParams;
import com.lt.win.apiend.service.IHelpCenterService;
import com.lt.win.dao.generator.po.HelpInfo;
import com.lt.win.dao.generator.po.HelpType;
import com.lt.win.dao.generator.service.HelpInfoService;
import com.lt.win.dao.generator.service.HelpTypeService;
import com.lt.win.service.io.dto.BaseParams;
import com.lt.win.service.io.enums.LangEnum;
import com.lt.win.service.thread.ThreadHeaderLocalData;
import com.lt.win.utils.BeanConvertUtils;
import com.lt.win.utils.components.pagination.ReqPage;
import com.lt.win.utils.components.pagination.ResPage;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author: piking
 * @date: 08/01/2022
 * @description:
 */
@Service
public class IHelpCenterServiceImpl implements IHelpCenterService {

    @Resource
    private HelpTypeService helpTypeServiceImpl;
    @Resource
    private HelpInfoService helpInfoServiceImpl;


    @Override
    public ResPage<HelpParams.HelpTypeRespBody> pageHelpType(ReqPage reqBody) {
        BaseParams.HeaderInfo headerInfo = ThreadHeaderLocalData.HEADER_INFO_THREAD_LOCAL.get();
        LambdaQueryWrapper<HelpType> wrapper = Wrappers.lambdaQuery();
        if (ObjectUtils.isNotEmpty(headerInfo.getLang())) {
            wrapper.eq(HelpType::getLanguage, headerInfo.getLang());
        } else {
            wrapper.eq(HelpType::getLanguage, LangEnum.EN);
        }
        wrapper.eq(HelpType::getStatus, 1);
        Page<HelpType> page1 = helpTypeServiceImpl.page(reqBody.getPage(), wrapper);
        Page<HelpParams.HelpTypeRespBody> page = BeanConvertUtils.copyPageProperties(page1, HelpParams.HelpTypeRespBody::new);
        for (HelpParams.HelpTypeRespBody helpTypeRespBody : page.getRecords()) {
            HelpParams.HelpInfoPageReqDto helpInfoPageReqDto = new HelpParams.HelpInfoPageReqDto();
            helpInfoPageReqDto.setHelpTypeId(String.valueOf(helpTypeRespBody.getId()));

            ReqPage<HelpParams.HelpInfoPageReqDto> reqHelpInfoPage = new ReqPage<>();
            reqHelpInfoPage.setPage(1, 3);
            reqHelpInfoPage.setSortField(new String[]{"sort"});
            reqHelpInfoPage.setSortKey("DESC");
            reqHelpInfoPage.setData(helpInfoPageReqDto);
            ResPage<HelpParams.HelpInfoRespBody> helpInfoPage = pageHelpInfo(reqHelpInfoPage);
            helpTypeRespBody.setListHelpInfo(helpInfoPage.getList());
        }

        return ResPage.get(page);
    }

    @Override
    public ResPage<HelpParams.HelpInfoRespBody> pageHelpInfo(ReqPage<HelpParams.HelpInfoPageReqDto> reqBody) {
        BaseParams.HeaderInfo headerInfo = ThreadHeaderLocalData.HEADER_INFO_THREAD_LOCAL.get();

        LambdaQueryWrapper<HelpInfo> wrapper = Wrappers.lambdaQuery();
        if (ObjectUtils.isNotEmpty(headerInfo.getLang())) {
            wrapper.eq(HelpInfo::getLanguage, headerInfo.getLang());
        } else {
            wrapper.eq(HelpInfo::getLanguage, LangEnum.EN);
        }
        wrapper.eq(HelpInfo::getStatus, 1);
        if (ObjectUtils.isNotEmpty(reqBody.getData())) {
            wrapper.eq(Optional.ofNullable(reqBody.getData().getHelpTypeId()).isPresent(), HelpInfo::getHelpTypeId, reqBody.getData().getHelpTypeId());
            wrapper.eq(Optional.ofNullable(reqBody.getData().getUpdateBy()).isPresent(), HelpInfo::getUpdatedAt, reqBody.getData().getUpdateBy());
        }
        Page<HelpInfo> page1 = helpInfoServiceImpl.page(reqBody.getPage(), wrapper);
        Page<HelpParams.HelpInfoRespBody> page = BeanConvertUtils.copyPageProperties(page1, HelpParams.HelpInfoRespBody::new);
        return ResPage.get(page);
    }

    @Override
    public HelpParams.HelpInfoRespBody helpInfoById(HelpParams.HelpInfoReqDto reqBody) {
        HelpParams.HelpInfoRespBody helpInfoRespBody = new HelpParams.HelpInfoRespBody();
        HelpInfo helpInfo = helpInfoServiceImpl.getById(reqBody.getId());
        if (ObjectUtils.isNotEmpty(helpInfo)) {
            BeanUtils.copyProperties(helpInfo, helpInfoRespBody);
            HelpType helpType = helpTypeServiceImpl.getById(helpInfo.getHelpTypeId());
            helpInfoRespBody.setHelpTypeName(helpType.getTypeName());
        }
        return helpInfoRespBody;
    }


}
