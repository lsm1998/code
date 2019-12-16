package com.lsm1998.util.sort;

import com.lsm1998.util.MyArrayList;

import java.util.List;

/**
 * @作者：刘时明
 * @时间：2019/6/24-14:20
 * @作用：
 */
public class InsertSort implements MySort
{
    private static final InsertSort sort = new InsertSort();

    private InsertSort()
    {
    }

    public static InsertSort getInstance()
    {
        return sort;
    }

    @Override
    public int[] sort(int[] arr)
    {
        int temp;
        int j;
        for (int i = 1; i < arr.length; i++)
        {
            temp = arr[i];
            j = i - 1;
            while (j >= 0 && temp < arr[j])
            {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = temp;
        }
        return arr;
    }

    @Override
    public long[] sort(long[] arr)
    {
        return new long[0];
    }

    @Override
    public double[] sort(double[] arr)
    {
        return new double[0];
    }

    @Override
    public <E extends Comparable<? super E>> void sort(MyArrayList<E> list)
    {

    }

    @Override
    public <E extends Comparable<? super E>> void sort(List<E> list)
    {

    }
}
