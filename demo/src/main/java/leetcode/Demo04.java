/*
 * 作者：刘时明
 * 时间：2020/1/2-0:11
 * 作用：
 */
package leetcode;

import java.util.*;

/**
 * 给定两个大小为 m 和 n 的有序数组 nums1 和 nums2。
 * <p>
 * 请你找出这两个有序数组的中位数，并且要求算法的时间复杂度为 O(log(m + n))。
 */
public class Demo04
{
    public static double findMedianSortedArrays(int[] nums1, int[] nums2)
    {
        PriorityQueue<Integer> queue = new PriorityQueue<>(Comparator.comparingInt(i -> i));
        for (int i : nums1)
        {
            queue.add(i);
        }
        for (int i : nums2)
        {
            queue.add(i);
        }
        if (queue.size() == 1)
        {
            return queue.peek();
        }
        int mod = queue.size() / 2 + 1;
        int len = queue.size();
        while (queue.size() > mod)
        {
            queue.poll();
        }
        if (len % 2 == 0)
        {
            double d1 = queue.poll();
            double d2 = queue.poll();
            return (d1 + d2) / 2;
        } else
        {
            return queue.poll();
        }
    }

    public static void main(String[] args)
    {
        System.out.println(findMedianSortedArrays(new int[]{1, 2,3}, new int[]{6, 4, 5}));
    }
}
