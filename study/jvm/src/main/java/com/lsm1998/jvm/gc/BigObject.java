/*
 * 作者：刘时明
 * 时间：2020/4/12-11:34
 * 作用：
 */
package com.lsm1998.jvm.gc;

public class BigObject
{
    private static final int _1MB = 1024 * 1024;

    // VM options:-Xms20m -Xmx20m -Xmn10m -XX:+PrintGCDetails -XX:SurvivorRatio=8
    public static void test1()
    {
        byte[] arr1, arr2, arr3, arr4;
        arr1 = new byte[2 * _1MB];
        arr2 = new byte[2 * _1MB];
        arr3 = new byte[2 * _1MB];
        arr4 = new byte[4 * _1MB];
    }

    public static void main(String[] args)
    {
        BigObject.test1();
    }
}
