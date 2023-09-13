package com.lt.win.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT处理
 *
 * @author david
 * @version 1.0
 * @date JWT加密、解密
 */
@Slf4j
@RequiredArgsConstructor
@SuppressWarnings("WeakerAccess")
@Component
public class JwtUtils {
    private Long expirationTimeInSecond = 3600 * 24 * 300L;
    private String secret = "maLg6WN5Y8/wzwSog+KI03s23XirZN4XvgRAgtc7ikQ=";

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>(16);
        map.put("username", "123131aaabab");
        map.put("id", "1234");

        JwtUtils jwtUtils = new JwtUtils();
        jwtUtils.init("maLg6WN5Y8/wzwSog+KI03s23XirZN4XvgRAgtc7ikQ=", 3600 * 24 * 30L);
        String token = jwtUtils.generateToken(map);
        log.info(token);
        String s = jwtUtils.generateSecretKey();

        log.info("111111111111");
        JSONObject jsonObject = jwtUtils.parseToken(token);
        log.info(jsonObject.toJSONString());
        log.info(s);
    }

    public void init(@NotNull String secret, Long expirationTimeInSecond) {
        if (!secret.equals(this.secret)) {
            this.secret = secret;
        }
        if (!expirationTimeInSecond.equals(this.expirationTimeInSecond)) {
            this.expirationTimeInSecond = expirationTimeInSecond;
        }

        str2SecretKey(secret);
    }

    /**
     * String类型转密钥
     */
    public String generateSecretKey() {
        SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        return Encoders.BASE64.encode(secretKey.getEncoded());
    }

    /**
     * String类型转密钥
     *
     * @param secret string类型的秘钥
     */
    private SecretKey str2SecretKey(String secret) {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 从token中获取claim
     *
     * @param token token
     * @return claim
     */
    public JSONObject parseToken(String token) {
        try {
            Claims body = Jwts.parserBuilder()
                    .setSigningKey(str2SecretKey(this.secret))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return (JSONObject) JSON.parse(JSON.toJSONString(body));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 计算token的过期时间
     *
     * @return 过期时间
     */
    @NotNull
    @Contract(" -> new")
    private Date getExpirationTime() {
        return new Date(System.currentTimeMillis() + this.expirationTimeInSecond * 1000);
    }

    /**
     * 为指定用户生成token
     *
     * @param claims 用户信息
     * @return token
     */
    public String generateToken(Map<String, String> claims) {
        Date expirationTime = getExpirationTime();

        return Jwts.builder()
                .setClaims(claims)
                .setId(UUID.getUUID())
                // 设置subject
                .setSubject("1xWin")
                // 设置签发时间
                .setIssuedAt(new Date())
                .setExpiration(expirationTime)
                // 采用HS256方式签名，key就是用来签名的秘钥
                .signWith(str2SecretKey(this.secret), SignatureAlgorithm.HS256)
                .compact();
    }
}