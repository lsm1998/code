package com.lsm1998.util.math.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.math.BigDecimal;

/**
 * @作者：刘时明
 * @时间：2019/6/14-16:49
 * @作用：可以通过此注解指定保留位数和策略
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface KeepDecimal
{
    // 默认保留4位
    int value() default 4;

    // 默认采用舍弃多余位数
    int roundingMode() default BigDecimal.ROUND_DOWN;
}
