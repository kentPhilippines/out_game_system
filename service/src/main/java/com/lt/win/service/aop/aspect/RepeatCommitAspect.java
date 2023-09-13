package com.lt.win.service.aop.aspect;

import com.lt.win.service.exception.BusinessException;
import com.lt.win.utils.JedisUtil;
import com.lt.win.utils.components.response.CodeInfo;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author ：wells
 * @version : 1.0.0
 * @date ：Created in 2019/11/28 23:00
 * @description :
 */
@Aspect
@Component
public class RepeatCommitAspect {
    private static final String KEY_TEMPLATE = "idempotent";
    @Autowired
    private JedisUtil jedisUtil;

    @Before("@annotation(com.lt.win.service.aop.annotation.RepeatCommit)")
    public void before(JoinPoint joinPoint) {
        //获取当前方法信息
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        //生成Key
        String key = KEY_TEMPLATE + "_" + method.getName() + Arrays.toString(joinPoint.getArgs());
        String redisRes = jedisUtil.setNX(key, 3);
        if (!Objects.equals("OK", redisRes)) {
            throw new BusinessException(CodeInfo.PLAT_REQUEST_FREQUENT);
        }
    }
}