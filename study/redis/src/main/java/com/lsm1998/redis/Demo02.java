package com.lsm1998.redis;

public class Demo02
{
    public static void main(String[] args)
    {
        System.out.println(matchString("aa","a"));
        System.out.println(matchString("aa","a*"));
        System.out.println(matchString("ab",".*"));
        System.out.println(matchString("aab","c*a*b"));
        System.out.println(matchString("mississippi","mis*is*p"));
    }

    /**
     * @param array
     * @param sIndex
     * @param pattern
     * @param pIndex
     * @return
     */
    private static boolean matchCore(char[] array, int sIndex, char[] pattern, int pIndex)
    {
        int sLen = array.length;
        int pLen = pattern.length;
        if (sIndex >= sLen && pIndex >= pLen)
        {
            return true;
        }
        if (sIndex < sLen && pIndex >= pLen)
        {
            return false;
        }
        if ((pIndex + 1) < pLen && pattern[pIndex + 1] == '*')
        {
            if (sIndex >= sLen)
            {
                return matchCore(array, sIndex, pattern, pIndex + 2);
            } else
            {
                if (array[sIndex] == pattern[pIndex] || pattern[pIndex] == '.')
                {
                    return matchCore(array, sIndex + 1, pattern, pIndex + 2)
                            || matchCore(array, sIndex + 1, pattern, pIndex)
                            || matchCore(array, sIndex, pattern, pIndex + 2);
                } else
                {
                    return matchCore(array, sIndex, pattern, pIndex + 2);
                }
            }

        }
        if (array[sIndex] == pattern[pIndex] || pattern[pIndex] == '.')
        {
            return matchCore(array, sIndex + 1, pattern, pIndex + 1);
        }
        return false;
    }

    public static boolean matchString(String str, String pattern)
    {
        if (str == null || pattern == null)
        {
            return false;
        }
        return matchCore(str.toCharArray(), 0, pattern.toCharArray(), 0);
    }
}
