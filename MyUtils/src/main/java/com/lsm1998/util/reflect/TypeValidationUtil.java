package com.lsm1998.util.reflect;

/**
 * @作者：刘时明
 * @时间：2019/5/24-16:49
 * @作用：类型验证工具类
 */
public class TypeValidationUtil
{
    public static boolean isNumber(Object o)
    {
        return o instanceof Number;
    }

    public static boolean isNumber(Class<?> c)
    {
        if (c == byte.class || c == Byte.class)
        {
            return true;
        } else if (c == short.class || c == Short.class)
        {
            return true;
        } else if (c == int.class || c == Integer.class)
        {
            return true;
        } else if (c == long.class || c == Long.class)
        {
            return true;
        } else if (c == float.class || c == Float.class)
        {
            return true;
        } else if (c == double.class || c == Double.class)
        {
            return true;
        } else
        {
            return false;
        }
    }
}
