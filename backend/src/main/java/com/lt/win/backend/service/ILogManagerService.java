package com.lt.win.backend.service;

import com.lt.win.backend.io.bo.Log;
import com.lt.win.utils.components.pagination.ReqPage;
import com.lt.win.utils.components.pagination.ResPage;

/**
 * <p>
 * 日志管理接口
 * </p>
 *
 * @author andy
 * @since 2020/6/5
 */
public interface ILogManagerService {
    /**
     * 会员管理-登录日志
     *
     * @param reqBody
     * @return
     */
    ResPage<Log.UserLoginLogInfoResBody> listUserLoginLog(ReqPage<Log.UserLoginLogInfoReqBody> reqBody);

    /**
     * 后台管理-登录日志
     *
     * @param reqBody
     * @return
     */
    ResPage<Log.LogInfo> listAdminLoginLog(ReqPage<Log.LogInfoReqBody> reqBody);

    /**
     * 后台管理-操作日志
     *
     * @param reqBody
     * @return
     */
    ResPage<Log.AdminOperateLog> listAdminOperateLog(ReqPage<Log.AdminOperateLogReqBody> reqBody);

    Boolean delCache();

    Log.OnLineNum getOnLineNum();

    /**
     * 会员管理-会员登录日志-修改备注
     *
     * @param reqBody
     * @return true or false
     */
    boolean updateUserLoginLog(Log.UpdateUserLoginLogReqBody reqBody);
}
