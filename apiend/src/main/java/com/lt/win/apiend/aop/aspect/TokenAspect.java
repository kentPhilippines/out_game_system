package com.lt.win.apiend.aop.aspect;

import com.lt.win.apiend.aop.annotation.UnCheckToken;
import com.lt.win.apiend.base.HeaderBase;
import com.lt.win.service.io.dto.BaseParams;
import com.lt.win.service.thread.ThreadHeaderLocalData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

/**
 * @author: David
 * @date: 05/03/2020
 * @description:
 */
@Aspect
@Configuration
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TokenAspect {
    public static final ThreadLocal<BaseParams.HeaderInfo> HEADER_INFO = ThreadHeaderLocalData.HEADER_INFO_THREAD_LOCAL;
    private final HeaderBase headerBase;

    @Pointcut("execution(* com.lt.win.apiend.controller..*(..))")
    public void executeService() {
        // do nothing here
    }

    @Before("executeService()")
    public void before(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        // 获取UnCheckToken 注解   有此注解不强制验证，无则强制认证
        UnCheckToken annotation = method.getAnnotation(UnCheckToken.class);
        boolean b = true;
        if (annotation != null) {
            b = false;
        }
        HEADER_INFO.set(headerBase.getHeaderLocalData(b));
    }

    @After("executeService()")
    public void after() {
        HEADER_INFO.remove();
    }
}

