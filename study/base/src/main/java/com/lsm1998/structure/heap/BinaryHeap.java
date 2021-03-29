/*
 * 作者：刘时明
 * 时间：2020/4/28-23:55
 * 作用：
 */
package com.lsm1998.structure.heap;

import java.util.function.Consumer;

public class BinaryHeap<E extends Comparable<? super E>> extends AbstractHeap<E>
{
    private int size;

    private Object[] data;

    public BinaryHeap()
    {
        this(10);
    }

    public BinaryHeap(int cap)
    {
        if (cap < 0)
        {
            throw new IllegalArgumentException();
        }
        data = new Object[cap];
    }

    @Override
    public void insert(E element)
    {
        insert(size + 1, element);
    }

    private void insert(int index, E element)
    {
        if (size >= data.length - 1)
        {
            dilatationCapacity(size * 2);
        }
        data[index] = element;
        // 和父节点比较
        this.siftUp(element, index);
        this.size++;
    }

    @Override
    public E poll()
    {
        return null;
    }

    @Override
    public E get()
    {
        return get(size - 1);
    }

    @Override
    public E get(int index)
    {
        return (E) data[index];
    }

    @Override
    public int size()
    {
        return size;
    }

    @Override
    public void forEach(Consumer<E> consumer)
    {
        for (int i = 1; i < size + 1; i++)
        {
            consumer.accept((E) this.data[i]);
        }
    }

    private void swap(int i, int j)
    {
        E temp = get(i);
        data[i] = data[j];
        data[j] = temp;
    }

    /**
     * 扩容方法
     *
     * @param capacity
     */
    private void dilatationCapacity(int capacity)
    {
        Object[] newData = new Object[capacity];
        System.arraycopy(data, 0, newData, 0, size);
        data = newData;
    }

    private void siftDown()
    {

    }

    private void siftUp(E element, int index)
    {
        // 获取父节点下标
        int parentIndex = index / 2;
        // 判断是否一直比父节点大
        while (index > 1 && less(get(parentIndex), element))
        {
            this.swap(parentIndex, index);
            index = parentIndex;
            parentIndex = index / 2;
        }
    }

    private boolean less(E v1, E v2)
    {
        return v1.compareTo(v2) < 0;
    }
}
