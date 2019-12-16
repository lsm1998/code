package com.lsm1998.util;

import java.util.Iterator;

/**
 * @作者：刘时明
 * @时间：2019/5/20-8:58
 * @作用：
 */
public interface MyListIterator<E> extends Iterator<E>
{
    boolean hasNext();

    E next();

    boolean hasPrevious();

    E previous();

    int nextIndex();

    int previousIndex();

    void remove();

    void set(E e);

    void add(E e);
}
