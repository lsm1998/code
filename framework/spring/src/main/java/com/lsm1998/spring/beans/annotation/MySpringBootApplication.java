package com.lsm1998.spring.beans.annotation;

import com.lsm1998.spring.beans.annotation.MyConfiguration;

import java.lang.annotation.*;

/**
 * @作者：刘时明
 * @时间：2019/1/10-11:48
 * @说明：
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@MyConfiguration
public @interface MySpringBootApplication
{
    String[] scanBasePackages() default {};

    Class<?>[] exclude() default {};
}
