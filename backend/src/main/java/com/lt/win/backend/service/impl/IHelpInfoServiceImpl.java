package com.lt.win.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lt.win.backend.io.bo.help.HelpInfoRespBody;
import com.lt.win.backend.io.dto.HelpInfoParams;
import com.lt.win.backend.service.IAdminInfoBase;
import com.lt.win.backend.service.IHelpInfoService;
import com.lt.win.dao.generator.po.HelpInfo;
import com.lt.win.dao.generator.po.HelpType;
import com.lt.win.dao.generator.service.HelpInfoService;
import com.lt.win.dao.generator.service.HelpTypeService;
import com.lt.win.service.io.dto.BaseParams;
import com.lt.win.utils.BeanConvertUtils;
import com.lt.win.utils.DateUtils;
import com.lt.win.utils.components.pagination.ReqPage;
import com.lt.win.utils.components.pagination.ResPage;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author: piking
 * @date: 2020/8/1
 */
@Service
public class IHelpInfoServiceImpl implements IHelpInfoService {

    @Resource
    private HelpInfoService helpInfoServiceImpl;
    @Resource
    private HelpTypeService helpTypeServiceImpl;

    /**
     * 分页
     */
    public ResPage<HelpInfoRespBody> page(ReqPage<HelpInfoParams.HelpInfoSelectReqDto> reqBody) {
        LambdaQueryWrapper<HelpInfo> wrapper = Wrappers.lambdaQuery();
        if (ObjectUtils.isNotEmpty(reqBody.getData())) {
            if (StringUtils.isNotBlank(reqBody.getData().getLanguage())) {
                wrapper.eq(HelpInfo::getLanguage, reqBody.getData().getLanguage());
            }
            if (StringUtils.isNotBlank(reqBody.getData().getHelpTypeId())) {
                wrapper.eq(HelpInfo::getHelpTypeId, reqBody.getData().getHelpTypeId());
            }
            if (ObjectUtils.isNotEmpty(reqBody.getData().getStatus())) {
                wrapper.eq(HelpInfo::getStatus, reqBody.getData().getStatus());
            }
            if (StringUtils.isNotBlank(reqBody.getData().getCreateBy())) {
                wrapper.like(HelpInfo::getCreateBy, reqBody.getData().getCreateBy());
            }
            if (StringUtils.isNotBlank(reqBody.getData().getUpdateBy())) {
                wrapper.like(HelpInfo::getUpdateBy, reqBody.getData().getUpdateBy());
            }
            if (Objects.nonNull(reqBody.getData().getStartTime())) {
                wrapper.ge(HelpInfo::getUpdatedAt, reqBody.getData().getStartTime());
            }
            if (Objects.nonNull(reqBody.getData().getEndTime())) {
                wrapper.le(HelpInfo::getUpdatedAt, reqBody.getData().getEndTime());
            }
        }
        Page<HelpInfo> page1 = helpInfoServiceImpl.page(reqBody.getPage(), wrapper);
        Page<HelpInfoRespBody> page = BeanConvertUtils.copyPageProperties(page1, HelpInfoRespBody::new);
        for (HelpInfoRespBody helpInfoRespBody : page.getRecords()) {
            HelpType helpType = helpTypeServiceImpl.getById(helpInfoRespBody.getHelpTypeId());
            if (ObjectUtils.isNotEmpty(helpType)) {
                helpInfoRespBody.setHelpTypeName(helpType.getTypeName());
                helpInfoRespBody.setImageUrl(helpType.getImageUrl());
            }
        }
        return ResPage.get(page);
    }


    /**
     * 新增
     */
    public boolean insert(HelpInfoParams.HelpInfoAddReqDto helpInfoAddReqDto) {
        BaseParams.HeaderInfo headLocalDate = IAdminInfoBase.getHeadLocalData();
        HelpInfo helpInfo = new HelpInfo();
        BeanUtils.copyProperties(helpInfoAddReqDto, helpInfo);
        helpInfo.setCreateBy(headLocalDate.getUsername());
        helpInfo.setUpdateBy(headLocalDate.getUsername());
        var now = DateUtils.getCurrentTime();
        helpInfo.setCreatedAt(now);
        helpInfo.setUpdatedAt(now);
        return helpInfoServiceImpl.save(helpInfo);
    }

    /**
     * 根据id更新
     */
    public boolean update(HelpInfoParams.HelpInfoEditReqDto helpInfoEditReqDto) {
        BaseParams.HeaderInfo headLocalDate = IAdminInfoBase.getHeadLocalData();
        HelpInfo helpInfo = new HelpInfo();
        BeanUtils.copyProperties(helpInfoEditReqDto, helpInfo);
        helpInfo.setUpdateBy(headLocalDate.getUsername());
        helpInfo.setUpdatedAt(DateUtils.getCurrentTime());
        return helpInfoServiceImpl.updateById(helpInfo);
    }

    /**
     * 根据id删除
     */
    public boolean deleteById(List<Integer> listId) {
        return helpInfoServiceImpl.removeByIds(listId);
    }


}
