package com.lsm1998.algorithm.search;

import com.lsm1998.utils.FileUtil;
import org.junit.Test;

import java.util.Arrays;

public class Maze
{
    private int initX = 1;
    private int initY = 1;

    // 墙体
    private static final int WALL = 1;
    // 道路
    private static final int ROAD = 0;
    // 终点
    private static final int END = 2;
    // 通过
    private static final int PASS = 3;

    @Test
    public void testMaze()
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
        map[x][y] = PASS;
        if (left == END || right == END || up == END || down == END)  // 是否已经达到终点
        {
            success(map);
        } else if ((left == PASS || left == WALL) && (right == PASS || right == WALL) // 是否已经达到绝境
                && (up == PASS || up == WALL) && (down == PASS || down == WALL))
        {
            error(map);
        } else
        {
            if (tryWalk(map, x - 1, y)) // 尝试左边走
            {
                walk(map, x - 1, y);
            } else if (tryWalk(map, x, y + 1)) // 尝试上边走
            {
                walk(map, x, y + 1);
            } else if (tryWalk(map, x + 1, y)) // 尝试右边走
            {
                walk(map, x + 1, y);
            } else if (tryWalk(map, x, y - 1))// 尝试下边走
            {
                walk(map, x, y - 1);
            }
        }
    }

    /**
     * @param map
     * @param x
     * @param y
     * @return 是否可以走
     */
    private boolean tryWalk(int[][] map, int x, int y)
    {
        return map[x][y] == ROAD || map[x][y] == END;
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
}
