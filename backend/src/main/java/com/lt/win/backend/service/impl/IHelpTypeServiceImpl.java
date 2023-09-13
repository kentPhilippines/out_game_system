package com.lt.win.backend.service.impl;

import com.lt.win.backend.io.dto.HelpTypeParams;
import com.lt.win.backend.service.IAdminInfoBase;
import com.lt.win.backend.service.IHelpTypeService;
import com.lt.win.dao.generator.po.HelpType;
import com.lt.win.dao.generator.service.HelpTypeService;
import com.lt.win.service.io.dto.BaseParams;
import com.lt.win.utils.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @author: piking
 * @date: 2020/8/1
 */
@Service
public class IHelpTypeServiceImpl implements IHelpTypeService {

    @Resource
    private HelpTypeService helpTypeServiceImpl;

    /**
     * 分页
     */
    public List<HelpType> page(HelpTypeParams.HelpTypeSelectReqDto dto) {
        return helpTypeServiceImpl.lambdaQuery()
                .eq(Optional.ofNullable(dto.getLanguage()).isPresent(), HelpType::getLanguage, dto.getLanguage())
                .like(Optional.ofNullable(dto.getTypeName()).isPresent(), HelpType::getTypeName, dto.getTypeName())
                .eq(Optional.ofNullable(dto.getStatus()).isPresent(), HelpType::getStatus, dto.getStatus())
                .like(Optional.ofNullable(dto.getCreateBy()).isPresent(), HelpType::getCreateBy, dto.getCreateBy())
                .ge(Optional.ofNullable(dto.getStartTime()).isPresent(), HelpType::getUpdatedAt, dto.getStartTime())
                .le(Optional.ofNullable(dto.getEndTime()).isPresent(), HelpType::getUpdatedAt, dto.getEndTime())
                .list();
    }

    /**
     * 新增
     */
    public boolean insert(HelpTypeParams.HelpTypeAddReqDto helpTypeAddReqDto) {
        BaseParams.HeaderInfo headLocalDate = IAdminInfoBase.getHeadLocalData();
        HelpType winHelpType = new HelpType();
        BeanUtils.copyProperties(helpTypeAddReqDto, winHelpType);
        winHelpType.setCreateBy(headLocalDate.getUsername());
        winHelpType.setUpdateBy(headLocalDate.getUsername());
        var now = DateUtils.getCurrentTime();
        winHelpType.setCreatedAt(now);
        winHelpType.setUpdatedAt(now);
        return helpTypeServiceImpl.save(winHelpType);
    }

    /**
     * 根据id更新
     */
    public boolean update(HelpTypeParams.HelpTypeEditReqDto helpTypeEditReqDto) {
        BaseParams.HeaderInfo headLocalDate = IAdminInfoBase.getHeadLocalData();
        HelpType winHelpType = new HelpType();
        BeanUtils.copyProperties(helpTypeEditReqDto, winHelpType);
        winHelpType.setUpdateBy(headLocalDate.getUsername());
        winHelpType.setUpdatedAt(DateUtils.getCurrentTime());
        return helpTypeServiceImpl.updateById(winHelpType);
    }

    /**
     * 根据id删除
     */
    public boolean deleteById(List<Integer> listId) {
        return helpTypeServiceImpl.removeByIds(listId);
    }

}
