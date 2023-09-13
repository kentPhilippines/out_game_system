package com.lt.win.apiend.aop.annotation;

import java.lang.annotation.*;


/**
 * @author admin
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface RequestLimit {
    /**
     * 允许访问的次数
     */
    int count() default 15;

    /**
     * 时间段，多少时间段内运行访问count次
     */
    long time() default 24;
}
