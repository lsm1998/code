package com.lsm1998.util.structure;

/**
 * @作者：刘时明
 * @时间：2019/6/21-23:31
 * @作用：自定义简单List顶层接口
 */
public interface MySimpleList<E> extends Iterable<E>
{
    /**
     * 指定位置插入
     *
     * @param index
     * @param ele
     */
    void add(int index, E ele);

    /**
     * 尾部插入
     *
     * @param ele
     */
    void add(E ele);

    /**
     * 是否为空List
     *
     * @return
     */
    boolean isEmpty();

    /**
     * 返回List大小
     *
     * @return
     */
    int size();

    /**
     * 查找元素
     *
     * @param o
     * @return
     */
    boolean contains(Object o);

    /**
     * 删除指定元素
     *
     * @param o
     * @return
     */
    boolean remove(Object o);

    /**
     * 删除指定位置元素
     *
     * @param index
     * @return
     */
    boolean remove(int index);

    /**
     * 清空List
     */
    void clear();

    /**
     * 获取指定位置元素
     * @param index
     * @return
     */
    E get(int index);

    /**
     * 修改指定位置元素
     * @param index
     * @param element
     * @return
     */
    E set(int index, E element);

    /**
     * 查找元素
     * @param o
     * @return
     */
    int indexOf(Object o);
}
