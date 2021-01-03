package com.lsm1998.redis;

import java.util.ArrayList;
import java.util.List;

public class Demo03
{
    static int stepCount = 1;

    static int number = 1;

    static int opStep = 1;

    static List<String> opList = new ArrayList<>();

    static
    {
        opList.add("上");
        opList.add("左");
    }

    static String[] opArrays = {"下", "右", "上", "左",};

    static int opIndex = 0;

    static int level = 0;

    public static void main(String[] args)
    {
        printArrays(9);
    }

    public static void printArrays(int level)
    {
        int count = 2;
        LOOP:
        while (opList.size() <= level - 1)
        {
            for (String s : opArrays)
            {
                for (int i = 0; i < count; i++)
                {
                    if (opList.size() == level - 1)
                    {
                        break LOOP;
                    }
                    opList.add(s);
                }

            }
            count++;
        }
        System.out.println(opList);
        int len = (int) Math.pow(level, 0.5);
        int[][] arrays = new int[len][len];
        Demo03.level = level;
        // 找到中心点
        int x = arrays.length / 2, y = x;
        next(arrays, x, y, "上", 1);
        for (int i = 0; i < arrays.length; i++)
        {
            for (int j = 0; j < arrays[i].length; j++)
            {
                System.out.print(arrays[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static void next(int[][] arrays, int x, int y, String fx, int step)
    {
        // 第一次
        if (number == 1)
        {
            arrays[x][y] = number++;
            next(arrays, x, y, fx, 0);
        }
        // 最后一次
        else if (number == level)
        {
            arrays[x][y] = number;
        } else
        {
            switch (fx)
            {
                case "上":
                    arrays[--x][y] = number++;
                    break;
                case "下":
                    arrays[++x][y] = number++;
                    break;
                case "左":
                    arrays[x][--y] = number++;
                    break;
                case "右":
                    arrays[x][++y] = number++;
                    break;
            }
            switch (change(fx, step))
            {
                case 0:
                    next(arrays, x, y, fx, step);
                    break;
                case 1:
                    next(arrays, x, y, opList.get(number - 2), step);
                    break;
                case 2:
                    next(arrays, x, y, opList.get(number - 2), ++step);
                    break;
            }
        }
    }

    private static int change(String fx, int step)
    {
        // 转向
        if (opStep++ >= step)
        {
            opStep = 0;
            opIndex++;
            if (opIndex >= opArrays.length)
            {
                stepCount++;
                opIndex = 0;
                return 2;
            }
            return 1;
        }
        return 0;
    }
}
