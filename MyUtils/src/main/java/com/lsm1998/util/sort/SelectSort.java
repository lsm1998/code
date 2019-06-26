package com.lsm1998.util.sort;

import com.lsm1998.util.structure.list.MySimpleArrayList;

import java.util.List;

/**
 * @作者：刘时明
 * @时间：2019/6/24-12:31
 * @作用：
 */
public class SelectSort implements MySort
{
    private static final SelectSort sort = new SelectSort();

    private SelectSort()
    {
    }

    public static SelectSort getInstance()
    {
        return sort;
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
    public <E extends Comparable<? super E>> void sort(MySimpleArrayList<E> list)
    {

    }

    @Override
    public <E extends Comparable<? super E>> void sort(List<E> list)
    {

    }

    @Override
    public int[] sort(int[] arr)
    {
        for (int i = 0; i < arr.length - 1; i++)
        {
            int minIndex = i + 1;
            for (int j = i + 1; j < arr.length; j++)
            {
                if (arr[j] < arr[minIndex])
                {
                    minIndex = j;
                }
            }
            MySort.swap(i, minIndex, arr);
        }
        return arr;
    }
}
