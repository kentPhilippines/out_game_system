package com.lt.win.backend.service;

import com.alibaba.fastjson.JSONObject;
import com.lt.win.backend.io.bo.user.*;
import com.lt.win.dao.generator.po.SesRecord;
import com.lt.win.service.io.bo.UserBo;
import com.lt.win.utils.components.pagination.ReqPage;
import com.lt.win.utils.components.pagination.ResPage;

import java.util.List;

/**
 * <p>
 * 会员管理 接口
 * </p>
 *
 * @author andy
 * @since 2020/3/12
 */
public interface IUserManagerService {
    /**
     * 会员列表
     *
     * @param reqBody
     * @return 分页
     */
    ResPage<ListResBody> list(ReqPage<ListReqBody> reqBody);

    /**
     * 会员详情
     *
     * @param po
     * @return
     */
    DetailResBody detail(DetailReqBody po);

    /**
     * 后台添加会员账号
     *
     * @param reqBody reqBody
     * @return res
     */
    AddUserResBody addUser(AddUserReqBody reqBody);

    /**
     * 修改会员
     *
     * @param reqBody
     * @return
     */
    boolean updateUser(UpdateUserReqBody reqBody);

    /**
     * 会员等级管理-等级列表
     *
     * @return res
     */
    List<UserBo.ListLevelDto> listLevel();

    /**
     * 会员等级管理-修改等级
     *
     * @param dto dto
     */
    void updateLevel(UserBo.ListLevelDto dto);

    /**
     * 会员管理-稽核明细-详情
     *
     * @return
     */
    ResPage<AuditDetailsResBody> auditDetails(ReqPage<CodeUidReqBody> reqPage);



    /**
     * 会员列表-团队在线-人数
     *
     * @param reqBody uid
     * @return
     */
    ListOnlineCountResBody listOnlineCount(ListOnlineReqBody reqBody);

    /**
     * 上级代理
     *
     * @param uid
     * @return
     */
    List<SupProxyListReqBody> supProxyList(Integer uid, String username);

    /**
     * 会员列表-下级列表
     *
     * @param reqBody
     * @return
     */
    ResPage<ListOnlineResBody> listChild(ListOnlineReqBody reqBody);

    /**
     * 会员等级管理-详情
     *
     * @param reqBody
     * @return
     */
    LevelDetailResBody levelDetail(JSONObject reqBody);

    /**
     * 会员管理-提款消费明细
     *
     * @param reqBody
     * @return
     */
    ResPage<ListCodeRecords> listCodeRecords(ListCodeRecordsReqBody reqBody);

    /**
     * 会员管理-清空提款消费量
     *
     * @param uid
     */
    void clearCodeRecords(Integer uid);

    /**
     * 会员管理-批量修改会员等级
     *
     * @param reqBody
     */
    void updateBatchLevel(UpdateBatchLevelReqBody reqBody);


    /**
     * 在线人数->列表查询(分页)
     *
     * @param reqBody reqBody
     * @return ResPage<OnlineUserCountListResBody>
     */
    ResPage<OnlineUserCountListResBody> onlineUserCountList(ReqPage<OnlineUserCountListReqBody> reqBody);

    /**
     * 清除用户token与手机验证次数
     */
    Boolean clearTokenCode(ClearTokenCodeReqBody reqBody);

    /**
     * 获取验证码列表
     *
     * @param dto dto
     * @return res
     */
    ResPage<SesRecord> getVerifyCodeList(ReqPage<AgentCenterParameter.SmsCodeReqDto> dto);
}
