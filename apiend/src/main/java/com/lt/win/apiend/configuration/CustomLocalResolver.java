package com.lt.win.apiend.configuration;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public class CustomLocalResolver implements LocaleResolver {


    @Nullable
    private Locale defaultLocale;

    public void setDefaultLocale(@Nullable Locale defaultLocale) {
        this.defaultLocale = defaultLocale;
    }

    @Nullable
    public Locale getDefaultLocale() {
        return this.defaultLocale;
    }

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        Locale defaultLocale = this.getDefaultLocale();//获取application.properties默认的配置
        String Language = request.getHeader("Accept-Language");
        if (defaultLocale != null && Language == null) {
            return defaultLocale;//http请求头没获取到Accept-Language才采用默认配置
        } else {
            //request.getHeader("Accept-Language")获取得到的情况
            if (!StringUtils.isNotBlank(Language)) {
                String[] split = Language.split("_");
                return new Locale(split[0], split[1]);
            }
            //没传的情况，默认返回request.getHeader("Accept-Language")的值
            return defaultLocale;
        }
}


    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {

    }
}

