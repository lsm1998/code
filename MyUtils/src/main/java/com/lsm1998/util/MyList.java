package com.lsm1998.util;

import java.util.Iterator;

/**
 * @作者：刘时明
 * @时间：2019/5/20-8:28
 * @作用：
 */
public interface MyList<E> extends MyCollection<E>
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

    boolean addAll(int index, MyCollection<? extends E> c);

    boolean removeAll(MyCollection<?> c);

    void clear();

    E get(int index);

    E set(int index, E element);

    void add(int index, E element);

    E remove(int index);

    int indexOf(Object o);

    int lastIndexOf(Object o);
}
