/*
 * 作者：刘时明
 * 时间：2020/5/1-23:57
 * 作用：
 */
package com.lsm1998.algorithm.sort;

import org.junit.Test;

import java.util.Arrays;

public class SortTest
{
    @Test
    public void test()
    {
        Integer[] arr = new Integer[]{1, 3, 6, 4, 7, 2, 9, 4, 7, 5};
        QuickSort.quickSort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
