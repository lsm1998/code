package com.lsm1998.util.sort;

import com.lsm1998.util.array.MyArrays;

import java.util.List;

/**
 * @作者：刘时明
 * @时间：2019/6/23-14:04
 * @作用：排序工具类
 */
public class MySortUtil
{
    private static final QuickSort quick = QuickSort.getInstance();
    private static final BubbleSort bubble = BubbleSort.getInstance();
    private static final SelectSort select = SelectSort.getInstance();
    private static final InsertSort insert = InsertSort.getInstance();
    private static final MergeSort merge = MergeSort.getInstance();

    public static int[] quickSort(int[] arr)
    {
        return quick.sort(arr);
    }

    public static long[] quickSort(long[] arr)
    {
        return quick.sort(arr);
    }

    public static double[] quickSort(double[] arr)
    {
        return quick.sort(arr);
    }

    public static int[] bubbleSort(int[] arr)
    {
        return bubble.sort(arr);
    }

    public static int[] selectSort(int[] arr)
    {
        return select.sort(arr);
    }

    public static int[] insertSort(int[] arr)
    {
        return insert.sort(arr);
    }

    public static int[] mergeSort(int[] arr)
    {
        return merge.sort(arr);
    }

    public static <E extends Comparable<? super E>> void quickSort(List<E> list)
    {
        quick.sort(list);
    }

    public static void main(String[] args)
    {
        int[] arr = MyArrays.getIntArrayByRandom(0, 10, 10);
        System.out.println(MyArrays.toString(arr));
        System.out.println(MyArrays.toString(mergeSort(arr)));
    }
}
