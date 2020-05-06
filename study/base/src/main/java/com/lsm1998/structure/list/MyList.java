/*
 * 作者：刘时明
 * 时间：2020/5/2-0:11
 * 作用：
 */
package com.lsm1998.structure.list;

public interface MyList<E>
{
    int size();

    boolean isEmpty();

    boolean add(E element);

    boolean add(int index, E element);

    boolean remove(E element);

    boolean remove(int index);

    boolean contains(E element);

    Object[] toArray();

    void clear();

    boolean equals(Object o);

    int hashCode();

    E get(int index);

    E set(int index, E element);

    int indexOf(E element);

    int lastIndexOf(E element);
}
