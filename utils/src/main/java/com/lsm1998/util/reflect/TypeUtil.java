package com.lsm1998.util.reflect;

/**
 * @作者：刘时明
 * @时间：2019/5/24-16:49
 * @作用：类型验证工具类
 */
public class TypeUtil
{
    /**
     * 是否为数值类型
     *
     * @param o
     * @return
     */
    public static boolean isNumber(Object o)
    {
        return o instanceof Number;
    }

    /**
     * 是否为数值类型
     *
     * @param c
     * @return
     */
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
        } else return c == double.class || c == Double.class;
    }

    /**
     * 返回对应的类型数组
     *
     * @param values 参数数组
     * @return
     */
    public static Class<?>[] types(Object... values)
    {
        if (values == null)
        {
            return new Class[0];
        } else
        {
            Class<?>[] result = new Class[values.length];
            for (int i = 0; i < values.length; ++i)
            {
                Object value = values[i];
                result[i] = value != null ? value.getClass() : null;
            }
            return result;
        }
    }
}
