/*
 * 作者：刘时明
 * 时间：2020/5/4-0:30
 * 作用：
 */
package com.lsm1998.algorithm.base;

import com.lsm1998.algorithm.BaseUtil;

/**
 * 二分查找
 */
public class BinarySearchFind
{
    public static <E extends Comparable<? super E>> int binarySearchFind(E[] arr, E target)
    {
        int left = 0;
        int right = arr.length - 1;
        int mod;
        while (left <= right)
        {
            mod = (left + right) / 2;
            if (BaseUtil.eq(arr[mod], target))
            {
                return mod;
            } else if (BaseUtil.less(arr[mod], target))
            {
                left = mod + 1;
            } else
            {
                right = mod - 1;
            }
        }
        return -1;
    }
}
