package com.lsm1998.util;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

/**
 * @作者：刘时明
 * @时间：2019/5/20-8:35
 * @作用：Collection抽象实现类，默认实现了部分方法，例如toString
 */
public abstract class MyAbstractCollection<E> implements MyCollection<E>
{
    protected MyAbstractCollection()
    {
    }

    public abstract Iterator<E> iterator();

    public abstract int size();

    public boolean isEmpty()
    {
        return size() == 0;
    }

    public boolean contains(Object o)
    {
        Iterator<E> it = iterator();
        if (o == null)
        {
            while (it.hasNext())
            {
                if (it.next() == null)
                {
                    return true;
                }
            }
        } else
        {
            while (it.hasNext())
            {
                if (o.equals(it.next()))
                {
                    return true;
                }
            }
        }
        return false;
    }

    public Object[] toArray()
    {
        Object[] r = new Object[size()];
        Iterator<E> it = iterator();
        for (int i = 0; i < r.length; i++)
        {
            if (!it.hasNext())
                return Arrays.copyOf(r, i);
            r[i] = it.next();
        }
        return it.hasNext() ? finishToArray(r, it) : r;
    }

    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    private static <T> T[] finishToArray(T[] r, Iterator<?> it)
    {
        int i = r.length;
        while (it.hasNext())
        {
            int cap = r.length;
            if (i == cap)
            {
                int newCap = cap + (cap >> 1) + 1;
                // overflow-conscious code
                if (newCap - MAX_ARRAY_SIZE > 0)
                    newCap = hugeCapacity(cap + 1);
                r = Arrays.copyOf(r, newCap);
            }
            r[i++] = (T) it.next();
        }
        // trim if overallocated
        return (i == r.length) ? r : Arrays.copyOf(r, i);
    }

    private static int hugeCapacity(int minCapacity)
    {
        if (minCapacity < 0) // overflow
            throw new OutOfMemoryError
                    ("Required array size too large");
        return (minCapacity > MAX_ARRAY_SIZE) ?
                Integer.MAX_VALUE :
                MAX_ARRAY_SIZE;
    }

    public <T> T[] toArray(T[] a)
    {
        // Estimate size of array; be prepared to see more or fewer elements
        int size = size();
        T[] r = a.length >= size ? a :
                (T[]) java.lang.reflect.Array
                        .newInstance(a.getClass().getComponentType(), size);
        Iterator<E> it = iterator();

        for (int i = 0; i < r.length; i++)
        {
            if (!it.hasNext())
            { // fewer elements than expected
                if (a == r)
                {
                    r[i] = null; // null-terminate
                } else if (a.length < i)
                {
                    return Arrays.copyOf(r, i);
                } else
                {
                    System.arraycopy(r, 0, a, 0, i);
                    if (a.length > i)
                    {
                        a[i] = null;
                    }
                }
                return a;
            }
            r[i] = (T) it.next();
        }
        // more elements than expected
        return it.hasNext() ? finishToArray(r, it) : r;
    }

    public boolean add(E e)
    {
        throw new RuntimeException("不可以直接调用" + this.getClass().getName() + "的add方法");
    }

    public boolean remove(Object o)
    {
        Iterator<E> it = iterator();
        if (o == null)
        {
            while (it.hasNext())
            {
                if (it.next() == null)
                {
                    it.remove();
                    return true;
                }
            }
        } else
        {
            while (it.hasNext())
            {
                if (o.equals(it.next()))
                {
                    it.remove();
                    return true;
                }
            }
        }
        return false;
    }

    public boolean addAll(MyCollection<? extends E> c)
    {
        boolean modified = false;
        for (E e : c)
            if (add(e))
                modified = true;
        return modified;
    }

    public boolean removeAll(MyCollection<?> c)
    {
        Objects.requireNonNull(c);
        boolean modified = false;
        Iterator<?> it = iterator();
        while (it.hasNext())
        {
            if (c.contains(it.next()))
            {
                it.remove();
                modified = true;
            }
        }
        return modified;
    }

    public void clear()
    {
        Iterator<E> it = iterator();
        while (it.hasNext())
        {
            it.next();
            it.remove();
        }
    }

    public String toString()
    {
        Iterator<E> it = iterator();
        if (!it.hasNext())
            return "[]";
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (; ; )
        {
            E e = it.next();
            sb.append(e == this ? "(this MyCollection)" : e);
            if (!it.hasNext())
                return sb.append(']').toString();
            sb.append(',').append(' ');
        }
    }
}
