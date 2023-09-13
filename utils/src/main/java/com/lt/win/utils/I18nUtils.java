package com.lt.win.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: wells
 * @date: 2020/7/7
 * @description:
 */
@Slf4j
public class I18nUtils extends ReloadableResourceBundleMessageSource {

    /**
     * 请求头信息，通过此解析
     */
    public static final String HTTP_ACCEPT_LANGUAGE = "Accept-Language";


    /**
     * 获取当前语种
     *
     * @return
     */
    public static Locale getLocale() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest httpServletRequest = servletRequestAttributes.getRequest();
        String locale = httpServletRequest.getHeader(HTTP_ACCEPT_LANGUAGE);
        if (LANG_COUNTRY.EN_US.language.equals(locale)) {
            return Locale.US;
        } else if (LANG_COUNTRY.ES_ES.language.equals(locale)) {
            return new Locale(LANG_COUNTRY.ES_ES.language, LANG_COUNTRY.ES_ES.country);
        } else if (LANG_COUNTRY.PT_PT.language.equals(locale)) {
            return new Locale(LANG_COUNTRY.PT_PT.language, LANG_COUNTRY.PT_PT.country);
        } else if (LANG_COUNTRY.RU_RU.language.equals(locale)) {
            return new Locale(LANG_COUNTRY.RU_RU.language, LANG_COUNTRY.RU_RU.country);
        } else if (LANG_COUNTRY.KO_KR.language.equals(locale)) {
            return new Locale(LANG_COUNTRY.KO_KR.language, LANG_COUNTRY.KO_KR.country);
        } else if (LANG_COUNTRY.JA_JP.language.equals(locale)) {
            return new Locale(LANG_COUNTRY.JA_JP.language, LANG_COUNTRY.JA_JP.country);
        } else if (LANG_COUNTRY.ZH_CN.language.equals(locale)) {
            return new Locale(LANG_COUNTRY.ZH_CN.language, LANG_COUNTRY.ZH_CN.country);
        }
        return Locale.US;
    }

    /**
     * 根据语种获取国际化信息
     *
     * @param propertyKey
     * @return
     */
    public static String getLocaleMessage(String propertyKey) {
        //路由的值不翻译或者key为空
        try {
            if (Strings.isEmpty(propertyKey) || propertyKey.contains("route_")) {
                return propertyKey;
            }
            Locale locale = getLocale();
            if (StringUtils.isNotEmpty(propertyKey)) {
                var bundleMessageSource = (ResourceBundleMessageSource) SpringUtils.getBean("bundleMessageSource");
                String key = "";
                //活动文本内容不读国际化文件
                if (!propertyKey.contains("<") && !propertyKey.contains(">")) {
                    key = propertyKey.replace(" ", "");
                }
                if (key.length() < 1) {
                    return propertyKey;
                }
                key = key.substring(0, 1).toUpperCase() + key.substring(1);
                String message = getMessage(bundleMessageSource, key, locale);
                if (Strings.isEmpty(message)) {
                    key = key.substring(0, 1).toLowerCase() + key.substring(1);
                    message = getMessage(bundleMessageSource, key, locale);
                }
                message = Strings.isEmpty(message) ? propertyKey : message;
                return message;
            }
        } catch (Exception e) {
//            log.error("国际化翻译异常", e);
        }
        return propertyKey;
    }

    private static String getMessage(ResourceBundleMessageSource bundleMessageSource, String key, Locale locale) {
        try {
            return bundleMessageSource.getMessage(key, null, locale);
        } catch (Exception e) {
            return "";
        }

    }

    /**
     * 根据语种获取国际化信息
     *
     * @param propertyKey
     * @param params
     * @return
     */
    public static String getLocaleMessageWithPlaceHolder(String propertyKey, Object... params) {
        Locale locale = getLocale();
        var bundleMessageSource = (ResourceBundleMessageSource) SpringUtils.getBean("bundleMessageSource");
        return bundleMessageSource.getMessage(propertyKey, params, locale);
    }

    /**
     * 在图片的末尾自动加上对应彩种的后缀(a.png -> a_ZH.png)
     *
     * @param img 图片地址
     * @return 切换后的图片目录
     */
    @NotNull
    public static String getLocaleImg(@NotNull String img) {
        int i = img.lastIndexOf('.');
        if (i == -1) {
            return img;
        }

        Locale locale = getLocale();
        String localeUrl = locale.getLanguage() + '-' + locale.getCountry();
        return img.substring(0, i) + "_" + localeUrl + img.substring(i);
    }

    /**
     * 在图片的末尾自动加上对应彩种的后缀(a.png -> a_ZH.png)
     *
     * @param img    图片地址
     * @param locale locale
     * @return 切换后的图片目录
     */
    @NotNull
    public static String getLocaleImg(@NotNull String img, Locale locale) {
        int i = img.lastIndexOf('.');
        if (i == -1) {
            return img;
        }

        String localeUrl = locale.getLanguage() + '-' + locale.getCountry();
        return img.substring(0, i) + "_" + localeUrl + img.substring(i);
    }

    @SneakyThrows
    public List resolveCode(Locale locale, String paramKey) {
        List<String> list = new ArrayList<>();
        var bundleMessageSource = (ResourceBundleMessageSource) SpringUtils.getBean("bundleMessageSource");
        var basename = bundleMessageSource.getBasenameSet().iterator().next();
        List<String> filenames = calculateAllFilenames(basename, locale);
        var fileList = filenames.stream().filter(x -> x.contains(locale.getLanguage() + "_" + locale.getCountry())).collect(Collectors.toList());
        PropertiesHolder propHolder = getProperties(fileList.get(0));
        var proper = propHolder.getProperties();
        for (Map.Entry<Object, Object> entry : proper.entrySet()) {
            var key = entry.getKey();
            var value = entry.getValue().toString().replace(" ", "");
            if (value.contains(paramKey.replace(" ", ""))) {
                list.add(key.toString());
            }
        }
        return list;
    }

    @SneakyThrows
    public List<String> resolveCode(Locale locale) {
        List<String> list = new ArrayList<>();
        var bundleMessageSource = (ResourceBundleMessageSource) SpringUtils.getBean("bundleMessageSource");
        var basename = bundleMessageSource.getBasenameSet().iterator().next();
        List<String> filenames = calculateAllFilenames(basename, locale);
        var fileList = filenames.stream().filter(x -> x.contains(locale.getLanguage() + "_" + locale.getCountry())).collect(Collectors.toList());
        PropertiesHolder propHolder = getProperties(fileList.get(0));
        var proper = propHolder.getProperties();
        for (Map.Entry<Object, Object> entry : proper.entrySet()) {
            var value = entry.getValue();
            var key = entry.getKey().toString().replace(" ", "");
            var ele = key.replace(" ", "") + "=" + value;
            list.add(ele);
        }
        return list;
    }

    @Getter
    @NoArgsConstructor
    enum LANG_COUNTRY {
        /**
         * 请求语言参数
         */
        // 繁体
        // ZH_TW("zh", "TW"),
        // 中文
        ZH_CN("zh", "CN"),
        // 英语
        EN_US("en", "US"),
        //西班牙
        ES_ES("es", "es"),
        // 俄罗斯
        RU_RU("ru", "ru"),
        // 葡萄牙
        PT_PT("pt", "pt"),
        // 韩国
        KO_KR("ko", "kr"),
        //日本
        JA_JP("ja", "jp");
        String language;
        String country;

        LANG_COUNTRY(String language, String country) {
            this.language = language;
            this.country = country;
        }
    }
}
