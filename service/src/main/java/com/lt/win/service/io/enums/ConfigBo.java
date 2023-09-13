package com.lt.win.service.io.enums;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: David
 * @date: 15/06/2020
 */
public interface ConfigBo {
    /**
     * RSA 公钥、私钥
     */
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    class Rsa {
        String publicKey;
        String privateKey;
    }

    /**
     * JWT 密钥、过期时间
     */
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    class Jwt {
        String secret;
        Long expired;
    }

    /**
     * StaticServerConfig
     */
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    class StaticServerConfig {
        String url;
        Integer index;
    }

    /**
     * Ses 相关配置
     */
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    class SesConfig {
        String from;
        String to;
        String subject;
        String context;
    }
}
