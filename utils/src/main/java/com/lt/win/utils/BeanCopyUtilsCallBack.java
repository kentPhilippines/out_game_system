package com.lt.win.utils;

/**
 * @author william
 * @version 1.0
 * @date 2020/3/5 0005 下午 4:37
 */
@FunctionalInterface
public interface BeanCopyUtilsCallBack<S, T> {
    void callBack(S t, T s);
}

