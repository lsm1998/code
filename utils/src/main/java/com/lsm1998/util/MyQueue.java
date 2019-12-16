package com.lsm1998.util;

/**
 * @作者：刘时明
 * @时间：2019/6/20-23:36
 * @作用：
 */
public interface MyQueue<E> extends MyCollection<E>
{
    boolean add(E e);

    boolean offer(E e);

    E remove();

    E poll();

    E element();

    E peek();
}
