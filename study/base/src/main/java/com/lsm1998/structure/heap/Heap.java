package com.lsm1998.structure.heap;

import java.util.function.Consumer;

/**
 * @program: code
 * @description:
 * @author: lsm
 * @create: 2020-04-28 12:46
 **/
public interface Heap<E extends Comparable<? super E>>
{
    void insert(E element);

    E poll();

    E get();

    E get(int index);

    int size();

    boolean isEmpty();

    void forEach(Consumer<E> consumer);
}
