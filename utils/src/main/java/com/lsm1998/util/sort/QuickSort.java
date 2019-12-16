package com.lsm1998.util.sort;

import com.lsm1998.util.MyArrayList;
import com.lsm1998.util.array.MyArrays;

import java.util.List;

/**
 * @作者：刘时明
 * @时间：2019/6/24-8:49
 * @作用：
 */
public class QuickSort implements MySort
{
    private static final QuickSort quickSort = new QuickSort();

    private QuickSort()
    {
    }

    public static QuickSort getInstance()
    {
        return quickSort;
    }

    @Override
    public int[] sort(int[] arr)
    {
        Integer[] temp = (Integer[]) sort(MyArrays.intArrayBoxing(arr), 0, arr.length - 1);
        return MyArrays.intArrayUnpacking(temp);
    }

    @Override
    public long[] sort(long[] arr)
    {
        Long[] temp = (Long[]) sort(MyArrays.longArrayBoxing(arr), 0, arr.length - 1);
        return MyArrays.longArrayUnpacking(temp);
    }

    @Override
    public double[] sort(double[] arr)
    {
        Double[] temp = (Double[]) sort(MyArrays.doubleArrayBoxing(arr), 0, arr.length - 1);
        return MyArrays.doubleArrayUnpacking(temp);
    }

    @Override
    public <E extends Comparable<? super E>> void sort(MyArrayList<E> list)
    {
        // 暂不提供
    }

    @Override
    public <E extends Comparable<? super E>> void sort(List<E> list)
    {
        sort(list, 0, list.size() - 1);
    }

    private Comparable[] sort(Comparable[] arr, int low, int hig)
    {
        if (low < hig)
        {
            int pos = getPartition(arr, low, hig);
            sort(arr, low, pos - 1);
            sort(arr, pos + 1, hig);
        }
        return arr;
    }

    private int getPartition(Comparable[] arr, int low, int hig)
    {
        Comparable pos = arr[low];
        while (low < hig)
        {
            while (low < hig && arr[hig].compareTo(pos) >= 0)
                hig--;
            if (low < hig)
                arr[low++] = arr[hig];
            while (low < hig && arr[hig].compareTo(pos) < 0)
                low++;
            if (low < hig)
                arr[hig--] = arr[low];
        }
        arr[low] = pos;
        return low;
    }

    private <E extends Comparable<? super E>> void sort(List<E> list, int low, int hig)
    {
        if (low < hig)
        {
            int pos = getPartition(list, low, hig);
            sort(list, low, pos - 1);
            sort(list, pos + 1, hig);
        }
    }

    private <E extends Comparable<? super E>> int getPartition(List<E> list, int low, int hig)
    {
        E pos = list.get(low);
        while (low < hig)
        {
            while (low < hig && list.get(hig).compareTo(pos) >= 0)
                hig--;
            if (low < hig)
                list.set(low++, list.get(hig));
            while (low < hig && list.get(hig).compareTo(pos) < 0)
                low++;
            if (low < hig)
                list.set(hig--, list.get(low));
        }
        list.set(low, pos);
        return low;
    }
}
