package com.lsm1998.algorithm.base;

/**
 * 字符串完全匹配
 */
public class StringFind
{
    /**
     * 暴力匹配
     *
     * @param str
     * @param find
     * @return
     */
    public static int indexOf(String str, String find)
    {
        if (str == null || find == null)
        {
            throw new RuntimeException("匹配字符串不可以为空！");
        }
        for (int i = 0; i < str.length(); i++)
        {
            if (match(str, find, i))
            {
                return i;
            }
        }
        return -1;
    }

    private static boolean match(String str, String find, int i)
    {
        for (int j = i; j - i < find.length(); j++)
        {
            if (str.charAt(j) != find.charAt(j - i))
            {
                return false;
            }
        }
        return true;
    }

    public static int kmpIndexOf(String str, String find)
    {
        return kmpFind(str, find, kmpNext(find));
    }

    private static int kmpFind(String str, String find, int[] next)
    {
        for (int i = 0, j = 0; i < str.length(); i++)
        {
            while (j > 0 && str.charAt(i) != find.charAt(j))
            {
                j = next[j - 1];
            }
            if (str.charAt(i) == find.charAt(j))
            {
                j++;
            }
            if (j == find.length())
            {
                return i - j + 1;
            }
        }
        return -1;
    }

    private static int[] kmpNext(String dist)
    {
        int[] next = new int[dist.length()];
        next[0] = 0;
        for (int i = 1, j = 0; i < dist.length(); i++)
        {
            // KMP算法核心
            while (j > 0 && dist.charAt(i) != dist.charAt(j))
            {
                j = next[j - 1];
            }
            // 是否满足部分匹配
            if (dist.charAt(i) == dist.charAt(j))
            {
                j++;
            }
            next[i] = j;
        }
        return next;
    }
}
