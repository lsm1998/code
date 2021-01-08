package com.lsm1998.algorithm.base;

/**
 * 字符串完全匹配
 */
public class StringFind
{
    public static boolean indexOf(String str, String find)
    {
        if (str == null || find == null)
        {
            throw new RuntimeException("匹配字符串不可以为空！");
        }
        for (int i = 0; i < str.length(); i++)
        {
            if (match(str, find, i))
            {
                return true;
            }
        }
        return false;
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
}
