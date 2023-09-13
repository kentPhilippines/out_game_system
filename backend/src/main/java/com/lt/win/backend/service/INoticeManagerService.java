package com.lt.win.backend.service;

import com.lt.win.backend.io.bo.notice.*;
import com.lt.win.utils.components.pagination.ReqPage;
import com.lt.win.utils.components.pagination.ResPage;
import com.lt.win.backend.io.bo.notice.*;

/**
 * <p>
 * 公告管理
 * </p>
 *
 * @author andy
 * @since 2020/6/8
 */
public interface INoticeManagerService {
    /**
     * 公告管理-列表
     *
     * @param reqBody
     * @return
     */
    ResPage<ListNoticeResBody> list(ReqPage<ListNoticeReqBody> reqBody);

    /**
     * 公告管理-添加
     *
     * @param reqBody
     * @return
     */
    void add(AddNoticeReqBody reqBody);

    /**
     * 公告管理-编辑
     *
     * @param reqBody
     * @return
     */
    void update(UpdateNoticeReqBody reqBody);

    /**
     * 公告管理-删除
     *
     * @param reqBody
     * @return
     */
    void delete(DeleteNoticeReqBody reqBody);

    /**
     * 公告管理-查询UID(站内信)
     *
     * @param reqBody
     * @return
     */
    ResPage<UidListResBody> uidList(ReqPage<UidListReqBody> reqBody);
}
