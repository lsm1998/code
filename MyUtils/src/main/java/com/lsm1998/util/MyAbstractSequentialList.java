package com.lsm1998.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @作者：刘时明
 * @时间：2019/6/20-23:33
 * @作用：基本的List接口实现
 */
public abstract class MyAbstractSequentialList<E> extends MyAbstractList<E>
{
    protected MyAbstractSequentialList()
    {
    }

    public E get(int index)
    {
        try
        {
            return listIterator(index).next();
        } catch (NoSuchElementException exc)
        {
            throw new IndexOutOfBoundsException("Index: " + index);
        }
    }

    public E set(int index, E element)
    {
        try
        {
            MyListIterator<E> e = listIterator(index);
            E oldVal = e.next();
            e.set(element);
            return oldVal;
        } catch (NoSuchElementException exc)
        {
            throw new IndexOutOfBoundsException("Index: " + index);
        }
    }

    public void add(int index, E element)
    {
        try
        {
            listIterator(index).add(element);
        } catch (NoSuchElementException exc)
        {
            throw new IndexOutOfBoundsException("Index: " + index);
        }
    }

    public E remove(int index)
    {
        try
        {
            MyListIterator<E> e = listIterator(index);
            E outCast = e.next();
            e.remove();
            return outCast;
        } catch (NoSuchElementException exc)
        {
            throw new IndexOutOfBoundsException("Index: " + index);
        }
    }

    public boolean addAll(int index, MyCollection<? extends E> c)
    {
        try
        {
            boolean modified = false;
            MyListIterator<E> e1 = listIterator(index);
            for (E e : c)
            {
                e1.add(e);
                modified = true;
            }
            return modified;
        } catch (NoSuchElementException exc)
        {
            throw new IndexOutOfBoundsException("Index: " + index);
        }
    }

    public Iterator<E> iterator()
    {
        return listIterator();
    }

    public abstract MyListIterator<E> listIterator(int index);
}
