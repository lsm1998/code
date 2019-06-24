package com.lsm1998.util.sort.impl;

import com.lsm1998.util.array.MyArrays;
import com.lsm1998.util.sort.MySort;
import com.lsm1998.util.structure.list.MySimpleArrayList;

import java.util.List;

/**
 * @作者：刘时明
 * @时间：2019/6/24-11:24
 * @作用：
 */
public class BubbleSort implements MySort
{
    private static final BubbleSort bubble = new BubbleSort();

    private BubbleSort()
    {
    }

    public static BubbleSort getInstance()
    {
        return bubble;
    }

    @Override
    public int[] sort(int[] arr)
    {
        Integer[] temp = (Integer[]) sort(MyArrays.intArrayBoxing(arr));
        return MyArrays.intArrayUnpacking(temp);
    }

    @Override
    public long[] sort(long[] arr)
    {
        // 暂不提供
        return new long[0];
    }

    @Override
    public double[] sort(double[] arr)
    {
        // 暂不提供
        return new double[0];
    }

    @Override
    public <E extends Comparable<? super E>> void sort(MySimpleArrayList<E> list)
    {
        // 暂不提供
    }

    @Override
    public <E extends Comparable<? super E>> void sort(List<E> list)
    {
        // 暂不提供
    }

    public Comparable[] sort(Comparable[] arr)
    {
        for (int i = 0; i < arr.length; i++)
        {
            for (int j = 0; j < arr.length - i - 1; j++)
            {
                if (arr[j].compareTo(arr[j + 1]) > 0)
                {
                    Comparable temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
        return arr;
    }
}
