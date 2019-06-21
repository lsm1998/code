package com.lsm1998.util;

import java.util.Iterator;

/**
 * @作者：刘时明
 * @时间：2019/5/25-22:27
 * @作用：
 */
public interface MySet<E> extends MyCollection<E>
{
    int size();

    boolean contains(Object o);

    Iterator<E> iterator();

    Object[] toArray();

    <T> T[] toArray(T[] a);

    boolean remove(Object o);

    boolean addAll(MyCollection<? extends E> c);
}
