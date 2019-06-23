package com.lsm1998.util.sort;

import com.lsm1998.util.array.MyArrays;

/**
 * @作者：刘时明
 * @时间：2019/6/23-14:04
 * @作用：排序工具类
 */
public class MySortUtil
{
    public static int[] quickSort(int[] arr)
    {
        return quickSort(0, arr.length - 1, arr);
    }

    private static int[] quickSort(int low, int hig, int[] arr)
    {
        if (low < hig)
        {
            int pos = partition(arr, low, hig);
            quickSort(low, pos - 1, arr);
            quickSort(pos + 1, hig, arr);
        }
        return arr;
    }

    private static int partition(int[] arr, int low, int hig)
    {
        int pos = arr[low];
        while (low < hig)
        {
            while (low < hig && arr[hig] >= pos)
                hig--;
            if (low < hig)
                arr[low++] = arr[hig];
            while (low < hig && arr[low] < pos)
                low++;
            if (low < hig)
                arr[hig--] = arr[low];
        }
        arr[low] = pos;
        return low;
    }

    public static void main(String[] args)
    {
        int[] arr= MyArrays.getIntArrayByRandom(0,100,100);

        System.out.println(MyArrays.toString(quickSort(arr)));
    }
}
