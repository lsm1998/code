package com.lsm1998.util.structure.list;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @作者：刘时明
 * @时间：2019/6/23-9:24
 * @作用：
 */
public class MySimpleArrayList<E> extends MyAbstractSimpleList<E>
{
    // 默认容量
    private static final int DEFAULT_CAPACITY = 10;
    // 共用的空线性表
    private static final Object[] EMPTY_ELEMENTDATA = {};
    // 具体存放元素的数组
    private Object[] elementData;
    // 线性表大小
    private int size;
    // 最大荷载因子
    private static final float MAX_LOAD = 0.75F;

    public MySimpleArrayList()
    {
        this(0);
    }

    public MySimpleArrayList(int capacity)
    {
        if (capacity < 0)
        {
            throw new RuntimeException("不可以创建长度小于0的数组");
        } else if (capacity > 0)
        {
            elementData = new Object[capacity];
        }
    }

    public MySimpleArrayList(E[] arr)
    {
        elementData = arr;
        size = arr.length;
    }

    @Override
    public Iterator<E> iterator()
    {
        return new MyArrayListIterator();
    }

    @Override
    public int size()
    {
        return size;
    }

    @Override
    public void add(int index, E ele)
    {
        MyAbstractSimpleList.checkBound(size, index);
        grow();
        System.arraycopy(elementData, index, elementData, index + 1, size - index);
        elementData[index] = ele;
        size++;
    }

    private void grow()
    {
        if (size == 0)
        {
            elementData = new Object[DEFAULT_CAPACITY];
        } else
        {
            if (size >= elementData.length * MAX_LOAD)
            {
                int newSize = elementData.length + (elementData.length >> 1);
                elementData = Arrays.copyOf(elementData, newSize);
            }
        }
    }

    @Override
    public void add(E ele)
    {
        add(size, ele);
    }

    @Override
    public boolean contains(Object o)
    {
        return indexOf(o) != -1;
    }

    @Override
    public boolean remove(Object o)
    {
        return remove(indexOf(o));
    }

    @Override
    public boolean remove(int index)
    {
        if (index == -1)
        {
            return false;
        }
        MyAbstractSimpleList.checkBound(size - 1, index);
        int numMoved = size - index - 1;
        if (numMoved > 0)
        {
            System.arraycopy(elementData, index + 1, elementData, index, numMoved);
        }
        elementData[--size] = null;
        return true;
    }

    @Override
    public void clear()
    {
        elementData = EMPTY_ELEMENTDATA;
        size = 0;
    }

    @Override
    public E get(int index)
    {
        MyAbstractSimpleList.checkBound(size - 1, index);
        return (E) elementData[index];
    }

    @Override
    public E set(int index, E ele)
    {
        MyAbstractSimpleList.checkBound(size - 1, index);
        E old = get(index);
        elementData[index] = ele;
        return old;
    }

    @Override
    public int indexOf(Object o)
    {
        for (int i = 0; i < size; i++)
        {
            E temp = get(i);
            if (temp == o)
            {
                return i;
            } else if (temp.equals(o))
            {
                return i;
            }
        }
        return -1;
    }

    private class MyArrayListIterator implements Iterator<E>
    {
        private int current = 0;

        @Override
        public void remove()
        {
            MySimpleArrayList.this.remove(--current);
        }

        @Override
        public boolean hasNext()
        {
            return current < size;
        }

        @Override
        public E next()
        {
            if (!hasNext())
                throw new NoSuchElementException();
            return MySimpleArrayList.this.get(current++);
        }
    }
}
