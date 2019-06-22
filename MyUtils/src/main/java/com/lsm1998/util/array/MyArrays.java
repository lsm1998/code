package com.lsm1998.util.array;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

/**
 * @作者：刘时明
 * @时间：2019/6/8-13:22
 * @说明：数组操作工具类
 */
public class MyArrays
{
    private static final Random random = new Random();

    /**
     * 获取一个随机元素的int数组
     *
     * @param min 最小值
     * @param max 最大值
     * @param len 数组长度
     * @return
     */
    public static int[] getIntArrayByRandom(int min, int max, int len)
    {
        return random.ints(len, min, max).toArray();
    }

    /**
     * 获取一个随机元素的long数组
     *
     * @param min 最小值
     * @param max 最大值
     * @param len 数组长度
     * @return
     */
    public static long[] getLongArrayByRandom(long min, long max, int len)
    {
        return random.longs(len, min, max).toArray();
    }

    /**
     * 获取一个随机元素的double数组
     *
     * @param min 最小值
     * @param max 最大值
     * @param len 数组长度
     * @return
     */
    public static double[] getDoubleArrayByRandom(double min, double max, int len)
    {
        return random.doubles(len, min, max).toArray();
    }

    /**
     * 类似于Arrays.toString
     *
     * @param arr
     * @return
     */
    public static String toString(Object[] arr)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Object o : arr)
        {
            sb.append(o + ",");
        }
        if (sb.length() > 1)
        {
            sb.delete(sb.length() - 1, sb.length());
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * 重载方法，说明见调用方法
     *
     * @param arr
     * @return
     */
    public static String toString(int[] arr)
    {
        return toString(IntStream.of(arr).boxed().toArray(Integer[]::new));
    }

    /**
     * 重载方法，说明见调用方法
     *
     * @param arr
     * @return
     */
    public static String toString(long[] arr)
    {
        return toString(LongStream.of(arr).boxed().toArray(Long[]::new));
    }

    /**
     * 重载方法，说明见调用方法
     *
     * @param arr
     * @return
     */
    public static String toString(double[] arr)
    {
        return toString(DoubleStream.of(arr).boxed().toArray(Double[]::new));
    }

    /**
     * int数组冒泡排序
     * @param arr
     * @return
     */
    public static int[] sortIntByBubble(int[] arr)
    {
        Number[] temp = sortByBubble(IntStream.of(arr).boxed().toArray(Integer[]::new));
        return Arrays.stream((Integer[]) temp).mapToInt(Integer::valueOf).toArray();
    }

    /**
     * long数组冒泡排序
     * @param arr
     * @return
     */
    public static long[] sortLongByBubble(long[] arr)
    {
        Number[] temp = sortByBubble(LongStream.of(arr).boxed().toArray(Integer[]::new));
        return Arrays.stream((Long[]) temp).mapToLong(Long::valueOf).toArray();
    }

    /**
     * double数组冒泡排序
     * @param arr
     * @return
     */
    public static double[] sortDoubleByBubble(double[] arr)
    {
        Number[] temp = sortByBubble(DoubleStream.of(arr).boxed().toArray(Double[]::new));
        return Arrays.stream((Double[]) temp).mapToDouble(Double::valueOf).toArray();
    }

    /**
     * 具体的排序方法
     * @param arr
     * @return
     */
    private static Number[] sortByBubble(Comparable[] arr)
    {
        for (int i = 0; i < arr.length; i++)
        {
            for (int j = 1; j < arr.length - i; j++)
            {
                if (arr[j].compareTo(arr[j - 1]) > 0)
                {
                    Comparable temp = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = temp;
                }
            }
        }
        return (Number[]) arr;
    }
}
