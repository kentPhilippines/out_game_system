package com.lt.win.apiend.aop.annotation;

import java.lang.annotation.*;

/**
 * @Author : David
 * @Date : 2022-09-08
 * @Description : 添加操作日志
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface LogAnnotation {
    String value();

    String description();
}
