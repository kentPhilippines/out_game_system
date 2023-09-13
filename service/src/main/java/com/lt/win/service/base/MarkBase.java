package com.lt.win.service.base;

import com.lt.win.service.common.Constant;
import com.lt.win.utils.I18nUtils;

import java.util.regex.Pattern;

/**
 * @Author : Wells
 * @Date : 2020-12-05 4:28 下午
 * @Description : xx
 */
public class MarkBase {
    /**
     * @param mark 备注
     * @return 拼接后的备注
     */
    public static String spliceMark(String mark) {
        //mark为数字加国际化提示，否则直接展示
        Pattern pattern = Pattern.compile("^-?\\d+(\\.\\d+)?$");
        if (pattern.matcher(mark).find()) {
            mark = I18nUtils.getLocaleMessage(Constant.SUPPLEMENT_MARK) + mark;
        }
        return mark;
    }
}
