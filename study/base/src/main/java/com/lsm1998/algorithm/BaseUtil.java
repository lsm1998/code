/*
 * 作者：刘时明
 * 时间：2020/5/1-23:46
 * 作用：
 */
package com.lsm1998.algorithm;

public class BaseUtil
{
    public static <E extends Comparable<? super E>> void swap(E[] arr, int i, int j)
    {
        E temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static <E extends Comparable<? super E>> boolean less(E v1, E v2)
    {
        return v1.compareTo(v2) < 0;
    }

    public static <E extends Comparable<? super E>> boolean eq(E v1, E v2)
    {
        return v1.compareTo(v2) == 0;
    }
}
