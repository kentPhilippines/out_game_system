package com.lt.win.backend.service;

import com.lt.win.backend.io.dto.HelpTypeParams;
import com.lt.win.dao.generator.po.HelpType;

import java.util.List;

/**
 * @author: piking
 * @date: 2020/8/1
 */
public interface IHelpTypeService {

    /**
     * 分页
     */
    List<HelpType> page(HelpTypeParams.HelpTypeSelectReqDto helpTypeSelectReqDto);

    /**
     * 新增
     */
    boolean insert(HelpTypeParams.HelpTypeAddReqDto winHelpTypeAddReqDto);

    /**
     * 根据id更新
     */
    boolean update(HelpTypeParams.HelpTypeEditReqDto winHelpTypeEditReqDto);

    /**
     * 根据id删除
     */
    boolean deleteById(List<Integer> listId);


}
