/*
 * 作者：刘时明
 * 时间：2020/5/2-0:45
 * 作用：
 */
package com.lsm1998.structure.list;

import java.util.Arrays;

public class MyArrayList<E> extends MyAbstractList<E>
{
    private int size;
    private transient Object[] data;

    private static final Object[] EMPTY = new Object[0];

    private static final int DEFAULT_CAP = 10;

    public MyArrayList()
    {
        this(DEFAULT_CAP);
    }

    public MyArrayList(int cap)
    {
        if (cap < 0)
        {
            throw new RuntimeException("cap less 0!");
        } else if (cap == 0)
        {
            this.data = EMPTY;
        } else
        {
            this.data = new Object[cap];
        }
    }

    @Override
    public int size()
    {
        return size;
    }

    @Override
    public boolean add(int index, E element)
    {
        return false;
    }

    @Override
    public boolean remove(E element)
    {
        return false;
    }

    @Override
    public boolean remove(int index)
    {
        return false;
    }

    @Override
    public boolean contains(E element)
    {
        return false;
    }

    @Override
    public Object[] toArray()
    {
        return Arrays.copyOf(data, size);
    }

    @Override
    public void clear()
    {
        this.data = EMPTY;
        this.size = 0;
    }

    @Override
    public E get(int index)
    {
        return null;
    }

    @Override
    public E set(int index, E element)
    {
        return null;
    }

    @Override
    public int indexOf(E element)
    {
        return 0;
    }

    @Override
    public int lastIndexOf(E element)
    {
        return 0;
    }
}
