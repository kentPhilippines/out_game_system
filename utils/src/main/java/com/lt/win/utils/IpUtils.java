package com.lt.win.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * getIP
 *
 * @author David
 * @since 2022/9/08
 */
@Slf4j
public class IpUtils {
    public static final String IPV4_DELIMITER = ",";
    public static final String IPV6_DELIMITER = ":";
    public static final String IPV6_DELIMITER_SPECIAL = "::";
    public static final String IPV4_DELIMITER_CONTAINS = ".";
    public static final String IPV4_DELIMITER_SPLIT = "\\.";
    private static final String UN_KNOW = "unknown";
    private static final String LOCAL_IP = "127.0.0.1";
    private static final String LOCAL_ADDR = "0:0:0:0:0:0:0:1";
    private static final Integer IPV4_MAX_LENGTHEN = 15;
    private static final String IP_ALL_MATCH = "*.*.*.*";


    private IpUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static String getIp(@NotNull HttpServletRequest request) {
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
            if (LOCAL_IP.equals(ip)) {
                ip = getHostAddress();
            }
        }
        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        // "***.***.***.***".length() == 15
        if (ip != null && ip.length() > IPV4_MAX_LENGTHEN && ip.contains(IPV4_DELIMITER)) {
            ip = ip.substring(0, ip.indexOf(","));
        }

        // 解决请求和响应的IP一致且通过浏览器请求时，request.getRemoteAddr()为"0:0:0:0:0:0:0:1"
        if (LOCAL_ADDR.equals(ip)) {
            ip = getHostAddress();
        }
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
            var inetAddress = InetAddress.getLocalHost();
            if (null != inetAddress) {
                ip = inetAddress.getHostAddress();
            }
        } catch (UnknownHostException e) {
            log.error(UN_KNOW + ":" + e);
        }
        return ip;
    }

    /**
     * 根据网卡取本机配置的IP
     *
     * @return hostname
     */
    public static String getHostName() {
        String hostname = UN_KNOW;
        try {
            var inetAddress = InetAddress.getLocalHost();
            if (null != inetAddress) {
                hostname = inetAddress.getHostName();
            }
        } catch (UnknownHostException e) {
            log.error(UN_KNOW + ":" + e);
        }
        return hostname;
    }

    /**
     * 查询淘宝IP库
     *
     * @return IP
     */
    public static String getAddress(String ip) {
        try {
            if (StrUtil.isEmpty(ip)) {
                return "-";
            }
            String url = "https://ip.taobao.com/outGetIpInfo?ip=" + ip;
            final String str = HttpUtil.get(url);
            return JSON.parseObject(str).getJSONObject("data").getString("country");
        } catch (Exception e) {
            log.error("查询Ip地址错误");
            return "-";
        }
    }

    /**
     * 验证IP白名单
     *
     * @param ip      需要验证的IP
     * @param destIps IP白名单
     * @return true-通过 false-不通过
     */
    @NotNull
    public static Boolean validIpWhitelist(@NotNull String ip, List<String> destIps) {
        // 判定有没设置通配符规则
        if (destIps.contains(IP_ALL_MATCH)) {
            return true;
        }

        // 获取当前访问IP
        List<String> temp = new ArrayList<>();
        // IPV4判断
        if (ip.contains(IPV4_DELIMITER_CONTAINS)) {
            String[] split = ip.split(IPV4_DELIMITER_SPLIT);
            for (int i = split.length; i >= 0; i--) {
                if (i != split.length) {
                    split[i] = "*";
                }
                temp.add(StringUtils.join(split, IPV4_DELIMITER_CONTAINS));
            }
        } else if (ip.contains(IPV6_DELIMITER_SPECIAL)) {
            return destIps.contains(ip);
        } else if (ip.contains(IPV6_DELIMITER)) {
            String[] split = ip.split(IPV6_DELIMITER);
            for (int i = split.length; i >= 0; i--) {
                if (i != split.length) {
                    split[i] = "*";
                }
                temp.add(StringUtils.join(split, IPV6_DELIMITER));
            }
        }

        // 取交集
        temp.retainAll(destIps);

        return !temp.isEmpty();
    }
}

