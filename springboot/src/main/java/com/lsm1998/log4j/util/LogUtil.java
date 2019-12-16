package lsm1998.log4j.util;

/**
 * @作者：刘时明
 * @时间：19-1-11-上午11:21
 * @说明：
 */
public class LogUtil
{
    public static int getMinIndex(int[] arr)
    {
        SortUtil.qukeSort(arr);
        for (int i = 0; i < arr.length; i++)
        {
            if (arr[i] != -1)
            {
                return arr[i];
            }
        }
        return -1;
    }
}
