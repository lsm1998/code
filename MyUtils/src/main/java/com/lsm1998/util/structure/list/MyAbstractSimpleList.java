package com.lsm1998.util.structure.list;

import java.util.Iterator;

/**
 * @作者：刘时明
 * @时间：2019/6/21-23:36
 * @作用：自定义简单List抽象实现类
 */
public abstract class MyAbstractSimpleList<E> implements MySimpleList<E>
{
    protected MyAbstractSimpleList()
    {
    }

    @Override
    public boolean isEmpty()
    {
        return size() == 0;
    }

    public abstract Iterator<E> iterator();

    public abstract int size();

    /**
     * 检查是否越界
     *
     * @param low
     * @param hig
     * @param curr
     */
    public static void checkBound(int low, int hig, int curr)
    {
        if (curr < low || curr >= hig)
        {
            new RuntimeException("越界异常：curr=" + curr);
        }
    }

    /**
     * 检查是否越界
     *
     * @param hig
     * @param curr
     */
    public static void checkBound(int hig, int curr)
    {
        checkBound(0, hig, curr);
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder("[");
        Iterator<E> iterator = iterator();
        while (iterator.hasNext())
        {
            sb.append(iterator.next() + ",");
        }
        if (sb.length() > 1)
        {
            sb.delete(sb.length() - 1, sb.length());
        }
        return sb.append("]").toString();
    }
}
