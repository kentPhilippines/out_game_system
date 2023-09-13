package com.lt.win.backend.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lt.win.backend.io.bo.AdminParams;
import com.lt.win.backend.io.constant.ConstantData;
import com.lt.win.backend.service.IAdminInfoBase;
import com.lt.win.backend.service.IAdminOperateService;
import com.lt.win.backend.thread.LoginLogTask;
import com.lt.win.dao.generator.po.Admin;
import com.lt.win.dao.generator.po.AuthGroupAccess;
import com.lt.win.dao.generator.service.AdminService;
import com.lt.win.dao.generator.service.AuthGroupAccessService;
import com.lt.win.service.cache.redis.AdminCache;
import com.lt.win.service.cache.redis.ConfigCache;
import com.lt.win.service.common.Constant;
import com.lt.win.service.exception.BusinessException;
import com.lt.win.service.io.dto.BaseParams;
import com.lt.win.service.io.enums.ConfigBo;
import com.lt.win.utils.*;
import com.lt.win.utils.components.response.CodeInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.collections.map.HashedMap;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static com.lt.win.service.io.constant.ConstData.INVALID_LOGIN_MAX_TIMES;

/**
 * @Description:
 * @Author: David
 * @Date: 03/06/2020
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))

public class AdminOperatorServiceImpl implements IAdminOperateService {
    private static final String THREAD_NAME = "SYNC_LOGIN_LOG";
    private static final String POOL_NAME = "POOL_LOGIN_LOG";
    private static final AtomicInteger NEXT_ID = new AtomicInteger(0);
    private static final ThreadPoolExecutor POOL_EXECUTOR = new ThreadPoolExecutor(
            50,
            50,
            0L,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(),
            runnable -> new Thread(runnable, String.format("%s-%s%d", POOL_NAME, THREAD_NAME, NEXT_ID.getAndDecrement()))
    );
    private final AuthManagerServiceImpl authManagerServiceImpl;
    private final LoginLogTask loginLogTask;

    private final JwtUtils jwtUtils;
    private final AdminService adminServiceImpl;
    private final ConfigCache configCache;
    private final AdminCache adminCache;
    private final AuthGroupAccessService authGroupAccessServiceImpl;

    @Override
    public AdminParams.LoginResponse login(AdminParams.LoginRequest dto, HttpServletRequest request) {
        Long times = adminCache.modifyInvalidLoginTimes(dto.getUsername(), 0L);
        if (times >= INVALID_LOGIN_MAX_TIMES) {
            throw new BusinessException(CodeInfo.LOGIN_INVALID_OVER_LIMIT);
        }
        Admin admin = findIdentityByUsername(dto.getUsername());
        if (admin == null) {
            throw new BusinessException(CodeInfo.ACCOUNT_NOT_EXISTS);
        }
        //是否显示验证码
        var codeFlag = Constant.VERIFICATION_SHOW.equals(configCache.getConfigByTitle(Constant.VERIFICATION_OF_GOOGLE));
        //检测验证码
        if (codeFlag && !GoogleAuthenticator.checkVerificationCode(admin.getSecret(), dto.getVerificationCode())) {
            throw new BusinessException(CodeInfo.VERIFICATION_CODE_INVALID);
        }
        if (Boolean.FALSE.equals(PasswordUtils.validatePasswordHash(dto.getPassword(), admin.getPasswordHash()))) {
            adminCache.modifyInvalidLoginTimes(dto.getUsername(), 1L);
            throw new BusinessException(CodeInfo.PASSWORD_INVALID);
        }
        String userAgent = request.getHeader("User-Agent");
        String ip = IpUtil.getIp(request);
        POOL_EXECUTOR.submit(() -> loginLogTask.run(admin.getId(), ip, userAgent, 1));

        String jwtToken = genJwtToken(admin.getId(), admin.getUsername());
        // 更新Redis缓存
        adminCache.setUserToken(admin.getId(), jwtToken);
        adminCache.delInvalidLoginTimes(dto.getUsername());
        AdminParams.LoginResponse loginResponse = BeanConvertUtils.copyProperties(admin, AdminParams.LoginResponse::new);
        loginResponse.setApiToken(jwtToken);
        //代理角色处理
        var authGroupAccess = authGroupAccessServiceImpl.getOne(new LambdaQueryWrapper<AuthGroupAccess>()
                .eq(AuthGroupAccess::getUid, admin.getId()), false);
        var role = Objects.nonNull(authGroupAccess) ? authGroupAccess.getGroupId() : 0;
        loginResponse.setRole(role);
        return loginResponse;
    }

    @Override
    public AdminParams.LoginResponse profile() {
        Integer id = IAdminInfoBase.getHeadLocalData().getId();
        Admin admin = findIdentity(id);
        return BeanConvertUtils.copyProperties(admin, AdminParams.LoginResponse::new);
    }

    /**
     * 刷新页面获取权限信息
     *
     * @return
     */
    public JSONObject refreshGetRule() {
        var uid = IAdminInfoBase.getHeadLocalData().getId();
        //获取权限
        return authManagerServiceImpl.loadAuthRule(new HashedMap() {{
            put("uid", uid);
        }});
    }

    /**
     * 根据UID获取管理员信息
     *
     * @param id uid
     * @return 管理员实体
     */
    public Admin findIdentity(Integer id) {
        Admin admin = adminServiceImpl.getById(id);
        if (null == admin) {
            throw new BusinessException(CodeInfo.ACCOUNT_NOT_EXISTS);
        } else if (admin.getStatus() != ConstantData.STATUS_VALID_CODE) {
            throw new BusinessException(CodeInfo.ACCOUNT_STATUS_INVALID);
        }

        return admin;
    }

    /**
     * 根据用户名获取用户实体
     *
     * @param username 用户名
     * @return 用户实体
     */
    public Admin findIdentityByUsername(String username) {
        Admin admin = adminServiceImpl.lambdaQuery().eq(Admin::getUsername, username).one();
        if (null == admin) {
            throw new BusinessException(CodeInfo.ACCOUNT_NOT_EXISTS);
        } else if (admin.getStatus() != ConstantData.STATUS_VALID_CODE) {
            throw new BusinessException(CodeInfo.ACCOUNT_STATUS_INVALID);
        }
        return admin;
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
        map.put("webRole", "admin");
        ConfigBo.Jwt jwtProp = configCache.getJwtProp();
        jwtUtils.init(jwtProp.getSecret(), jwtProp.getExpired());
        return jwtUtils.generateToken(map);
    }

    /**
     * 用户登出
     */
    @Override
    public void logout(HttpServletRequest request) {
        BaseParams.HeaderInfo headLocalDate = IAdminInfoBase.getHeadLocalData();

        String userAgent = request.getHeader("User-Agent");
        POOL_EXECUTOR.submit(() -> loginLogTask.run(headLocalDate.getId(), IpUtil.getIp(request), userAgent, 0));
        // 删除Token、无效登录次数
        adminCache.delUserToken(headLocalDate.getId());
        adminCache.delInvalidLoginTimes(headLocalDate.getUsername());
    }

    private void checkVerificationCode(Admin admin, String verificationCode) {
        Base32 base32 = new Base32();
        byte[] bytes = base32.decode(admin.getSecret());
        String hexKey = Hex.encodeHexString(bytes);
        long time = (System.currentTimeMillis() / 1000) / 30;
        String hexTime = Long.toHexString(time);
        GoogleAuthenticator.generateTOTP(hexKey, hexTime, "6");
    }
}
