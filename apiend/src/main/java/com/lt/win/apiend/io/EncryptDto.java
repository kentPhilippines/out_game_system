package com.lt.win.apiend.io;

import com.lt.win.apiend.aop.annotation.FieldAnnotation;
import lombok.Data;

/**
 * @author: wells
 * @date: 2020/7/8
 * @description:
 */
@Data
@FieldAnnotation(createFlag = true)
public class EncryptDto {
    String name = "encrypt name!";
}
