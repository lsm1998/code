package com.lsm1998.util.math.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @作者：刘时明
 * @时间：2019/6/14-16:11
 * @作用：带有此注解的字段将不会被处理
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NotKeepDecimal
{
}
