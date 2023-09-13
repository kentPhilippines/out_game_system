package com.lt.win.apiend.aop.aspect;


import com.lt.win.apiend.aop.annotation.RequestLimit;
import com.lt.win.utils.IpUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;


/**
 * @author admin
 */
@Configuration
@Aspect
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RequestLimitAspect {
    private final StringRedisTemplate stringRedisTemplate;

    @Before("execution(public * com..xinbo.sports.apiend.controller.*.*(..)) && @annotation(limit)")
    public void requestLimit(JoinPoint joinPoint, RequestLimit limit) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String ip = IpUtils.getIp(request);
        String url = request.getRequestURL().toString();
        String key = "req_limit_".concat(url).concat("_").concat(ip);
        boolean checkResult = checkWithRedis(limit, key);
        if (!checkResult) {
            log.info("requestLimited," + "[用户ip:{}],[访问地址:{}]超过了限定的次数[{}]次", ip, url, limit.count());
//            throw new BusinessException(CodeInfo.API_IP_ACCESS_OVER_LIMIT);
        }
    }

    /**
     * 以redis实现请求记录
     *
     * @param limit
     * @param key
     * @return
     */
    private boolean checkWithRedis(RequestLimit limit, String key) {
        long count = stringRedisTemplate.opsForValue().increment(key, 1);
        if (count == 1) {
            stringRedisTemplate.expire(key, limit.time(), TimeUnit.HOURS);
        }
        return count <= limit.count();
    }

}
