/*
 * 作者：刘时明
 * 时间：2020/5/4-1:04
 * 作用：
 */
package com.lsm1998.algorithm.base;

/**
 * 分治算法完成汉诺塔问题
 * <p>
 * DivideAndConquer 分治算法DAC
 */
public class Hanoi
{
    public static void hanoi(int num, char a, char b, char c)
    {
        if (num == 1)
        {
            System.out.printf("第一个盘从%c到%c \n", a, c);
        } else
        {
            hanoi(num - 1, a, c, b);
            System.out.printf("第%d个盘从%c到%c \n", num, a, c);
            hanoi(num - 1, b, a, c);
        }
    }
}
