package com.lsm1998.springboot.annotation;

import java.lang.annotation.*;

/**
 * @作者：刘时明
 * @时间：2019/1/10-11:48
 * @说明：
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MySpringBootApplication
{
    String[] scanBasePackages() default {};

    Class<?>[] exclude() default {};
}
