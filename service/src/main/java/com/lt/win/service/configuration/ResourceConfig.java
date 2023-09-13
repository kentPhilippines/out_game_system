package com.lt.win.service.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class ResourceConfig {

    @Bean("bundleMessageSource")
    @Primary
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource bundleMessageSource = new ResourceBundleMessageSource();
        bundleMessageSource.setDefaultEncoding("UTF-8");
        // 指定国际化资源目录,其中i18n/,Messages为国际化文件前缀
        bundleMessageSource.setBasename("i18n/messages");
        bundleMessageSource.setCacheMillis(10);
        return bundleMessageSource;
    }
}
