package com.lsm1998.structure.list;

import java.util.Iterator;

/**
 * @program: code
 * @description:
 * @author: lsm
 * @create: 2020-04-20 18:03
 **/
public interface MyList<E>
{
    int size();

    boolean isEmpty();

    boolean contains(Object var1);

    Iterator<E> iterator();

    Object[] toArray();

    <T> T[] toArray(T[] var1);

    boolean add(E var1);

    boolean remove(Object var1);

    void clear();

    boolean equals(Object var1);

    int hashCode();

    E get(int var1);

    E set(int var1, E var2);

    void add(int var1, E var2);

    E remove(int var1);

    int indexOf(Object var1);

    int lastIndexOf(Object var1);

    MyListIterator<E> listIterator();

    MyListIterator<E> listIterator(int var1);
}
