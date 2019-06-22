package com.lsm1998.util;

/**
 * @作者：刘时明
 * @时间：2019/6/17-18:55
 * @作用：
 */
public interface MyMap<K,V>
{
    int size();

    boolean isEmpty();

    boolean containsKey(Object key);

    boolean containsValue(Object value);

    V get(Object key);

    V put(K key, V value);

    V remove(Object key);

    void putAll(MyMap<? extends K, ? extends V> m);

    void clear();

    MySet<K> keySet();

    MyCollection<V> values();
}
