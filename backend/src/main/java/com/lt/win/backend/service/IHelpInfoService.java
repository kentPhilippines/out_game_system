package com.lt.win.backend.service;

import com.lt.win.backend.io.bo.help.HelpInfoRespBody;
import com.lt.win.backend.io.dto.HelpInfoParams;
import com.lt.win.utils.components.pagination.ReqPage;
import com.lt.win.utils.components.pagination.ResPage;

import java.util.List;

/**
 * @author: piking
 * @date: 2020/8/1
 */
public interface IHelpInfoService {

    /**
     * 分页
     */
    ResPage<HelpInfoRespBody> page(ReqPage<HelpInfoParams.HelpInfoSelectReqDto> helpInfoSelectReqDto);

    /**
     * 新增
     */
    public boolean insert(HelpInfoParams.HelpInfoAddReqDto helpInfoAddReqDto);

    /**
     * 根据id更新
     */
    public boolean update(HelpInfoParams.HelpInfoEditReqDto helpInfoEditReqDto);

    /**
     * 根据id删除
     */
    public boolean deleteById(List<Integer> listId);





}
