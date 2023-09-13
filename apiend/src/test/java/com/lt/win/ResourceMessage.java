package com.lt.win;

import com.lt.win.utils.SpringUtils;
import lombok.SneakyThrows;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author : Wells
 * @Date : 2020/10/9 3:02 上午
 * @Description : XX
 **/
public class ResourceMessage extends ReloadableResourceBundleMessageSource {
    @SneakyThrows
    public String resolveCode(Locale locale, String paramKey) {
        // 输出私有变量的值
        var bundleMessageSource = (ResourceBundleMessageSource) SpringUtils.getBean("bundleMessageSource");
        var basename = bundleMessageSource.getBasenameSet().iterator().next();
        List<String> filenames = calculateAllFilenames(basename, locale);
        var fileList = filenames.stream().filter(x -> x.contains(locale.getLanguage() + "_" + locale.getCountry())).collect(Collectors.toList());
        PropertiesHolder propHolder = getProperties(fileList.get(0));
        var proper = propHolder.getProperties();
        for (Map.Entry<Object, Object> entry : proper.entrySet()) {
            var key = entry.getKey();
            var value = entry.getValue().toString().replace(" ", "");
            System.out.println("key=" + key + "value=" + value);
            if (value.equals(paramKey.replace(" ", ""))) {
                return key.toString();
            }
        }
        return "";
    }
}
