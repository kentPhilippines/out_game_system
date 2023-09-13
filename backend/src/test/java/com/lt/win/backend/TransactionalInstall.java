package com.lt.win.backend;

import com.lt.win.dao.generator.po.User;
import com.lt.win.dao.generator.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: wells
 * @date: 2020/8/17
 * @description:
 */
@Component
public class TransactionalInstall {
    @Autowired
    private UserService userServiceImpl;
    ReentrantLock lock = new ReentrantLock();

    @Transactional(rollbackFor = Exception.class)
    public synchronized boolean updateUser(int i) {
        //lock.tryLock();
        try {
            System.out.println("开始执行线程===" + i);
            User user = userServiceImpl.getById(12);
            User newUser = new User();
            newUser.setId(user.getId());
//            newUser.setCoin(user.getCoin().add(BigDecimal.valueOf(10)));
//            userServiceImpl.lambdaUpdate()
//                    .eq(User::getUpdatedAt, user.getUpdatedAt())
//                    .eq(User::getId, 12)
//                    .set(User::getCoin, user.getCoin().add(BigDecimal.valueOf(10)))
//                    .set(User::getUpdatedAt, Instant.now().getEpochSecond())
//                    .update();
            System.out.println("结束线程===" + i);
            return true;
            // return userServiceImpl.lambdaUpdate().setSql("coin = coin +" + 10).eq(User::getId, 12).update();
        } catch (Exception e) {

        } finally {
            // lock.unlock();
        }
        return false;
    }
}