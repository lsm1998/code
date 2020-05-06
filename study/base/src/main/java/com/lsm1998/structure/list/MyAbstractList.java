/*
 * 作者：刘时明
 * 时间：2020/5/2-0:16
 * 作用：
 */
package com.lsm1998.structure.list;

public abstract class MyAbstractList<E> implements MyList<E>
{
    @Override
    public boolean isEmpty()
    {
        return size() == 0;
    }

    @Override
    public boolean add(E element)
    {
        return add(size(), element);
    }
}
