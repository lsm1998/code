package com.lsm1998.util.sort;

import com.lsm1998.util.structure.list.MySimpleArrayList;

import java.util.List;

/**
 * @作者：刘时明
 * @时间：2019/6/24-8:49
 * @作用：排序顶层接口
 */
public interface MySort
{
    int[] sort(int[] arr);

    long[] sort(long[] arr);

    double[] sort(double[] arr);

    <E extends Comparable<? super E>> void sort(MySimpleArrayList<E> list);

    <E extends Comparable<? super E>> void sort(List<E> list);

    static <E> void swap(int i, int j, E[] arr)
    {
        E temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    static void swap(int i, int j, int[] arr)
    {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    static void swap(int i, int j, long[] arr)
    {
        long temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    static void swap(int i, int j, double[] arr)
    {
        double temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
