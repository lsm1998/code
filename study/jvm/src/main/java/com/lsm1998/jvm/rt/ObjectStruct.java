/*
 * 作者：刘时明
 * 时间：2020/4/12-9:58
 * 作用：
 */
package com.lsm1998.jvm.rt;

import java.io.Serializable;

public class ObjectStruct implements Serializable
{
    /**
     * 对象内存布局(HotSpot虚拟机)：
     *  对象头+实例数据+对齐填充
     *
     * 对象头=运行时数据+类型指针，数组类型还有长度：
     *  对象头大小为32bit或64bit，取决于操作系统（未开启指针压缩）；
     *  对象头存储内容是动态的，根据状态复用存储空间；
     *  对象头存储内容见 对象头markword.png；
     *  类型指针大小为32bit；
     *
     * 实例数据=代码里定义的字段内容；
     *
     * 对齐填充=对象起始地址若不为8字节的整数倍，会由此补齐；
     */

    /**
     * 估算一个对象大小 ObjectStruct对象，64位操作系统为例：
     *  对象头=64bit+32bit=12字节
     *  实例数据：a字段int类型32bit，b字段double类型64bit，str字段对象类型，只存引用32bit
     *  大小=12字节+4字节+8字节+4字节=28字节
     *  由于28不是8的整数倍，所以还有4字节的对齐填充，共32字节
     */

    private int a;

    private double b;

    protected String str;

    // 对象实例数据不包括方法
    public void setA(int a)
    {
        this.a=a;
    }
}
