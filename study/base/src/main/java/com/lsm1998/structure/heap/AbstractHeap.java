/*
 * 作者：刘时明
 * 时间：2020/4/28-23:55
 * 作用：
 */
package com.lsm1998.structure.heap;

public abstract class AbstractHeap<E extends Comparable<? extends E>> implements Heap<E>
{
    @Override
    public boolean isEmpty()
    {
        return size()==0;
    }
}
