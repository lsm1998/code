/*
 * 作者：刘时明
 * 时间：2019/12/21-15:47
 * 作用：
 */
package com.lsm1998.echoes.common.annotaion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EchoesRpcServer
{
    String value() default "";

    int timeout() default 5000;

    String method() default "callBack";
}
