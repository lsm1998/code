/*
 * 作者：刘时明
 * 时间：2019/12/21-15:54
 * 作用：
 */
package com.lsm1998.echoes.annotaion;

public @interface MyRpcClient
{
    String value() default "";

    // 是否引用检查
    boolean check() default true;
}
