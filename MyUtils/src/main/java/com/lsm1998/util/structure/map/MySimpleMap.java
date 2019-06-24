package com.lsm1998.util.structure.map;

import com.lsm1998.util.structure.set.MySimpleSet;

/**
 * @作者：刘时明
 * @时间：2019/6/24-8:41
 * @作用：
 */
public interface MySimpleMap<K, V>
{
    int size();

    boolean isEmpty();

    boolean containsKey(K key);

    boolean containsValue(K value);

    V get(K key);

    V put(K key, V value);

    V remove(K key);

    void putAll(MySimpleMap<? extends K, ? extends V> m);

    void clear();

    MySimpleSet<K> keySet();

    // Collection<V> values();

    MySimpleSet<MySimpleMap.Entry<K, V>> entrySet();

    interface Entry<K, V>
    {
        K getKey();

        V getValue();

        V setValue(V value);

        boolean equals(Object o);

        int hashCode();
    }
}
