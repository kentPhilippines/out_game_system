package com.lt.win.apiend.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.lt.win.apiend.aop.annotation.RequestLimit;
import com.lt.win.apiend.aop.annotation.UnCheckToken;
import com.lt.win.apiend.io.bo.UserParams;
import com.lt.win.apiend.io.dto.mapper.UserGoogleCodeRespDto;
import com.lt.win.apiend.io.dto.mapper.UserInfoResDto;
import com.lt.win.apiend.service.IUserInfoService;
import com.lt.win.service.io.bo.UserBo;
import com.lt.win.utils.components.response.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;

/**
 * 用户个人信息接口
 * <p>
 * author: David
 * date: 06/04/2020
 *
 * @author david
 */
@Slf4j
@RestController
@Api(tags = "首页 - 用户管理")
@ApiSort(2)
@RequestMapping("/v1/user")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {
    private final IUserInfoService userInfoServiceImpl;

    @PostMapping(value = "/register")
    @ApiOperation(value = "用户-注册", notes = "用户-注册")
    @ApiOperationSupport(author = "David", order = 1)
    @UnCheckToken
    public Result<UserInfoResDto> register(@Valid @RequestBody UserParams.RegisterReqDto dto, HttpServletRequest request) {
        return Result.ok(userInfoServiceImpl.register(dto, request));
    }


    @PostMapping(value = "/login")
    @ApiOperation(value = "用户-登录", notes = "用户-登录")
    @ApiOperationSupport(author = "david", order = 2)
    @UnCheckToken
    public Result<UserInfoResDto> login(@Valid @RequestBody UserParams.LoginReqDto dto, HttpServletRequest request) {
        return Result.ok(userInfoServiceImpl.login(dto, request));
    }

    @PostMapping(value = "/logout")
    @ApiOperation(value = "用户-登出", notes = "用户-登出")
    @ApiOperationSupport(author = "david", order = 3)
    public Result<String> logout(HttpServletRequest request) {
        userInfoServiceImpl.logout(request);
        return Result.ok();
    }

    @PostMapping(value = "/userProfile")
    @ApiOperation(value = "用户-获取个人信息", notes = "用户-获取个人信息")
    @ApiOperationSupport(author = "david", order = 4)
    public Result<UserInfoResDto> userProfile() {
        return Result.ok(userInfoServiceImpl.getProfile());
    }

    @PostMapping(value = "/updateUserProfile")
    @ApiOperation(value = "用户-更新个人信息", notes = "用户-更新个人信息")
    @ApiOperationSupport(author = "david", order = 4)
    public Result<UserInfoResDto> updateProfile(@Valid @RequestBody UserParams.UpdateProfileReqDto dto) {
        return Result.ok(userInfoServiceImpl.updateProfile(dto));
    }


    @PostMapping(value = "/resetMobile")
    @ApiOperation(value = "用户-修改手机号", notes = "用户-修改手机号")
    @ApiOperationSupport(author = "piking", order = 5)
    @RequestLimit()
    public Result<String> resetMobile(@Valid @RequestBody UserParams.SendSmsCodeReqDto dto) {
        userInfoServiceImpl.resetMobile(dto);
        return Result.ok();
    }

    @PostMapping(value = "/sendSesCode")
    @ApiOperation(value = "获取邮箱验证码", notes = "获取邮箱验证码")
    @ApiOperationSupport(author = "David", order = 11)
    @RequestLimit()
    @UnCheckToken
    public Result<String> sendSesCode(@Valid @RequestBody UserParams.SendSesCodeReqDto dto) {
        userInfoServiceImpl.sendSesCode(dto);
        return Result.ok();
    }

    @PostMapping(value = "/forgotPassword")
    @ApiOperation(value = "忘记密码", notes = "忘记密码")
    @ApiOperationSupport(author = "David", order = 12, ignoreParameters = {"dto.category"})
    @RequestLimit()
    @UnCheckToken
    public Result<String> forgotPassword(@Valid @RequestBody UserParams.ForgotPasswordReqDto dto) {
        userInfoServiceImpl.forgotPassword(dto);
        return Result.ok();
    }

    @PostMapping(value = "/resetPassword")
    @ApiOperation(value = "重置密码", notes = "重置密码")
    @ApiOperationSupport(author = "David", order = 12)
    @RequestLimit()
    public Result<String> resetPassword(@Valid @RequestBody UserParams.ResetPasswordReqDto dto) {
        userInfoServiceImpl.resetPassword(dto);
        return Result.ok();
    }

    @PostMapping(value = "/resetEmail")
    @ApiOperation(value = "重置邮箱地址", notes = "重置邮箱地址")
    @ApiOperationSupport(author = "David", order = 12)
    @RequestLimit()
    public Result<String> resetEmail(@Valid @RequestBody UserParams.ResetEmailReqDto dto) {
        userInfoServiceImpl.resetEmail(dto);
        return Result.ok();
    }


    @PostMapping(value = "/createGoogleCode")
    @ApiOperation(value = "谷歌验证码-用户生成谷歌秘钥", notes = "用户生成谷歌秘钥")
    @ApiOperationSupport(author = "piking", order = 20)
    @RequestLimit()
    public Result<UserGoogleCodeRespDto> createGoogleCode() {
        return Result.ok(userInfoServiceImpl.createGoogleCode());
    }

    @PostMapping(value = "/bindGoogleCode")
    @ApiOperation(value = "谷歌验证码-用户绑定谷歌秘钥", notes = "用户绑定谷歌秘钥")
    @ApiOperationSupport(author = "David", order = 21)
    @RequestLimit()
    public Result<String> bindGoogleCode(@Valid @RequestBody UserParams.BindGoogleCodeDto bindGoogleCodeDto) {
        userInfoServiceImpl.bindGoogleCode(bindGoogleCodeDto);
        return Result.ok();
    }

    @PostMapping(value = "/openOrCloseGoogleCode")
    @ApiOperation(value = "谷歌验证码-用户设置开关", notes = "谷歌验证码-用户设置开关")
    @ApiOperationSupport(author = "David", order = 22)
    @RequestLimit()
    public Result<String> openOrCloseGoogleCode(@Valid @RequestBody UserParams.OpenOrCloseGoogleCodeDto openOrCloseGoogleCodeDto) {
        userInfoServiceImpl.openOrCloseGoogleCode(openOrCloseGoogleCodeDto);
        return Result.ok();
    }

    @PostMapping(value = "/checkOpenGoogleCode")
    @ApiOperation(value = "谷歌验证码-用户查看开关", notes = "谷歌验证码-用户查看开关")
    @ApiOperationSupport(author = "piking", order = 23)
    @UnCheckToken
    public Result<Integer> checkOpenGoogleCode(@Valid @RequestBody UserParams.CheckOpenGoogleCodeDto checkOpenGoogleCodeDto) {
        return Result.ok(userInfoServiceImpl.checkOpenGoogleCode(checkOpenGoogleCodeDto));
    }

    @UnCheckToken
    @ApiOperationSupport(author = "David", order = 80)
    @ApiOperation(value = "会员等级管理-等级列表", notes = "会员等级管理-等级列表")
    @PostMapping("/listLevel")
    public Result<List<UserBo.ListLevelDto>> listLevel() {
        return Result.ok(userInfoServiceImpl.listLevel());
    }
}
