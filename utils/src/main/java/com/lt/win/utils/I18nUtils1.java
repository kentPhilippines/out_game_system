package com.lt.win.utils;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Objects;

@Component
public class I18nUtils1 {

    private static MessageSource messageSource;


    public I18nUtils1(MessageSource messageSource) {
        I18nUtils1.messageSource = messageSource;
    }

    /**
     * 获取admin默认国际化翻译值
     */
    public static String getAdminDefault(String msgKey) {
        try {
            Locale currentLocale = new Locale(Locale.SIMPLIFIED_CHINESE.getLanguage(), Locale.SIMPLIFIED_CHINESE.getCountry());
            return messageSource.getMessage(msgKey, null, currentLocale);
        } catch (Exception e) {
            return msgKey;
        }
    }

    /**
     * 获取单个国际化翻译值
     */
    public static String get(String msgKey) {
        try {
            if (Objects.isNull(LocaleContextHolder.getLocale())) {
                Locale currentLocale = new Locale(Locale.SIMPLIFIED_CHINESE.getLanguage(), Locale.SIMPLIFIED_CHINESE.getCountry());
                return messageSource.getMessage(msgKey, null, currentLocale);
            } else {
                return messageSource.getMessage(msgKey, null, LocaleContextHolder.getLocale());
            }
        } catch (Exception e) {
            return msgKey;
        }
    }

    public static String get(String msgKey, String LanguageType) {
        try {
            if (StringUtils.isBlank(LanguageType)) {
                Locale currentLocale = new Locale(Locale.SIMPLIFIED_CHINESE.getLanguage(), Locale.SIMPLIFIED_CHINESE.getCountry());
                return messageSource.getMessage(msgKey, null, currentLocale);
            }
            return messageSource.getMessage(msgKey, null, new Locale(LanguageType));
        } catch (Exception e) {
            return msgKey;
        }
    }

    public static String message(String msgKey, Object... args) {
        try {
            return messageSource.getMessage(msgKey, args, LocaleContextHolder.getLocale());
        } catch (Exception e) {
            return msgKey;
        }
    }
}
