package com.lsm1998.algorithm.search;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 吃金币最优路径算法
 *
 * 移动只能向右、下、右下三个方向
 */
public class BasePath
{
    @Test
    public void testBasePath()
    {
        int[][] map = new int[][]{
                {5, 4, 2, 2},
                {8, 0, 5, 7},
                {4, 1, 2, 0},
                {1, 4, 6, 3},
        };

        List<int[]> path = baseChoice(map);
        System.out.println(path.stream().map(x -> String.format("(%d,%d)", x[0], x[1])).collect(Collectors.toList()));
        for (var p : path)
        {
            System.out.print(map[p[1]][p[0]]);
            System.out.print("->");
        }
        System.out.println();
    }

    private List<int[]> baseChoice(int[][] map)
    {
        LinkedList<int[]> path = new LinkedList<>();
        int L = map.length;
        // (分数,x坐标,y坐标)
        var dp = new int[L + 1][L + 1][3];

        for (int i = 1; i < L + 1; i++)
        {
            dp[i][0][0] = -1000;
            dp[0][i][0] = -1000;
        }

        for (int y = 1; y < L + 1; y++)
        {
            for (int x = 1; x < L + 1; x++)
            {
                int max = dp[y - 1][x - 1][0];
                int px = x - 1;
                int py = y - 1;
                if (dp[y - 1][x][0] > max)
                {
                    max = dp[y - 1][x][0];
                    px = x;
                }
                if (dp[y][x - 1][0] > max)
                {
                    max = dp[y][x - 1][0];
                    px = x - 1;
                    py = y;
                }
                dp[y][x][0] = max + map[y - 1][x - 1];
                dp[y][x][1] = px;
                dp[y][x][2] = py;

                System.out.format("fill:dp=%d,px=%d,py=%d\n", dp[y][x][0], px, py);
            }
        }
        // 打印dp表格
        System.out.println("--- dp tables ---");
        for (int i = 0; i < L + 1; i++)
        {
            for (int j = 0; j < L + 1; j++)
            {
                System.out.format("%7d", dp[i][j][0]);
            }
            System.out.println();
        }

        // 构造路径
        int x = L, y = L;
        path.addFirst(new int[]{x - 1, y - 1});
        while (true)
        {
            int px = dp[y][x][1];
            int py = dp[y][x][2];
            x = px;
            y = py;
            if (x == 0 && y == 0)
            {
                break;
            }
            path.addFirst(new int[]{x - 1, y - 1});
        }
        return path;
    }
}
