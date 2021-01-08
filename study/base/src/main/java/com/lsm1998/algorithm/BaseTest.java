/*
 * 作者：刘时明
 * 时间：2020/4/29-23:57
 * 作用：
 */
package com.lsm1998.algorithm;

import com.lsm1998.algorithm.base.BinarySearchFind;
import com.lsm1998.algorithm.base.Hanoi;
import com.lsm1998.utils.FileUtil;
import org.junit.Test;

import java.util.Arrays;

public class BaseTest
{
    private int initX = 1;
    private int initY = 1;

    // 墙体
    private static final int WALL = 1;
    // 道路
    private static final int ROAD = 0;
    // 终点
    private static final int END = 8;
    // 通过
    private static final int PASS = 3;

    @Test
    public void mazeTest()
    {
        int[][] map = FileUtil.getArrByFile("/map/1.txt", 10, 10);
        walk(map, initX, initY);
    }

    private void walk(int[][] map, int x, int y)
    {
        int left = map[x - 1][y];
        int right = map[x + 1][y];
        int up = map[x][y - 1];
        int down = map[x][y + 1];

        if (x == END || y == END)
        {
            // 终点
            success(map);
        } else if ((left == PASS || left == WALL) && (right == PASS || right == WALL)
                && (up == PASS || up == WALL) && (down == PASS || down == WALL))
        {
            // 绝境
            error(map);
        } else
        {
            if (tryWalk(map, x - 1, y))
            {
                map[x][y] = PASS;
                walk(map, x - 1, y);
            }
        }
    }

    private boolean tryWalk(int[][] map, int x, int y)
    {
        return map[x][y] == PASS || map[x][y] == END;
    }

    private void success(int[][] map)
    {
        System.out.println("----------------ok-------------");
        for (int i = 0; i < map.length; i++)
        {
            System.out.println(Arrays.toString(map[i]));
        }
    }

    private void error(int[][] map)
    {
        System.out.println("----------------error-------------");
        for (int i = 0; i < map.length; i++)
        {
            System.out.println(Arrays.toString(map[i]));
        }
    }

    @Test
    public void test1()
    {
        Integer[] arr = {1, 2, 3, 5, 7, 8, 9, 10, 18, 20};
        System.out.println(BinarySearchFind.binarySearchFind(arr, 1));
        System.out.println(BinarySearchFind.binarySearchFind(arr, 20));
        System.out.println(BinarySearchFind.binarySearchFind(arr, 10));
    }

    @Test
    public void test2()
    {
        Hanoi.hanoi(64,'A','B','C');
    }
}
