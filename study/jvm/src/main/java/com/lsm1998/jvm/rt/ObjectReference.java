/*
 * 作者：刘时明
 * 时间：2020/4/12-10:47
 * 作用：
 */
package com.lsm1998.jvm.rt;

public class ObjectReference
{
    /**
     * Java程序通过栈内的Reference访问堆内对象
     *
     * 访问方式分为句柄访问和指针访问，HotSpot使用指针访问
     *
     * 句柄访问：
     *  原理：在堆中划出一块区域存放句柄地址，Reference存储句柄地址，通过句柄中的对象实例指针和对象类型指针来访问
     *  优点：解耦，Reference不用关心对象地址移动；
     *  缺点：性能相对慢；
     *
     * 指针访问：
     *  原理：Reference直接存储对象地址
     *  优缺点与句柄访问相反；
     */
}
