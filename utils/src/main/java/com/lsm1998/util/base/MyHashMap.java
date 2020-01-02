/*
 * 作者：刘时明
 * 时间：2019/12/27-21:06
 * 作用：
 */
package com.lsm1998.util.base;

public class MyHashMap<K, V>
{
    static class MyNode<K, V> implements MyMap.MyEntry
    {
        final int hash;
        final K key;
        V value;
        MyNode<K, V> next;

        MyNode(int hash, K key, V value, MyNode<K, V> next)
        {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        @Override
        public Object getKey()
        {
            return null;
        }

        @Override
        public Object getValue()
        {
            return null;
        }

        @Override
        public Object setValue(Object var1)
        {
            return null;
        }

        @Override
        public boolean equals(Object var1)
        {
            return false;
        }

        @Override
        public int hashCode()
        {
            return 0;
        }
    }

    static final int tableSizeFor(int cap)
    {
        int n = -1 >>> Integer.numberOfLeadingZeros(cap - 1);
        return n < 0 ? 1 : (n >= 1073741824 ? 1073741824 : n + 1);
    }
}
