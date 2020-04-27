/*
 * 作者：刘时明
 * 时间：2020/4/27-23:49
 * 作用：
 */
package com.lsm1998.structure.tree;

public abstract class AbstractTree<K extends Comparable<K>,V> implements Tree<K,V>
{
    @Override
    public boolean isEmpty(K key)
    {
        return size()==0;
    }

    @Override
    public void clear()
    {
        this.forEach((k,v)->this.remove(k));
    }
}
