package com.lsm1998.structure.list;

import java.util.Iterator;

/**
 * @program: code
 * @description:
 * @author: lsm
 * @create: 2020-04-20 18:22
 **/
public interface MyListIterator<E> extends Iterator<E>
{
    boolean hasNext();

    E next();

    boolean hasPrevious();

    E previous();

    int nextIndex();

    int previousIndex();

    void remove();

    void set(E var1);

    void add(E var1);
}
