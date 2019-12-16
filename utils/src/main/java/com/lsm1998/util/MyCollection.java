package com.lsm1998.util;

import java.util.Iterator;

/**
 * @作者：刘时明
 * @时间：2019/5/20-8:24
 * @作用：自定义集合顶层接口
 */
public interface MyCollection<E> extends Iterable<E>
{
    int size();

    boolean isEmpty();

    boolean contains(Object o);

    Iterator<E> iterator();

    Object[] toArray();

    <T> T[] toArray(T[] a);

    boolean add(E e);

    boolean remove(Object o);

    boolean addAll(MyCollection<? extends E> c);

    boolean removeAll(MyCollection<?> c);

    void clear();
}
