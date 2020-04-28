/*
 * 作者：刘时明
 * 时间：2020/4/27-23:49
 * 作用：
 */
package com.lsm1998.structure.tree;

public abstract class AbstractTree<K extends Comparable<K>, V> implements Tree<K, V>
{
    @Override
    public boolean isEmpty(K key)
    {
        return size() == 0;
    }

    @Override
    public void clear()
    {
        this.forEach((k, v) -> this.remove(k));
    }

    abstract protected Node<K, V> getRoot();

    protected Node<K, V> getNode(K key)
    {
        if (getRoot() == null) return null;
        Node<K, V> temp = getRoot();
        while (true)
        {
            if (temp.key.compareTo(key) > 0)
            {
                if (temp.left == null)
                {
                    break;
                } else
                {
                    temp = temp.left;
                }
            } else if (temp.key.compareTo(key) < 0)
            {
                if (temp.right == null)
                {
                    break;
                } else
                {
                    temp = temp.right;
                }
            } else
            {
                return temp;
            }
        }
        return null;
    }
}
