package com.lsm1998.structure.page;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 0-1 背包问题
 * <p>
 * 背包容量为4
 * <p>
 * 物品表：
 * 名称  重量  价值
 * 吉他  1    1500
 * 音响  4    3000
 * 电脑  3    2000
 */
public class PageMain
{
    public static void main(String[] args)
    {
        int[] w = {1, 4, 3};
        int[] val = {1500, 3000, 2000};

        int m = 4;
        int n = val.length;

        // 创建二维表格
        int[][] v = new int[n + 1][m + 1];

        for (int i = 0; i < v.length; i++)
        {
            v[i][0] = 0;
        }
        Arrays.fill(v[0], 0);

        int[][] path = new int[n + 1][m + 1];

        for (int i = 1; i < v.length; i++)
        {
            for (int j = 1; j < v[i].length; j++)
            {
                if (w[i - 1] > j)
                {
                    v[i][j] = v[i - 1][j];
                } else
                {
                    if (v[i - 1][j] > val[i - 1] + v[i - 1][j - w[i - 1]])
                    {
                        v[i][j] = v[i - 1][j];
                    } else
                    {
                        v[i][j] = val[i - 1] + v[i - 1][j - w[i - 1]];
                        path[i][j] = 1;
                    }
                }
            }
        }
        print(v);
        System.out.println("----------");
        printPath(path, w);
    }

    private static void print(int[][] v)
    {
        for (int i = 0; i < v.length; i++)
        {
            for (int j = 0; j < v[i].length; j++)
            {
                System.out.print(v[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static void printPath(int[][] path, int[] w)
    {
        int i = path.length - 1;
        int j = path[0].length - 1;
        while (i > 0 && j > 0)
        {
            if (path[i][j] == 1)
            {
                System.out.println(i);
                j -= w[i - 1];
            }
            i--;
        }
    }
}
