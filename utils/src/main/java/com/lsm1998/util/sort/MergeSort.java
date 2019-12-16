package com.lsm1998.util.sort;

import com.lsm1998.util.MyArrayList;

import java.util.List;

/**
 * @作者：刘时明
 * @时间：2019/6/26-8:48
 * @作用：
 */
public class MergeSort implements MySort
{
    private static final MergeSort sort = new MergeSort();

    private MergeSort()
    {
    }

    public static MergeSort getInstance()
    {
        return sort;
    }

    @Override
    public int[] sort(int[] arr)
    {
        return mergeSort(arr);
    }

    private int[] mergeSort(int[] arr)
    {
        if (arr.length > 1)
        {
            int leftLen = arr.length / 2;
            int rightLen = arr.length - leftLen;
            int[] left = new int[leftLen];
            System.arraycopy(arr, 0, left, 0, leftLen);
            mergeSort(left);
            int[] right = new int[rightLen];
            System.arraycopy(arr, leftLen, right, 0, rightLen);
            mergeSort(right);
            mergeArray(left, right, arr);
        }
        return arr;
    }

    private void mergeArray(int[] left, int[] right, int[] merge)
    {
        int index1, index2, index3;
        index1 = index2 = index3 = 0;
        while (index1 < left.length && index2 < right.length)
        {
            merge[index3++] = left[index1] < right[index2] ? left[index1++] : right[index2++];
        }
        while (index1 < left.length)
        {
            merge[index3++] = left[index1++];
        }
        while (index2 < right.length)
        {
            merge[index3++] = right[index2++];
        }
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
