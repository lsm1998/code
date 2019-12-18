package com.lsm1998.spring.beans.annotation;

import java.lang.annotation.*;

/**
 * @作者：刘时明
 * @时间:2018/12/20-18:10
 * @说明：配置标识
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyConfiguration
{
}
