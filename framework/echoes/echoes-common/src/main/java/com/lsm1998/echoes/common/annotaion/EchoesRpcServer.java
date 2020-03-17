/*
 * 作者：刘时明
 * 时间：2019/12/21-15:47
 * 作用：
 */
package com.lsm1998.echoes.common.annotaion;

public @interface EchoesRpcServer
{
    String value() default "";

    int timeout() default 5000;

    String method() default "callBack";
}
