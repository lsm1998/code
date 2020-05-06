/*
 * 作者：刘时明
 * 时间：2020/5/2-0:45
 * 作用：
 */
package com.lsm1998.structure.list;

public class MyArrayList<E> extends MyAbstractList<E>
{
    private int size;
    private Object[] data;

    @Override
    public int size()
    {
        return 0;
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
        return new Object[0];
    }

    @Override
    public void clear()
    {

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
