package com.lsm1998.algorithm.search;

import org.junit.Test;

/**
 * 八皇后问题
 */
public class EQueen
{
    @Test
    public void testEQueue()
    {
        EQueen queen = new EQueen();
        queen.solve(0);
        queen.print();
    }

    int[] queens = new int[8];

    private boolean solve(int col)
    {
        if (col == 8)
            return true;
        for (int i = 0; i < queens.length; i++)
        {
            queens[col] = i;
            boolean flag = true;
            for (int j = 0; j < col; j++)
            {
                int rowDiff = Math.abs(queens[j] - i);
                int colDiff = col - j;
                if (queens[j] == i || rowDiff == colDiff)
                {
                    flag = false;
                    break;
                }
            }
            if (flag && solve(col + 1))
                return true;
        }
        return false;
    }

    private void print()
    {
        for (int i = 0; i < queens.length; i++)
        {
            for (int j = 0; j < queens.length; j++)
            {
                if (queens[i] == j)
                {
                    System.out.print("Q");
                } else
                {
                    System.out.print(".");
                }
            }
            System.out.println();
        }
    }
}
