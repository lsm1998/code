/*
 * 作者：刘时明
 * 时间：2020/4/27-23:59
 * 作用：
 */
package com.lsm1998.structure.tree;

import java.util.function.BiConsumer;

public class AVLTree<K extends Comparable<K>,V> extends AbstractTree<K,V>
{
    @Override
    protected Node<K, V> getRoot()
    {
        return null;
    }

    @Override
    public int size()
    {
        return 0;
    }

    @Override
    public boolean put(K key, V value)
    {
        return false;
    }

    @Override
    public boolean find(K key)
    {
        return false;
    }

    @Override
    public V get(K key)
    {
        return null;
    }

    @Override
    public void forEach(BiConsumer<K, V> consumer)
    {

    }

    /**
     * 获取平衡因子
     * @param key
     * @return
     */
    public int getBalance(K key)
    {
        Node<K, V> node=getNode(key);
        return node==null ? -1:getBalance((AVLNode<K,V>)node);
    }

    @Override
    protected Node<K, V> getNode(K key)
    {
        return null;
    }

    private int getBalance(AVLNode<K,V> root)
    {
        if (root.left != null && root.right != null)
        {
            return ((AVLNode<K,V>)root.right).height - ((AVLNode<K,V>)root.left).height;
        }
        else
        {
            return root.left == null ? 1 : -1;
        }
    }

    @Override
    public boolean remove(K key)
    {
        return false;
    }

    private static class AVLNode<K,V> extends Tree.Node<K,V>
    {
        private AVLNode<K,V> parent;
        private int height;

        public AVLNode(AVLNode<K, V> parent, AVLNode<K, V> left, AVLNode<K, V> right, K key, V value, int height)
        {
            this.parent = parent;
            this.left = left;
            this.right = right;
            this.key = key;
            this.value = value;
            this.height = height;
        }
    }
}
