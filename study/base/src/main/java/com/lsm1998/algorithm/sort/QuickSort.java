/*
 * 作者：刘时明
 * 时间：2020/5/1-23:45
 * 作用：
 */
package com.lsm1998.algorithm.sort;

import com.lsm1998.algorithm.BaseUtil;

public class QuickSort
{
    public static <E extends Comparable<? super E>> void quickSort(E[] arr)
    {
        quickSort(arr, 0, arr.length - 1);
    }

    private static <E extends Comparable<? super E>> void quickSort(E[] arr, int left, int right)
    {
        if (left < right)
        {
            int pos = getPos(arr, left, right);
            quickSort(arr, left, pos - 1);
            quickSort(arr, pos + 1, right);
        }
    }

    private static <E extends Comparable<? super E>> int getPos(E[] arr, int left, int right)
    {
        E pos = arr[left];
        while (left < right)
        {
            while (left < right && !BaseUtil.less(arr[right], pos))
            {
                right--;
            }
            if (left < right)
            {
                arr[left++] = arr[right];
            }
            while (left < right && BaseUtil.less(arr[left], pos))
            {
                left++;
            }
            if (left < right)
            {
                arr[right--] = arr[left];
            }
        }
        arr[left] = pos;
        return left;
    }
}
