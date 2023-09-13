package com.lt.win.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * <p>
 * getIP
 * </p>
 *
 * @author andy
 * @since 2020/8/17
 */
@Slf4j
public class IpUtil {
    private static final String UN_KNOW = "unknown";

    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (StringUtils.isBlank(ip) || UN_KNOW.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || UN_KNOW.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || UN_KNOW.equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (StringUtils.isBlank(ip) || UN_KNOW.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if ("127.0.0.1".equals(ip)) {
                ip = getHostAddress();
            }
        }
        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        // "***.***.***.***".length() == 15
        if (ip != null && ip.length() > 15 && ip.contains(",")) {
            ip = ip.substring(0, ip.indexOf(","));
        }

        // 解决请求和响应的IP一致且通过浏览器请求时，request.getRemoteAddr()为"0:0:0:0:0:0:0:1"
        if ("0:0:0:0:0:0:0:1".equals(ip)) {
            ip = getHostAddress();
        }
        log.info("ip={}", ip);
        return ip;
    }

    /**
     * 根据网卡取本机配置的IP
     *
     * @return IP
     */
    public static String getHostAddress() {
        String ip = UN_KNOW;
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            if (null != inetAddress) {
                ip = inetAddress.getHostAddress();
            }
        } catch (UnknownHostException e) {
            log.error(UN_KNOW + ":" + e);
        }
        return ip;
    }
}