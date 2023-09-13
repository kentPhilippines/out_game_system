package com.lt.win.backend.service;


import com.lt.win.backend.io.bo.AdminParams;
import com.lt.win.backend.io.bo.AdminParams.LoginRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: David
 * @date: 04/03/2020
 * @description:
 */
public interface IAdminOperateService {
    /**
     * 后台管理员登录
     *
     * @param dto {"username", "password"}
     * @return 登录后结果集
     */
    AdminParams.LoginResponse login(LoginRequest dto, HttpServletRequest request);

    /**
     * 获取管理员信息
     *
     * @return 登录后结果集
     */
    AdminParams.LoginResponse profile();

    /**
     * 用户登出
     */
    void logout(HttpServletRequest request);
}
