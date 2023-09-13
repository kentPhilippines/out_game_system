package com.lt.win.backend.aop.annotation;

import java.lang.annotation.*;

/**
 * <p>
 * LogAspect annotation
 * </p>
 *
 * @author andy
 * @since 2020/8/12
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface UnCheckLog {
}
