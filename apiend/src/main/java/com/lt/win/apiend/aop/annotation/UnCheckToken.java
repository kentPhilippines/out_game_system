package com.lt.win.apiend.aop.annotation;

import java.lang.annotation.*;

/**
 * @author: David
 * @date: 05/03/2020
 * @description:
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface UnCheckToken {
}
