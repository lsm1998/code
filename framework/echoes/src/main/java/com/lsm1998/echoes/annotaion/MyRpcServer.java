/*
 * 作者：刘时明
 * 时间：2019/12/21-15:47
 * 作用：
 */
package com.lsm1998.echoes.annotaion;

import com.lsm1998.echoes.wapper.DefaultCallBack;

public @interface MyRpcServer
{
    String value() default "";

    int timeout() default 5000;

    Class<?> callBack() default DefaultCallBack.class;

    String method() default "callBack";
}
