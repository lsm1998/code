package com.lsm1998.util.concurrent;

import java.util.*;

public class MyCopyOnWriteArrayList<E>
{
    // synchronized加锁使用
    final transient Object lock = new Object();

    // 存储元素的数组，volatile修饰，保证可见性和有序性
    private transient volatile Object[] array;

    public MyCopyOnWriteArrayList()
    {
        this.array = new Object[0];
    }

    public int size()
    {
        return this.array.length;
    }

    public boolean isEmpty()
    {
        return this.array.length == 0;
    }

    public boolean contains(Object o)
    {
        return indexOf(o) >= 0;
    }

    public boolean add(E e)
    {
        // add方法是线程同步的
        // add方法操作过程中，不影响其他线程读取，不能实时同步
        synchronized (lock)
        {
            Object[] es = this.array;
            int len = es.length;
            // 拷贝一个新数组出来做修改操作
            es = Arrays.copyOf(es, len + 1);
            es[len] = e;
            // this.array=新数组
            this.array = es;
            return true;
        }
    }

    public boolean remove(Object o)
    {
        synchronized (lock)
        {
            // 这个方法负责保证在遍历元素过程中不允许add/remove
            // checkForComodification();
            int index = indexOf(o);
            if (index == -1)
                return false;
            remove(index);
            return true;
        }
    }


    public void clear()
    {
        synchronized (lock)
        {
            this.array = new Object[0];
        }
    }

    public E get(int index)
    {
        return elementAt(this.array, index);
    }

    public E set(int index, E element)
    {
        synchronized (lock)
        {
            Object[] es = this.array;
            E oldValue = elementAt(es, index);

            if (oldValue != element)
            {
                es = es.clone();
                es[index] = element;
                this.array = es;
            }
            return oldValue;
        }
    }

    public void add(int index, E element)
    {
        synchronized (lock)
        {
            Object[] es = this.array;
            int len = es.length;
            if (index > len || index < 0)
                throw new IndexOutOfBoundsException(outOfBounds(index, len));
            Object[] newElements;
            int numMoved = len - index;
            if (numMoved == 0)
                newElements = Arrays.copyOf(es, len + 1);
            else
            {
                newElements = new Object[len + 1];
                System.arraycopy(es, 0, newElements, 0, index);
                System.arraycopy(es, index, newElements, index + 1,
                        numMoved);
            }
            newElements[index] = element;
            this.array = newElements;
        }
    }

    static String outOfBounds(int index, int size)
    {
        return "Index: " + index + ", Size: " + size;
    }

    static <E> E elementAt(Object[] a, int index)
    {
        return (E) a[index];
    }

    public E remove(int index)
    {
        synchronized (lock)
        {
            Object[] es = this.array;
            int len = es.length;
            // 获取要被删除的元素，可以会抛出数组越界异常
            E oldValue = elementAt(es, index);
            int numMoved = len - index - 1;
            Object[] newElements;
            // 通过numMoved判断是否删除最后一个元素
            if (numMoved == 0)
                newElements = Arrays.copyOf(es, len - 1);
            else
            {
                // 删除非尾部元素需要分段copy
                newElements = new Object[len - 1];
                System.arraycopy(es, 0, newElements, 0, index);
                System.arraycopy(es, index + 1, newElements, index,
                        numMoved);
            }
            // 设置array为新数组
            this.array = newElements;
            return oldValue;
        }
    }

    public int indexOf(Object o)
    {
        Object[] es = this.array;
        return indexOfRange(o, es, 0, es.length);
    }

    public int lastIndexOf(Object o)
    {
        Object[] es = this.array;
        return lastIndexOfRange(o, es, 0, es.length);
    }

    private static int indexOfRange(Object o, Object[] es, int from, int to)
    {
        if (o == null)
        {
            for (int i = from; i < to; i++)
                if (es[i] == null)
                    return i;
        } else
        {
            for (int i = from; i < to; i++)
                if (o.equals(es[i]))
                    return i;
        }
        return -1;
    }

    private static int lastIndexOfRange(Object o, Object[] es, int from, int to)
    {
        if (o == null)
        {
            for (int i = to - 1; i >= from; i--)
                if (es[i] == null)
                    return i;
        } else
        {
            for (int i = to - 1; i >= from; i--)
                if (o.equals(es[i]))
                    return i;
        }
        return -1;
    }
}
