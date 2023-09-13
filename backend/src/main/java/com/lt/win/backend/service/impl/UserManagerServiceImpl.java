package com.lt.win.backend.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lt.win.backend.io.bo.user.*;
import com.lt.win.backend.mapper.UserManagerMapper;
import com.lt.win.backend.service.IUserManagerService;
import com.lt.win.dao.generator.po.*;
import com.lt.win.dao.generator.service.*;
import com.lt.win.service.base.UserCoinBase;
import com.lt.win.service.base.UserServiceBase;
import com.lt.win.service.cache.KeyConstant;
import com.lt.win.service.cache.redis.UserCache;
import com.lt.win.service.cache.redis.UserChannelRelCache;
import com.lt.win.service.cache.redis.UserOnlineCache;
import com.lt.win.service.common.Constant;
import com.lt.win.service.exception.BusinessException;
import com.lt.win.service.io.bo.UserBo;
import com.lt.win.service.io.constant.ConstData;
import com.lt.win.service.io.enums.StatusEnum;
import com.lt.win.utils.*;
import com.lt.win.utils.components.pagination.ReqPage;
import com.lt.win.utils.components.pagination.ResPage;
import com.lt.win.utils.components.response.CodeInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;


/**
 * <p>
 * 会员管理 接口实现
 * </p>
 *
 * @author andy
 * @since 2020/3/12
 */
@Slf4j
@Service
public class UserManagerServiceImpl extends ServiceImpl<UserManagerMapper, ListReqBody> implements IUserManagerService {

    @Resource
    private UserService userServiceImpl;
    @Resource
    private UserLevelService userLevelServiceImpl;
    @Resource
    private UserLoginLogService userLoginLogServiceImpl;
    @Resource
    private CodeRecordsService codeRecordsServiceImpl;
    @Resource
    private UserCache userCache;
    @Resource
    private CodeAuditService codeAuditServiceImpl;
    @Resource
    private UserServiceBase userServiceBase;
    @Resource
    private JedisUtil jedisUtil;
    @Resource
    private UserChannelRelCache userChannelRelCache;
    @Resource
    private UserCoinBase userCoinBase;
    @Resource
    private SesRecordService sesRecordServiceImpl;
    @Resource
    private UserWalletService userWalletServiceImpl;
    @Resource
    private UserOnlineCache userOnlineCache;

    @Override
    public ResPage<ListResBody> list(ReqPage<ListReqBody> reqBody) {
        if (null == reqBody) {
            throw new BusinessException(CodeInfo.PARAMETERS_INVALID);
        }
        ListReqBody data = reqBody.getData();
        if (null != data) {
            data.setUidList(getOnLineUidList(data.getOnLineNumFlag()));
        }

        var page = userServiceImpl.page(
                reqBody.getPage(),
                new LambdaQueryWrapper<User>()
                        .like(Optional.ofNullable(data.getUsername()).isPresent(), User::getUsername, data.getUsername())
                        .like(Optional.ofNullable(data.getMobile()).isPresent(), User::getMobile, data.getMobile())
                        .eq(Optional.ofNullable(data.getLevelId()).isPresent(), User::getLevelId, data.getLevelId())
                        .in(Optional.ofNullable(data.getRole()).isPresent(), User::getRole, data.getRole())
                        .eq(Optional.ofNullable(data.getStatus()).isPresent(), User::getStatus, data.getStatus())
                        .gt(Optional.ofNullable(data.getStartTime()).isPresent(), User::getCreatedAt, data.getStartTime())
                        .lt(Optional.ofNullable(data.getEndTime()).isPresent(), User::getCreatedAt, data.getEndTime())
        );
        var result = BeanConvertUtils.copyPageProperties(
                page,
                ListResBody::new
        );
        var unaryOperator = userServiceBase.checkShow(Constant.MOBILE);
        CompletableFuture.allOf(result.getRecords().stream().map(o -> CompletableFuture.supplyAsync(() -> {
            o.setLevelText(getLevelTextByLevelId(o.getLevelId()));
            // 手机号码是否隐藏
            o.setMobile(unaryOperator.apply(o.getMobile()));
            // 金额
            o.setCoin(userCoinBase.getUserCoin(o.getId()));
            return null;
        })).toArray(CompletableFuture[]::new)).join();

        return ResPage.get(result);
    }

    private List<Integer> getOnLineUidList(String onLineNumFlag) {
        List<Integer> onLineNumUidList = null;
        if ((StringUtils.isNotBlank(onLineNumFlag) && "Y".equals(onLineNumFlag))) {
            onLineNumUidList = userChannelRelCache.getChannelUids();
        }
        if (Optional.ofNullable(onLineNumUidList).isPresent() && onLineNumUidList.isEmpty()) {
            onLineNumUidList = null;
        }
        return onLineNumUidList;
    }

    /**
     * 获取会员等级by等级ID
     *
     * @param levelId 等级ID
     * @return 会员等级:vip1-乒乓球达人
     */
    private String getLevelTextByLevelId(Integer levelId) {
        String levelText = null;
        UserLevel userLevel = userCache.getUserLevelById(levelId);
        if (null != userLevel) {
            levelText = userLevel.getCode() + " - " + userLevel.getName();
        }
        return levelText;
    }

    @Override
    public DetailResBody detail(DetailReqBody po) {
        if (null == userCache.getUserInfo(po.getId())) {
            throw new BusinessException(CodeInfo.USER_NOT_EXISTS);
        }
        DetailResBody data = BeanConvertUtils.beanCopy(userServiceImpl.getById(po.getId()), DetailResBody::new);
        if (null != data) {
            // 下级会员数
            int childCount = 0;
            LambdaQueryWrapper<User> where = whereUserPro(data.getId());
            List<User> childList = userServiceImpl.list(where);
            if (null != childList) {
                childCount = childList.size();
            }
            // 上级代理
            List<SupProxyListReqBody> userNameList = supProxyList(data.getId(), data.getUsername());
            data.setIp(data.getIp() != null ? data.getIp().split(":")[0].replace("/", "") : data.getIp());
            data.setProxyList(userNameList);
            data.setChildCount(childCount);
            // 手机号码是否隐藏
            data.setMobile(userServiceBase.checkShow(Constant.MOBILE).apply(data.getMobile()));
            // 金额设置
            data.setCoin(userCoinBase.getUserCoin(data.getId()));
            // 获取登录日志记录-最近一次
            UserLoginLog one = userLoginLogServiceImpl.lambdaQuery().eq(UserLoginLog::getUid, data.getId()).orderByDesc(UserLoginLog::getCreatedAt).last("limit 1").one();
            if (null != one) {
                data.setIp(one.getIp());
                data.setCategory(one.getCategory());
                data.setDevice(one.getDevice());
            }
        }
        return data;
    }

    /**
     * 上级代理
     *
     * @param uid
     * @return
     */
    @Override
    public List<SupProxyListReqBody> supProxyList(Integer uid, String username) {
        List<SupProxyListReqBody> userNameList = new ArrayList<>();
        uid = supProxyListReqBody(uid, username);
        User userProfile = userServiceImpl.getById(uid);
        if (null != userProfile) {
            if (null != userProfile.getSupUid6()) {
                processSupProxyList(userNameList, userProfile.getSupUid6());
            }
            if (null != userProfile.getSupUid5()) {
                processSupProxyList(userNameList, userProfile.getSupUid5());
            }
            if (null != userProfile.getSupUid4()) {
                processSupProxyList(userNameList, userProfile.getSupUid4());
            }
            if (null != userProfile.getSupUid3()) {
                processSupProxyList(userNameList, userProfile.getSupUid3());
            }
            if (null != userProfile.getSupUid2()) {
                processSupProxyList(userNameList, userProfile.getSupUid2());
            }
            if (null != userProfile.getSupUid1()) {
                processSupProxyList(userNameList, userProfile.getSupUid1());
            }
        }
        Collections.reverse(userNameList);
        return userNameList;
    }

    private Integer supProxyListReqBody(Integer uid, String username) {
        if (StringUtils.isNotBlank(username)) {
            var user = userCache.getUserInfo(username);
            if (null == user) {
                throw new BusinessException(CodeInfo.USER_NOT_EXISTS);
            }
            uid = user.getId();
        }
        return uid;
    }


    private void processSupProxyList(List<SupProxyListReqBody> userNameList, Integer uid) {
        //用于过滤代理用户，故而不用缓存
        var user = userServiceImpl.getById(uid);
        if (null != user) {
            SupProxyListReqBody reqBody = new SupProxyListReqBody();
            reqBody.setUid(uid);
            reqBody.setUsername(user.getUsername());
            userNameList.add(reqBody);
        }
    }

    private String passwordHash(String pwd) {
        return PasswordUtils.generatePasswordHash(pwd);
    }

    /**
     * 后台添加会员账号
     *
     * @param reqBody reqBody
     * @return res
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AddUserResBody addUser(AddUserReqBody reqBody) {
        // 判断会员账号
        var count = userServiceImpl.lambdaQuery().eq(User::getUsername, reqBody.getUsername()).count();
        if (count > 0) {
            throw new BusinessException(CodeInfo.ACCOUNT_EXISTS);
        }
        // 判断邮箱号码
        count = userServiceImpl.lambdaQuery().eq(Optional.ofNullable(reqBody.getEmail()).isPresent(), User::getEmail, reqBody.getEmail()).count();
        if (count > 0) {
            throw new BusinessException(CodeInfo.EMAIL_EXISTS);
        }


        // 创建会员信息
        Integer currentTime = DateNewUtils.now();
        User user = new User();
        user.setUsername(reqBody.getUsername());
        user.setRole(reqBody.getRole());
        user.setAvatar("/avatar/9.png");
        user.setEmail(reqBody.getEmail());
        user.setCreatedAt(currentTime);
        user.setUpdatedAt(currentTime);
        // 代理依赖关系
        if (Optional.ofNullable(reqBody.getSupUsername()).isPresent()) {
            var agent = userServiceImpl.lambdaQuery().eq(User::getUsername, reqBody.getSupUsername()).one();
            if (Optional.ofNullable(agent).isEmpty()) {
                throw new BusinessException(CodeInfo.USER_SUP_ACCOUNT_NOT_EXISTS);
            } else if (agent.getIsPromoter() == 0 && agent.getSupLevelTop() != ConstData.ZERO) {
                userServiceImpl.lambdaUpdate().set(User::getIsPromoter, 1).eq(User::getId, agent.getId()).update();
            }

            user.setSupUid1(agent.getId());
            user.setSupUsername1(agent.getUsername());
            user.setSupUid2(agent.getSupUid1());
            user.setSupUid3(agent.getSupUid2());
            user.setSupUid4(agent.getSupUid3());
            user.setSupUid5(agent.getSupUid4());
            user.setSupUid6(agent.getSupUid5());
            if (agent.getSupLevelTop() == ConstData.ZERO) {
                // 上级代理是顶级代理
                user.setSupUsernameTop(agent.getUsername());
                user.setSupUidTop(agent.getId());
                user.setSupLevelTop(1);
            } else if (agent.getSupLevelTop() > ConstData.ZERO) {
                // 上级代理有顶级代理
                user.setSupLevelTop(agent.getSupLevelTop() + 1);
                user.setSupUsernameTop(agent.getSupUsernameTop());
                user.setSupUidTop(agent.getSupUidTop());
            }
        }
        user.setPasswordHash(passwordHash(reqBody.getPasswordHash()));

        // 邀请码生成
        var promoCode = TextUtils.generateRandomString(6).toUpperCase();
        while (userServiceImpl.lambdaQuery().eq(User::getPromoCode, promoCode).one() != null) {
            promoCode = TextUtils.generateRandomString(6).toUpperCase();
        }
        user.setPromoCode(promoCode);
        // VIP积分
        if (Optional.ofNullable(reqBody.getLevelId()).isPresent()) {
            var level = userLevelServiceImpl.getById(reqBody.getLevelId());
            user.setScore(level.getScoreUpgradeMin());
        }
        userServiceImpl.save(user);

        // 初始化用户所有币种钱包
        var wallet = new UserWallet();
        wallet.setId(user.getId());
        wallet.setUsername(user.getUsername());
        wallet.setCreatedAt(currentTime);
        wallet.setUpdatedAt(currentTime);
        userWalletServiceImpl.save(wallet);

        // 更新上级代理缓存
        userCache.delUserCache(user.getId());

        var res = baseMapper.getUserInfo(user.getId());
        res.setCoin(userCoinBase.getUserCoin(user.getId()));
        return res;
    }

    /**
     * 修改会员信息
     *
     * @param dto
     * @return res
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateUser(UpdateUserReqBody dto) {
        var user = BeanConvertUtils.beanCopy(dto, User::new);
        user.setId(dto.getUid());
        if (Optional.ofNullable(dto.getPasswordHash()).isPresent()) {
            user.setPasswordHash(passwordHash(dto.getPasswordHash()));
            userCache.delInvalidLoginTimes(user.getId());
        }
        if (Optional.ofNullable(dto.getPasswordCoin()).isPresent()) {
            user.setPasswordCoin(passwordHash(dto.getPasswordCoin()));
        }
        // 冻结用户状态则直接踢出用户
        if (Optional.ofNullable(dto.getStatus()).isPresent() && dto.getStatus() != StatusEnum.USER_STATUS.NORMAL.getCode()) {
            userCache.delUserToken(user.getId());
        }

        user.setId(user.getId());
        user.setUpdatedAt(DateNewUtils.now());

        // VIP积分
        if (Optional.ofNullable(dto.getLevelId()).isPresent()) {
            var level = userLevelServiceImpl.getById(dto.getLevelId());
            user.setScore(level.getScoreUpgradeMin());
        }
        userServiceImpl.updateById(user);

        // 更新缓存
        userCache.delUserCache(user.getId());
        userCache.delInvalidLoginTimes(user.getId());
        return true;
    }

    /**
     * 会员等级管理-等级列表
     *
     * @return res
     */
    @Override
    public List<UserBo.ListLevelDto> listLevel() {
        return userLevelServiceImpl.lambdaQuery().orderByAsc(UserLevel::getId).list().stream().map(o -> BeanConvertUtils.beanCopy(o, UserBo.ListLevelDto::new)).collect(Collectors.toList());
    }

    /**
     * 会员等级管理-修改等级
     *
     * @param dto dto
     */
    @Override
    public void updateLevel(UserBo.ListLevelDto dto) {
        Integer currentTime = DateUtils.getCurrentTime();
        var level = BeanConvertUtils.beanCopy(dto, UserLevel::new);
        level.setUpdatedAt(currentTime);
        userLevelServiceImpl.updateById(level);
        jedisUtil.del(KeyConstant.USER_LEVEL_ID_LIST_HASH);
    }

    /**
     * 会员管理-稽核明细-详情
     *
     * @param reqPage@return
     */
    @Override
    public ResPage<AuditDetailsResBody> auditDetails(ReqPage<CodeUidReqBody> reqPage) {
        Page<CodeAudit> page = codeAuditServiceImpl.page(reqPage.getPage(), new LambdaQueryWrapper<CodeAudit>().eq(CodeAudit::getUid, reqPage.getData().getUid()));
        Page<AuditDetailsResBody> returnPage = BeanConvertUtils.copyPageProperties(page, AuditDetailsResBody::new);
        return ResPage.get(returnPage);
    }


    @Override
    public ListOnlineCountResBody listOnlineCount(ListOnlineReqBody reqBody) {
        return ListOnlineCountResBody.builder().count(userCache.subordinate(reqBody.getUid()).getTeam().size()).uid(reqBody.getUid()).build();
    }

    @Override
    public ResPage<ListOnlineResBody> listChild(ListOnlineReqBody reqBody) {
        ResPage<ListOnlineResBody> resPage = new ResPage<>();
        LambdaQueryWrapper<User> where = whereUserProSup1(reqBody.getUid());
        Page<User> page = userServiceImpl.page(reqBody.getPage(), where);
        if (page.getRecords().isEmpty()) {
            return resPage;
        }
        Page<ListOnlineResBody> tmpPage = BeanConvertUtils.copyPageProperties(page, ListOnlineResBody::new);
        resPage = ResPage.get(tmpPage);
        resPage.getList().stream().parallel().forEach(s -> {
            DetailReqBody req = new DetailReqBody();
            req.setId(s.getId());
            DetailResBody detail = baseMapper.getDetail(req);
            if (null != detail) {
                BeanConvertUtils.beanCopy(detail, s);
            }
        });
        return resPage;
    }

    @Override
    public LevelDetailResBody levelDetail(JSONObject reqBody) {
        if (null == reqBody) {
            throw new BusinessException(CodeInfo.PARAMETERS_INVALID);
        }
        Integer id = reqBody.getInteger("id");
        UserLevel userLevel = userLevelServiceImpl.getById(id);
        return BeanConvertUtils.beanCopy(userLevel, LevelDetailResBody::new);
    }

    @Override
    public ResPage<ListCodeRecords> listCodeRecords(ListCodeRecordsReqBody reqBody) {
        ResPage<ListCodeRecords> resPage = new ResPage<>();
        LambdaQueryWrapper<CodeRecords> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(CodeRecords::getUid, reqBody.getUid());
        Page<CodeRecords> page = codeRecordsServiceImpl.page(reqBody.getPage(), wrapper);
        Page<ListCodeRecords> target = BeanConvertUtils.copyPageProperties(page, ListCodeRecords::new);
        if (target.getRecords().isEmpty()) {
            return resPage;
        }
        for (ListCodeRecords records : target.getRecords()) {
            User userCacheInfo = userCache.getUserInfo(records.getUid());
            records.setUsername(userCacheInfo.getUsername());
        }
        return ResPage.get(target);
    }

    @Override
    public void clearCodeRecords(Integer uid) {
        if (null != uid) {
            LambdaQueryWrapper<CodeRecords> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(CodeRecords::getUid, uid);
            codeRecordsServiceImpl.remove(wrapper);
        }
    }

    private LambdaQueryWrapper<User> whereUserPro(Integer uid) {
        LambdaQueryWrapper<User> where = Wrappers.lambdaQuery();
        where.eq(User::getSupUid1, uid).or();
        where.eq(User::getSupUid2, uid).or();
        where.eq(User::getSupUid3, uid).or();
        where.eq(User::getSupUid4, uid).or();
        where.eq(User::getSupUid5, uid).or();
        where.eq(User::getSupUid6, uid);
        return where;
    }

    private LambdaQueryWrapper<User> whereUserProSup1(Integer uid) {
        LambdaQueryWrapper<User> where = Wrappers.lambdaQuery();
        where.eq(User::getSupUid1, uid);
        return where;
    }


    @Override
    public void updateBatchLevel(UpdateBatchLevelReqBody reqBody) {
        Integer levelId = reqBody.getLevelId();
        List<Integer> uidList = reqBody.getUidList();
        if (null != levelId && !uidList.isEmpty()) {
            List<User> entityList = new ArrayList<>();
            Integer currentTime = DateUtils.getCurrentTime();
            for (Integer uid : uidList) {
                if (null != uid && uid > 0) {
                    User user = new User();
                    user.setUpdatedAt(currentTime);
                    user.setLevelId(levelId);
                    user.setId(uid);
                    entityList.add(user);
                }
            }
            userServiceImpl.updateBatchById(entityList);
            uidList.forEach(s -> userCache.delUserCache(s));
            log.info("updateBatchLevel ok.." + entityList.size());
        }
    }

    @Override
    public ResPage<OnlineUserCountListResBody> onlineUserCountList(ReqPage<OnlineUserCountListReqBody> reqBody) {
        if (null == reqBody) {
            throw new BusinessException(CodeInfo.PARAMETERS_INVALID);
        }
        var onlineUidList = userOnlineCache.getOnLineUerList();
        if (Optional.ofNullable(onlineUidList).isEmpty() || onlineUidList.isEmpty()) {
            return new ResPage<>();
        }

        OnlineUserCountListReqBody data = reqBody.getData();
        if (null == data) {
            data = new OnlineUserCountListReqBody();
        }
        if (StringUtils.isNotBlank(data.getUsername())) {
            var userInfo = userCache.getUserInfo(data.getUsername());
            if (null == userInfo || null == userInfo.getId()) {
                return new ResPage<>();
            }
            onlineUidList = onlineUidList.stream().filter(o -> o.equals(userInfo.getId())).collect(Collectors.toList());
        }
        if (CollectionUtils.isEmpty(onlineUidList)) {
            return new ResPage<>();
        }
        if (StringUtils.isNotBlank(data.getSupUsername())) {
            var userInfo = userCache.getUserInfo(data.getSupUsername());
            if (null == userInfo || null == userInfo.getId()) {
                return new ResPage<>();
            }
            data.setSupUid1(userInfo.getId());
        }
        data.setUidList(onlineUidList);


        var poPage = baseMapper.onlineUserCountList(reqBody.getPage(), data);
        if (Optional.ofNullable(poPage.getRecords()).isEmpty() || poPage.getRecords().isEmpty()) {
            return new ResPage<>();
        }
        var tmpPage = BeanConvertUtils.copyPageProperties(poPage, OnlineUserCountListResBody::new);
        var resPage = ResPage.get(tmpPage);
        Set<Integer> uidSet = resPage.getList().stream().map(OnlineUserCountListResBody::getUid)
                .collect(Collectors.toSet());
        for (var o : resPage.getList()) {
            var userInfo = userCache.getUserInfo(o.getUid());
            // 会员等级 -> vip1-乒乓球达人
            o.setLevelText(getLevelTextByLevelId(userInfo.getLevelId()));
            o.setSupUsername(userInfo.getSupUsername1());
            o.setCoin(userCoinBase.getUserCoin(o.getUid()));
        }
        return resPage;
    }

    /**
     * 清除用户token与手机验证次数
     */
    @Override
    public Boolean clearTokenCode(ClearTokenCodeReqBody reqBody) {
        var uid = reqBody.getUid();
        var code = reqBody.getCode();
        if (Objects.nonNull(uid)) {
            var username = Optional.ofNullable(userCache.getUserInfo(uid)).map(User::getUsername).orElse("");
            jedisUtil.hdel(KeyConstant.USER_LOGIN_INVALID_TIMES, username);
        }
        if (Objects.nonNull(code)) {
            jedisUtil.hdel(KeyConstant.SMS_CODE_HASH, code);
        }
        return true;
    }

    /**
     * 获取验证码列表
     *
     * @param dto dto
     * @return res
     */
    @Override
    public ResPage<SesRecord> getVerifyCodeList(ReqPage<AgentCenterParameter.SmsCodeReqDto> dto) {
        var data = dto.getData();
        return ResPage.get(
                sesRecordServiceImpl.page(
                        dto.getPage(),
                        new LambdaQueryWrapper<SesRecord>().
                                like(Optional.ofNullable(data.getEmail()).isPresent(), SesRecord::getEmail, data.getEmail()))
        );
    }
}
