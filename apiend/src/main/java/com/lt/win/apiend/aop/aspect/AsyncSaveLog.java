package com.lt.win.apiend.aop.aspect;

import com.alibaba.fastjson.JSON;
import com.lt.win.service.io.constant.ConstData;
import com.lt.win.service.io.dto.BaseParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author: David
 * @date: 2022/8/9
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AsyncSaveLog {
    /**
     * 保存请求日志
     *
     * @param joinPoint 连接点
     * @param retValue  返回值
     */
    @Async
    public void saveLog(JoinPoint joinPoint, Object retValue, String clientIp) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        var annotation = signature.getMethod().getAnnotation(com.lt.win.apiend.aop.annotation.LogAnnotation.class);

        Object request = "";
        if (joinPoint.getArgs().length != ConstData.ZERO) {
            request = joinPoint.getArgs()[0];
            if (request instanceof String) {
                request = ((String) request).trim();
            } else {
                request = JSON.toJSONString(request);
            }
        }

        var tips = " Success";
        if (retValue instanceof Exception) {
            retValue = ((Exception) retValue).getStackTrace();
            tips = " Failure";
        } else if (retValue instanceof String) {
            retValue = ((String) retValue).trim();
        } else {
            retValue = JSON.parseObject(JSON.toJSONString(retValue));
        }

        log.info(
                BaseParams.formatUpAndDownStreamRequest(),
                annotation.value() + tips,
                request,
                clientIp,
                retValue,
                annotation.value() + tips
        );
    }
}
