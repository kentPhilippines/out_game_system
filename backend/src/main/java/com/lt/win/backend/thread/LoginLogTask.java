package com.lt.win.backend.thread;

import com.lt.win.dao.generator.po.Admin;
import com.lt.win.dao.generator.po.AdminLoginLog;
import com.lt.win.dao.generator.service.AdminLoginLogService;
import com.lt.win.dao.generator.service.AdminService;
import com.lt.win.service.cache.redis.AdminCache;
import com.lt.win.utils.DateNewUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Description:
 * @Author: David
 * @Date: 03/06/2020
 */
@Slf4j
@Component
public class LoginLogTask {
    @Resource
    AdminService adminServiceImpl;
    @Resource
    AdminLoginLogService adminLoginLogServiceImpl;
    @Resource
    AdminCache adminCache;


    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    public void run(Integer uid, String ip, String userAgent, Integer category) {
        Integer time = DateNewUtils.now();

        // 更新Updated_at时间
        Admin admin = adminServiceImpl.getById(uid);
        admin.setUpdatedAt(time);
        adminServiceImpl.updateById(admin);
        adminCache.updateAdminById(uid);
        // 插入Admin_login_log日志
        AdminLoginLog adminLoginLog = new AdminLoginLog();
        adminLoginLog.setUid(uid);
        adminLoginLog.setUsername(admin.getUsername());
        adminLoginLog.setUpdatedAt(time);
        adminLoginLog.setCreatedAt(time);
        adminLoginLog.setIp(ip);
        adminLoginLog.setUserAgent(userAgent);
        adminLoginLog.setCategory(category);
        adminLoginLogServiceImpl.save(adminLoginLog);
        log.info("=======登录OK....uid={},ip={}", uid, ip);
    }

}
