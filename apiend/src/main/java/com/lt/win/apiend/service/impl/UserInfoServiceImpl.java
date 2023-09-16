package com.lt.win.apiend.service.impl;

import com.lt.win.apiend.base.JavaMailSenderBase;
import com.lt.win.apiend.io.bo.UserParams;
import com.lt.win.apiend.io.dto.mapper.UserGoogleCodeRespDto;
import com.lt.win.apiend.io.dto.mapper.UserInfoResDto;
import com.lt.win.apiend.service.IUserInfoService;
import com.lt.win.apiend.service.transactional.UserTrans;
import com.lt.win.dao.generator.po.*;
import com.lt.win.dao.generator.service.*;
import com.lt.win.service.base.GoogleAuthBase;
import com.lt.win.service.base.UserCoinBase;
import com.lt.win.service.cache.redis.ConfigCache;
import com.lt.win.service.cache.redis.UserCache;
import com.lt.win.service.exception.BusinessException;
import com.lt.win.service.io.bo.UserBo;
import com.lt.win.service.io.constant.ConstData;
import com.lt.win.service.io.dto.UserInfo;
import com.lt.win.service.io.enums.ConfigBo;
import com.lt.win.service.io.enums.StatusEnum;
import com.lt.win.service.thread.ThreadHeaderLocalData;
import com.lt.win.utils.*;
import com.lt.win.utils.components.response.CodeInfo;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

import static com.lt.win.service.cache.KeyConstant.USER_LOGOUT_HASH;
import static com.lt.win.service.io.constant.ConstData.INVALID_LOGIN_MAX_TIMES;
import static com.lt.win.service.io.dto.BaseParams.HeaderInfo;

/**
 * @author: David
 * @date: 06/04/2020
 * @description:
 */
@Service
@Slf4j(topic = "UserInfoServiceImplxxxxxxx")
public class UserInfoServiceImpl implements IUserInfoService {
    public static final int STATUS_ACTIVE = 10;
    Random random = new Random();
    @Resource
    private UserService userServiceImpl;
    @Resource
    private UserTrans userTrans;
    @Resource
    private JwtUtils jwtUtils;
    @Resource
    private UserCache userCache;
    @Resource
    private ConfigCache configCache;
    @Resource
    private JavaMailSenderBase javaMailSenderBase;
    @Resource
    private SesRecordService sesRecordServiceImpl;
    @Resource
    private UserGoogleCodeService userGoogleCodeServiceImpl;
    @Resource
    private UserCoinBase userCoinBase;
    @Resource
    private GoogleAuthBase googleAuthBase;
    @Resource
    private UserLoginLogService userLoginLogServiceImpl;
    @Resource
    private UserLevelService userLevelServiceImpl;
    @Resource
    private JedisUtil jedisUtil;

    /**
     * 用户注册
     *
     * @param dto     dto
     * @param request request
     * @return res
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserInfoResDto register(UserParams.@NotNull RegisterReqDto dto, HttpServletRequest request) {
        //  verifySesCode(dto.getEmail(), dto.getVerifyCode());

        // 检查用户名是否已存在
        User user = userServiceImpl.lambdaQuery().eq(User::getUsername, dto.getUsername()).one();
        if (ObjectUtils.isNotEmpty(user)) {
            throw new BusinessException(CodeInfo.ACCOUNT_EXISTS);
        }
        String promoCode = dto.getPromoCode();
        if (ObjectUtils.isEmpty(promoCode)) {
            throw new BusinessException(CodeInfo.PROMO_CODE_INVALID);
        }


       /* // 检查手机号是否存在
        var checkMobile = userServiceImpl.lambdaQuery().eq(User::getAreaCode, dto.getAreaCode()).eq(User::getMobile, dto.getMobile()).one();
        if (ObjectUtils.isNotEmpty(checkMobile)) {
            throw new BusinessException(CodeInfo.MOBILE_EXISTS);
        }

        //查看邮箱是否存在
        var userProfile = userServiceImpl.lambdaQuery().eq(User::getEmail, dto.getEmail()).one();
        if (ObjectUtils.isNotEmpty(userProfile)) {
            throw new BusinessException(CodeInfo.EMAIL_EXISTS);
        }*/

        if (ObjectUtils.isNotEmpty(dto.getEmail())) {
            dto.setEmail(TextUtils.generatePromoCode() + "@gmail.com");
        }
        if (ObjectUtils.isNotEmpty(dto.getMobile())) {
            dto.setMobile(TextUtils.generatePromoCode() + "");
        }
        if (ObjectUtils.isNotEmpty(dto.getAreaCode())) {
            dto.setAreaCode("+86");
        }

        // 获取推广域名
        String host = request.getHeader("Host");
        dto.setLink(host);

        // 插入用户表
        userTrans.registerUser(dto);
        // 构建返回参数
        UserInfoResDto userInfoResDto = findIdentityByUsername(dto.getUsername());
        userInfoResDto.setGoogleCodeStatus(googleAuthBase.getGoogleAuthStatus(userInfoResDto.getId()));
        // 设置JWT
        String jwtToken = genJwtToken(userInfoResDto.getId(), userInfoResDto.getUsername());
        userInfoResDto.setApiToken(jwtToken);

        userCache.setUserToken(userInfoResDto.getId(), jwtToken);

        return userInfoResDto;
    }

    /**
     * 判定 邮箱验证码是否正确
     *
     * @param email email
     * @param code  code
     */
    private void verifySesCode(String email, String code) {
        // 验证邮箱验证码是否正确
        var sesRecord = sesRecordServiceImpl.lambdaQuery()
                .eq(SesRecord::getEmail, email)
                .eq(SesRecord::getCode, code)
                .last("limit 1").one();
        if (Optional.ofNullable(sesRecord).isEmpty()) {
            throw new BusinessException(CodeInfo.SES_CODE_INVALID);
        } else if (sesRecord.getCreatedAt() + 300 < DateUtils.getCurrentTime()) {
            throw new BusinessException(CodeInfo.SES_CODE_EXPIRED);
        } else if (sesRecord.getStatus() == 1) {
            throw new BusinessException(CodeInfo.SES_CODE_ALREADY_USED);
        }

        sesRecord.setStatus(1);
        sesRecord.setUpdatedAt(DateNewUtils.now());
        sesRecordServiceImpl.updateById(sesRecord);
    }

    /**
     * 用户登录
     *
     * @param dto     dto
     * @param request request
     * @return res
     */
    @Override
    public UserInfoResDto login(@NotNull UserParams.LoginReqDto dto, HttpServletRequest request) {
        Long times = userCache.modifyInvalidLoginTimes(dto.getUsername(), 0L);
        if (times >= INVALID_LOGIN_MAX_TIMES) {
            throw new BusinessException(CodeInfo.LOGIN_INVALID_OVER_LIMIT);
        }

        var user = userServiceImpl.lambdaQuery()
                //    .eq(User::getEmail, dto.getUsername())
                //    .or()
                .eq(User::getUsername, dto.getUsername())
                .one();
        if (ObjectUtils.isEmpty(user)) {
            throw new BusinessException(CodeInfo.ACCOUNT_NOT_EXISTS);
        } else if (user.getSupLevelTop() == ConstData.ZERO) {
            throw new BusinessException(CodeInfo.AGENT_LOGIN_URL_INVALID);
        } else if (user.getStatus() != StatusEnum.USER_STATUS.NORMAL.getCode()) {
            throw new BusinessException(CodeInfo.ACCOUNT_STATUS_INVALID);
        }

        var userInfo = findIdentityByUsername(user.getUsername());
        if (Boolean.FALSE.equals(PasswordUtils.validatePasswordHash(dto.getPassword(), userInfo.getPasswordHash()))) {
            userCache.modifyInvalidLoginTimes(dto.getUsername(), 1L);
            throw new BusinessException(CodeInfo.PASSWORD_INVALID);
        }

        // 验证Google Auth Code
        googleAuthBase.checkGoogleCode(userInfo.getId(), dto.getGoogleCode() + "");

        String jwtToken = genJwtToken(userInfo.getId(), userInfo.getUsername());
        userInfo.setApiToken(jwtToken);
        userInfo.setGoogleCodeStatus(googleAuthBase.getGoogleAuthStatus(userInfo.getId()));
        userInfo.setCoin(userCoinBase.getUserCoin(userInfo.getId()));

        // 更新Redis缓存
        userCache.setUserToken(userInfo.getId(), jwtToken);
        userCache.delInvalidLoginTimes(userInfo.getId());
        userServiceImpl.lambdaUpdate().eq(User::getId, userInfo.getId()).set(User::getUpdatedAt, DateNewUtils.now()).update();
        // 插入登陆日志
        var log = new UserLoginLog();
        log.setUid(userInfo.getId());
        log.setUsername(userInfo.getUsername());
        log.setIp(IpUtil.getIp(request));
        log.setDevice(getHeadLocalData().getDevice());
        log.setCategory(1);
        var now = DateNewUtils.now();
        log.setUpdatedAt(now);
        log.setCreatedAt(now);
        userLoginLogServiceImpl.save(log);

        return userInfo;
    }

    /**
     * 用户登出
     */
    @Override
    public void logout(HttpServletRequest request) {
        HeaderInfo info = getHeadLocalData();
        // 删除Token、无效登录次数
        userCache.delUserToken(info.getId());
        userCache.delInvalidLoginTimes(info.getId());
        // 插入登陆日志
        jedisUtil.setex(USER_LOGOUT_HASH + ":" + info.getId(), 1, info.getUsername());
    }

    /**
     * 获取用户信息
     *
     * @return 用户实体
     */
    @SneakyThrows
    @Override
    public UserInfoResDto getProfile() {
        HeaderInfo headLocalDate = getHeadLocalData();

        User user = userServiceImpl.getById(headLocalDate.getId());
        var res = BeanConvertUtils.beanCopy(user, UserInfoResDto::new);
        res.setCoin(userCoinBase.getUserCoin(user.getId()));
        res.setGoogleCodeStatus(googleAuthBase.getGoogleAuthStatus(user.getId()));

        return res;
    }

    /**
     * 根据username获取用户
     *
     * @param username username
     * @return 实体
     */
    @Override
    public UserInfoResDto findIdentityByUsername(String username) {
        var user = userServiceImpl.lambdaQuery().eq(User::getUsername, username).one();
        if (Optional.ofNullable(user).isEmpty()) {
            throw new BusinessException(CodeInfo.ACCOUNT_NOT_EXISTS);
        }
        if (user.getStatus() != STATUS_ACTIVE) {
            throw new BusinessException(CodeInfo.ACCOUNT_STATUS_INVALID);
        }

        var res = BeanConvertUtils.beanCopy(user, UserInfoResDto::new);
        res.setCoin(userCoinBase.getUserCoin(user.getId()));
        return res;
    }

    /**
     * 更新用户信息
     *
     * @param dto 需要更新的字段
     * @return 更新后的实体
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserInfoResDto updateProfile(UserParams.UpdateProfileReqDto dto) {
        HeaderInfo headLocalDate = getHeadLocalData();

        var user = userServiceImpl.getById(headLocalDate.getId());
        user.setAvatar(dto.getAvatar());
        userServiceImpl.updateById(user);
        dto.setAvatar(null);
        // 更新缓存
        userCache.delUserCache(user.getId());

        return findIdentityByApiTokenRes();
    }

    /**
     * 根据Token获取用户
     *
     * @return 实体
     */
    @Override
    public UserInfo findIdentityByApiToken() {
        HeaderInfo headerInfo = ThreadHeaderLocalData.HEADER_INFO_THREAD_LOCAL.get();
        var user = userServiceImpl.getById(headerInfo.getId());
        return BeanConvertUtils.beanCopy(user, UserInfo::new);
    }

    /**
     * 根据Token获取用户
     *
     * @return 实体
     */
    @Override
    public UserInfoResDto findIdentityByApiTokenRes() {
        HeaderInfo headerInfo = ThreadHeaderLocalData.HEADER_INFO_THREAD_LOCAL.get();
        var user = userServiceImpl.getById(headerInfo.getId());
        return BeanConvertUtils.beanCopy(user, UserInfoResDto::new);
    }

    /**
     * 根据Header信息
     *
     * @return 实体
     */
    @Override
    public HeaderInfo getHeadLocalData() {
        return ThreadHeaderLocalData.HEADER_INFO_THREAD_LOCAL.get();
    }

    /**
     * 发送短信验证码
     */
    @Override
    public void sendSesCode(UserParams.@NotNull SendSesCodeReqDto dto) {
        var verifyCode = (random.nextInt(9999 - 1000 + 1) + 1000) + "";
        var now = DateUtils.getCurrentTime();

        // 保存发送记录
        var sesRecord = new SesRecord();
        sesRecord.setCode(verifyCode);
        sesRecord.setEmail(dto.getEmail());
        sesRecord.setCreatedAt(now);
        sesRecord.setUpdatedAt(now);
        sesRecordServiceImpl.save(sesRecord);

        // 邮箱服务器消息发送
        javaMailSenderBase.send(verifyCode, dto.getEmail());
    }


    /**
     * 忘记密码
     *
     * @param dto category:1-登录 2-资金
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void forgotPassword(UserParams.ForgotPasswordReqDto dto) {
        // 验证邮箱验证码
        verifySesCode(dto.getEmail(), dto.getVerifyCode());

        // 获取用户信息
        var profile = userServiceImpl.lambdaQuery().eq(User::getEmail, dto.getEmail()).one();
        if (Optional.ofNullable(profile).isEmpty()) {
            throw new BusinessException(CodeInfo.EMAIL_INVALID);
        }

        profile.setPasswordHash(PasswordUtils.generatePasswordHash(dto.getPassword()));
        profile.setUpdatedAt(DateUtils.getCurrentTime());
        // 更新用户密码
        userServiceImpl.updateById(profile);
    }

    /**
     * 更新密码
     *
     * @param dto category:1-登录 2-资金
     */
    @Override
    public void resetPassword(@NotNull UserParams.ResetPasswordReqDto dto) {
        HeaderInfo headerInfo = ThreadHeaderLocalData.HEADER_INFO_THREAD_LOCAL.get();
        // 判定邮箱号码是否正确
        var userProfile = userServiceImpl.getById(headerInfo.getId());

        if (Boolean.FALSE.equals(PasswordUtils.validatePasswordHash(dto.getOldPassword(), userProfile.getPasswordHash()))) {
            throw new BusinessException(CodeInfo.PASSWORD_INVALID);
        }

        userProfile.setPasswordHash(PasswordUtils.generatePasswordHash(dto.getPassword()));
        userServiceImpl.updateById(userProfile);
    }

    /**
     * 重置邮箱地址
     *
     * @param dto dto
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetEmail(UserParams.ResetEmailReqDto dto) {
        verifySesCode(dto.getEmail(), dto.getVerifyCode());
        // 判定邮箱地址是否存在
        var isExists = userServiceImpl.lambdaQuery().eq(User::getEmail, dto.getEmail()).one();
        if (Optional.ofNullable(isExists).isPresent()) {
            throw new BusinessException(CodeInfo.EMAIL_EXISTS);
        }

        var head = getHeadLocalData();
        var profile = userServiceImpl.getById(head.getId());
        profile.setEmail(dto.getEmail());
        profile.setUpdatedAt(DateUtils.getCurrentTime());
        userServiceImpl.updateById(profile);
    }

    /**
     * 修改手机号码
     */
    @Override
    public void resetMobile(@NotNull UserParams.SendSmsCodeReqDto dto) {
        // 检查手机号是否存在
        var num = userServiceImpl.lambdaQuery().eq(User::getAreaCode, dto.getAreaCode()).eq(User::getMobile, dto.getMobile()).count();
        if (num > 0) {
            throw new BusinessException(CodeInfo.MOBILE_EXISTS);
        }

        // 修改手机号码
        HeaderInfo headerInfo = ThreadHeaderLocalData.HEADER_INFO_THREAD_LOCAL.get();
        userServiceImpl.lambdaUpdate().eq(User::getId, headerInfo.getId()).set(User::getAreaCode, dto.getAreaCode()).set(User::getMobile, dto.getMobile()).set(User::getUpdatedAt, DateUtils.getCurrentTime()).update();
    }

    /**
     * 使用 uid, username 生成JWT token
     *
     * @param uid      uid
     * @param username username
     * @return token
     */
    public String genJwtToken(@NotNull Integer uid, String username) {
        // 更新API Token
        Map<String, String> map = new HashMap<>(2);
        map.put("id", uid.toString());
        map.put("username", username);
        map.put("webRole", "web");
        ConfigBo.Jwt jwtProp = configCache.getJwtProp();
        jwtUtils.init(jwtProp.getSecret(), jwtProp.getExpired());
        return jwtUtils.generateToken(map);
    }


    /**
     * 生成Google验证码
     *
     * @return res
     */
    @Override
    public UserGoogleCodeRespDto createGoogleCode() {
        // 生成google二维码字符串
        var randomSecretKey = GoogleAuthenticator.getRandomSecretKey();
        var googleAuthenticatorBarCode = GoogleAuthenticator.getGoogleAuthenticatorBarCode(randomSecretKey, "Authenticator", "");
        return UserGoogleCodeRespDto.builder().secret(randomSecretKey).qrCode(googleAuthenticatorBarCode).build();
    }

    /**
     * 用户绑定谷歌秘钥
     *
     * @param dto dto
     */
    @Override
    public void bindGoogleCode(UserParams.BindGoogleCodeDto dto) {
        googleAuthBase.verifyGoogleAuthCode(dto.getSecret(), dto.getGoogleCode() + "");

        HeaderInfo headerInfo = ThreadHeaderLocalData.HEADER_INFO_THREAD_LOCAL.get();
        var now = DateUtils.getCurrentTime();
        var userGoogleCode = userGoogleCodeServiceImpl.lambdaQuery().eq(UserGoogleCode::getUid, headerInfo.getId()).one();
        if (Optional.ofNullable(userGoogleCode).isEmpty()) {
            userGoogleCode = new UserGoogleCode();
            userGoogleCode.setUid(headerInfo.getId());
            userGoogleCode.setGoogleCode(dto.getSecret());
            userGoogleCode.setStatus(1);
            userGoogleCode.setUpdatedAt(now);
            userGoogleCode.setCreatedAt(now);
            userGoogleCodeServiceImpl.save(userGoogleCode);
        } else {
            userGoogleCodeServiceImpl.lambdaUpdate().eq(UserGoogleCode::getUid, headerInfo.getId()).set(UserGoogleCode::getGoogleCode, dto.getSecret()).set(UserGoogleCode::getStatus, 1).set(UserGoogleCode::getUpdatedAt, now).update();
        }
    }


    /**
     * 用户打开或者关闭谷歌验证码
     */
    @Override
    public void openOrCloseGoogleCode(UserParams.OpenOrCloseGoogleCodeDto dto) {
        HeaderInfo headerInfo = ThreadHeaderLocalData.HEADER_INFO_THREAD_LOCAL.get();
        userGoogleCodeServiceImpl.lambdaUpdate().eq(UserGoogleCode::getUid, headerInfo.getId()).set(UserGoogleCode::getStatus, dto.getStatus()).update();
    }

    /**
     * 用户检验是否开启谷歌验证码
     *
     * @param dto dto
     * @return res
     */
    @Override
    public int checkOpenGoogleCode(UserParams.CheckOpenGoogleCodeDto dto) {
        Integer uid;
        var userInfo = userServiceImpl.lambdaQuery().eq(User::getUsername, dto.getUsername()).one();
        if (ObjectUtils.isNotEmpty(userInfo)) {
            uid = userInfo.getId();
        } else {
            var user = userServiceImpl.lambdaQuery().eq(User::getEmail, dto.getUsername()).one();
            if (ObjectUtils.isEmpty(user)) {
                throw new BusinessException(CodeInfo.ACCOUNT_NOT_EXISTS);
            }
            uid = user.getId();
        }

        var status = googleAuthBase.getGoogleAuthStatus(uid);
        if (status == 1) {
            return status;
        } else {
            return 0;
        }
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
}
