/*
 * 作者：刘时明
 * 时间：2020/4/27-23:46
 * 作用：
 */
package com.lsm1998.structure.tree;

import java.util.function.BiConsumer;

public interface Tree<K extends Comparable<K>, V>
{
    int size();

    boolean put(K key, V value);

    boolean find(K key);

    V get(K key);

    boolean isEmpty(K key);

    void forEach(BiConsumer<K, V> consumer);

    boolean remove(K key);

    void clear();

    class Node<K, V>
    {
        K key;
        V value;
        Node<K, V> left;
        Node<K, V> right;
    }
}
