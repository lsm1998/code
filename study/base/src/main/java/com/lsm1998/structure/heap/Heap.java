package com.lsm1998.structure.heap;

/**
 * @program: code
 * @description:
 * @author: lsm
 * @create: 2020-04-28 12:46
 **/
public interface Heap<E extends Comparable<? extends E>>
{
    void insert(E ele);

    E poll();

    E get();

    int size();

    boolean isEmpty();
}
