/*
 * 作者：刘时明
 * 时间：2020/4/21-22:59
 * 作用：
 */
package com.lsm1998;

public class BTree<K extends Comparable<K>, V>
{
    private static final int M=4;
    private Node<K, V> root;
    private int height;
    private int size;

    public BTree()
    {
        root=new Node<>(0);
    }
    
    public int size()
    {
        return size;
    }

    public int height()
    {
        return height;
    }

    public V get(K key)
    {
        return search(root,key,height);
    }

    private V search(Node<K,V> root, K key, int height)
    {
        Entry<K,V>[] children=root.children;
        if(height==0)
        {
            for (int i = 0; i < root.m; i++)
            {
                if(eq(key,children[i].key))
                {
                    return children[i].value;
                }
            }
        }else
        {
            for (int i = 0; i < root.m; i++)
            {
                if(i+1==root.m||less(key,children[i+1].key))
                {
                    return search(children[i].next,key,height-1);
                }
            }
        }
        return null;
    }

    public void put(K key,V value)
    {
        
    }

    private boolean eq(K key1,K key2)
    {
        return key1.compareTo(key2)==0;
    }

    private boolean less(K key1,K key2)
    {
        return key1.compareTo(key2)<0;
    }

    static class Node<K, V>
    {
        private int m;
        private Entry<K,V>[] children=new Entry[M];

        private Node(int k)
        {
            this.m=k;
        }
    }

    private static class Entry<K,V>
    {
        private K key;
        private V value;
        private Node<K,V> next;

        public Entry(K key, V value, Node<K, V> next)
        {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }
}
