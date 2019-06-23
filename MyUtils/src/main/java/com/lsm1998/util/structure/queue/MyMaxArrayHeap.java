package com.lsm1998.util.structure.queue;

import com.lsm1998.util.array.MyArrays;
import com.lsm1998.util.structure.list.MySimpleArrayList;

/**
 * @作者：刘时明
 * @时间：2019/6/23-9:23
 * @作用：自定义最大二叉堆，基于数组实现
 */
public class MyMaxArrayHeap<E extends Comparable<? super E>>
{
    private MySimpleArrayList<E> list;

    public MyMaxArrayHeap()
    {
        this(0);
    }

    public MyMaxArrayHeap(int capacity)
    {
        list = new MySimpleArrayList<>(capacity);
    }

    public int size()
    {
        return list.size();
    }

    public boolean isEmpty()
    {
        return list.isEmpty();
    }

    private int parent(int index)
    {
        if (index == 0)
        {
            throw new IllegalArgumentException("index=0");
        }
        return (index - 1) / 2;
    }

    private int leftChild(int index)
    {
        return index * 2 + 1;
    }

    private int rightChild(int index)
    {
        return index * 2 + 2;
    }

    /**
     * 向堆中添加元素
     *
     * @param ele
     */
    public void add(E ele)
    {
        list.add(ele);
        siftUp(list.size() - 1);
    }

    /**
     * 获取最大元素
     *
     * @return
     */
    public E getMax()
    {
        return size() > 0 ? list.get(0) : null;
    }

    /**
     * 弹出最大元素
     *
     * @return
     */
    public E extractMax()
    {
        E ret = getMax();
        if (ret != null)
        {
            MyArrays.swap(0, list.size() - 1, list);
            list.remove(list.size() - 1);
            siftDown(0);
        }
        return ret;
    }

    private void siftUp(int index)
    {
        while (index > 0 && list.get(parent(index)).compareTo(list.get(index)) < 0)
        {

            MyArrays.swap(index, parent(index), list);
            index = parent(index);
        }
    }

    private void siftDown(int index)
    {
        int size = size();
        while (leftChild(index) < size)
        {
            int temp = leftChild(index);
            // 是否存在右子节点且比左子节点大
            if (rightChild(index) < size && list.get(temp + 1).compareTo(list.get(temp)) > 0)
            {
                temp = rightChild(index);
            }
            if (list.get(index).compareTo(list.get(temp)) >= 0)
            {
                break;
            }
            MyArrays.swap(index, temp, list);
            index = temp;
        }
    }

    /**
     * 取出最大元素，同时新增新元素
     *
     * @param ele
     */
    public E replace(E ele)
    {
        E ret = getMax();
        list.set(0, ele);
        siftDown(0);
        return ret;
    }

    @Override
    public String toString()
    {
        return list.toString();
    }
}
