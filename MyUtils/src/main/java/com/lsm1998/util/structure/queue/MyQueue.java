package com.lsm1998.util.structure.queue;

/**
 * @作者：刘时明
 * @时间：2019/6/23-14:06
 * @作用：自定义队列顶层接口
 */
public interface MyQueue<E>
{
    /**
     * 队列大小
     *
     * @return
     */
    int size();

    /**
     * 队列是否为空
     *
     * @return
     */
    boolean isEmpty();

    /**
     * 入队
     *
     * @param ele
     * @return
     */
    void add(E ele);

    /**
     * 出队
     *
     * @return
     */
    E remove();

    /**
     * 查看队头元素
     *
     * @return
     */
    E peek();
}
