package com.lsm1998.util.structure.tree;

/**
 * @作者：刘时明
 * @时间：2019/6/21-21:09
 * @作用：二叉树顶层接口
 */
public interface MyBTree<K extends Comparable<? super K>, V>
{
    /**
     * 二叉树大小
     *
     * @return
     */
    int size();

    /**
     * 是否存在元素
     *
     * @return
     */
    boolean isEmpty();

    /**
     * 插入节点
     *
     * @param key
     * @return
     */
    boolean insert(K key, V value);

    /**
     * 删除节点
     *
     * @param key
     * @return
     */
    boolean remove(K key);

    /**
     * 先序遍历
     */
    void prevDisPlay();

    /**
     * 后序遍历
     */
    void postDisPlay();

    /**
     * 后序遍历
     */
    void middleDisPlay();

    /**
     * 查找元素
     *
     * @param key
     * @return
     */
    boolean contains(K key);
}
