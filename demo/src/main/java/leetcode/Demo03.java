/*
 * 作者：刘时明
 * 时间：2020/1/1-22:57
 * 作用：
 */
package leetcode;

import java.util.*;

/**
 * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
 */
public class Demo03
{
    public static int lengthOfLongestSubstring(String s)
    {
        if (s.length() == 0)
        {
            return 0;
        }
        int max = 0;
        Set<Character> set = new HashSet<>();
        List<Integer> list = new ArrayList<>();
        //list.add(0);
        for (int i = 0; i < s.length(); i++)
        {
            if (!set.add(s.charAt(i)))
            {
                list.add(i);
            }
        }
        //list.add(s.length()-1);
        System.out.println(list);
        return max;
    }

    public static int lengthOfLongestSubstring1(String s)
    {
        if (s.length() == 0)
        {
            return 0;
        }
        String temp;
        int max = 1;
        Set<Character> set = new HashSet<>();
        for (int i = 0; i < s.length() - 1; i++)
        {
            C:
            for (int j = i + 1; j < s.length(); j++)
            {
                temp = s.substring(i, j + 1);
                if (temp.length() <= max)
                {
                    continue;
                }

                set.clear();
                for (int k = 0; k < temp.length(); k++)
                {
                    if (!set.add(temp.charAt(k)))
                    {
                        break C;
                    }
                }
                max = temp.length();
            }
        }
        return max;
    }

    public static void main(String[] args)
    {
        System.out.println(lengthOfLongestSubstring1("abba"));
        System.out.println(lengthOfLongestSubstring1("dvdf"));
    }
}
