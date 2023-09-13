package com.lt.win.apiend.configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.Locale;

/**
 * 资源文件配置加载
 *
 * @author ims
 */
@Configuration
public class I18nConfig implements WebMvcConfigurer {

    @Autowired
    WebMvcProperties webMvcProperties;

    /**
     * 默认解析器 其中locale表示默认语言
     */
    @Bean
    public LocaleResolver localeResolver() {
        CustomLocalResolver localResolver = new CustomLocalResolver();
        localResolver.setDefaultLocale(Locale.CHINESE);
        return localResolver;
    }


    /**
     * 默认拦截器 其中lang表示切换语言的参数名
     */
    @Bean
    public WebMvcConfigurer localeInterceptor() {
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                LocaleChangeInterceptor localeInterceptor = new LocaleChangeInterceptor();
                //默认的请求参数为locale，eg: login?locale=zh_CN
                localeInterceptor.setParamName(LocaleChangeInterceptor.DEFAULT_PARAM_NAME);
                registry.addInterceptor(localeInterceptor);
            }
        };
    }

//    @Bean
//    public LocaleChangeInterceptor localeChangeInterceptor()
//    {
//        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
//        // 参数名
//        lci.setParamName("lang");
//        return lci;
//    }
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry)
//    {
//        registry.addInterceptor(localeChangeInterceptor());
//    }
}
