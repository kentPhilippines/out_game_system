package com.lt.win.apiend.service;

import com.lt.win.apiend.io.bo.UserParams;
import com.lt.win.apiend.io.dto.mapper.UserGoogleCodeRespDto;
import com.lt.win.apiend.io.dto.mapper.UserInfoResDto;
import com.lt.win.service.io.bo.UserBo;
import com.lt.win.service.io.dto.UserInfo;
import org.jetbrains.annotations.NotNull;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.lt.win.service.io.dto.BaseParams.HeaderInfo;

/**
 * @author: David
 * @date: 06/04/2020
 * @description:
 */
public interface IUserInfoService {
    /**
     * 用户注册
     *
     * @param dto     dto
     * @param request request
     * @return res
     */
    UserInfoResDto register(UserParams.RegisterReqDto dto, HttpServletRequest request);

    /**
     * 用户登录
     *
     * @param dto     dto
     * @param request request
     * @return res
     */
    UserInfoResDto login(UserParams.LoginReqDto dto, HttpServletRequest request);

    /**
     * 用户登出
     */
    void logout(HttpServletRequest request);

    /**
     * 获取用户信息
     *
     * @return 用户实体
     */
    UserInfoResDto getProfile();

    /**
     * 根据username获取用户
     *
     * @param username username
     * @return 实体
     */
    UserInfoResDto findIdentityByUsername(String username);

    /**
     * 更新用户信息
     *
     * @param dto 需要更新的字段
     * @return 更新后的实体
     */
    UserInfoResDto updateProfile(UserParams.UpdateProfileReqDto dto);

    /**
     * 根据Token获取用户
     *
     * @return 实体
     */
    UserInfo findIdentityByApiToken();

    /**
     * 根据Token获取用户
     *
     * @return 实体
     */
    UserInfoResDto findIdentityByApiTokenRes();

    /**
     * 根据Header信息
     *
     * @return 实体
     */
    HeaderInfo getHeadLocalData();


    /**
     * 发送短信验证码
     *
     * @param dto dto
     */
    void sendSesCode(UserParams.SendSesCodeReqDto dto);

    /**
     * 忘记密码
     *
     * @param dto category:1-登录 2-资金
     */
    void forgotPassword(UserParams.ForgotPasswordReqDto dto);

    /**
     * 重置密码
     *
     * @param dto category:1-登录 2-资金
     */
    void resetPassword(UserParams.ResetPasswordReqDto dto);

    /**
     * 重置邮箱地址
     *
     * @param dto dto
     */
    void resetEmail(UserParams.ResetEmailReqDto dto);

    /**
     * 修改手机号码
     *
     * @param dto 验证码
     */
    void resetMobile(UserParams.SendSmsCodeReqDto dto);

    /**
     * 用户生成谷歌秘钥
     *
     * @return piking
     */
    UserGoogleCodeRespDto createGoogleCode();

    /**
     * 用户绑定谷歌秘钥
     *
     * @param dto dto
     */
    void bindGoogleCode(UserParams.BindGoogleCodeDto dto);

    /**
     * 用户打开或者关闭谷歌验证码
     *
     * @param dto dto
     */
    void openOrCloseGoogleCode(UserParams.OpenOrCloseGoogleCodeDto dto);

    /**
     * 用户检验是否开启谷歌验证码
     *
     * @param dto dto
     * @return res
     */
    int checkOpenGoogleCode(UserParams.CheckOpenGoogleCodeDto dto);

    /**
     * 会员等级管理-等级列表
     *
     * @return res
     */
    List<UserBo.ListLevelDto> listLevel();

    /**
     * 使用 uid, username 生成JWT token
     *
     * @param uid      uid
     * @param username username
     * @return token
     */
    String genJwtToken(@NotNull Integer uid, String username);
}
