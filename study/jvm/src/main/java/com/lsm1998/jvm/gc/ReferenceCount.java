/*
 * 作者：刘时明
 * 时间：2020/4/12-11:09
 * 作用：
 */
package com.lsm1998.jvm.gc;

public class ReferenceCount
{
    public Object obj = null;

    private static final int _1MB = 1024 * 1024;

    private byte[] bigArr = new byte[2 * _1MB];

    public static void main(String[] args)
    {
        ReferenceCount count1=new ReferenceCount();
        ReferenceCount count2=new ReferenceCount();
        count1.obj=count2;
        count2.obj=count1;
        count1=null;
        count2=null;
        System.gc();
    }
}
