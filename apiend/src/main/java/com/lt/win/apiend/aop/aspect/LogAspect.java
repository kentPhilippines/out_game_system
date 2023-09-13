package com.lt.win.apiend.aop.aspect;

import com.lt.win.utils.IpUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author : David
 * @Date : 2022-09-08
 */
@Slf4j
@Aspect
@Configuration(value = "logAspectImpl")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LogAspect {
    private final AsyncSaveLog asyncSaveLog;
    private final HttpServletRequest httpServletRequest;

    /**
     * 切入点，即一组连接点的集合
     */
    @Pointcut("execution(* com.lt.win.apiend.controller..*(..)) && @annotation(com.lt.win.apiend.aop.annotation.LogAnnotation)")
    public void executeService() {
        // Empty
    }

    /**
     * 记录操作日志
     *
     * @param joinPoint 连接点
     * @param retVal    返回值
     */
    @AfterReturning(value = "executeService()", returning = "retVal")
    public void after(JoinPoint joinPoint, Object retVal) {
        asyncSaveLog.saveLog(
                joinPoint,
                retVal,
                IpUtils.getIp(httpServletRequest)
        );
    }

    /**
     * 记录操作日志
     *
     * @param joinPoint 连接点
     * @param retVal    返回值
     */
    @AfterThrowing(value = "executeService()", throwing = "retVal")
    public void after(JoinPoint joinPoint, Exception retVal) {
        asyncSaveLog.saveLog(
                joinPoint,
                retVal,
                IpUtils.getIp(httpServletRequest)
        );
    }
}
