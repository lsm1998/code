package com.lsm1998.util.structure;

/**
 * @作者：刘时明
 * @时间：2019/6/21-21:09
 * @作用：二叉树顶层接口
 */
public interface MyBTree<E extends Comparable<? extends E>>
{
    /**
     * 二叉树大小
     *
     * @return
     */
    int size();

    /**
     * 插入节点
     *
     * @param ele
     * @return
     */
    boolean insert(E ele);

    /**
     * 删除节点
     *
     * @param ele
     * @return
     */
    boolean remove(E ele);

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
}
