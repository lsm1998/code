package lsm1998.log4j.util;

/**
 * @作者：刘时明
 * @时间：19-1-11-上午11:13
 * @说明：
 */
public class SortUtil
{
    public static void qukeSort(int[] arr)
    {
        qukeSort(arr, 0, arr.length-1);
    }

    private static void qukeSort(int[] arr, int left, int rigth)
    {
        if (left < rigth)
        {
            int pos = partition(arr, left, rigth);
            qukeSort(arr, left, pos - 1);
            qukeSort(arr, pos + 1, rigth);
        }
    }

    private static int partition(int[] arr, int i, int j)
    {
        int pos = arr[i];
        while (i < j)
        {
            while (i < j && arr[j] >= pos)
                j--;
            if (i < j)
                arr[i++] = arr[j];
            while (i < j && arr[i] < pos)
                i++;
            if (i < j)
                arr[j--] = arr[i];
        }
        arr[i] = pos;
        return i;
    }
}
