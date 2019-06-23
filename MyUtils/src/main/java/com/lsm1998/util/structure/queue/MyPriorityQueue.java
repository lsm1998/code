//package com.lsm1998.util.structure.queue;
//
//import java.util.Comparator;
//
///**
// * @作者：刘时明
// * @时间：2019/6/23-14:11
// * @作用：自定义优先队列
// */
//public class MyPriorityQueue<E extends Comparable<E>> implements MyQueue<E>
//{
//    private MyMaxArrayHeap<E> heap;
//    private final Comparator<E> comparator;
//
//    public MyPriorityQueue()
//    {
//        this((o1, o2) -> o1.hashCode()-o2.hashCode());
//    }
//
//    public MyPriorityQueue(Comparator<E> comparator)
//    {
//        heap = new MyMaxArrayHeap();
//        this.comparator = comparator;
//    }
//
//    @Override
//    public int size()
//    {
//        return heap.size();
//    }
//
//    @Override
//    public boolean isEmpty()
//    {
//        return heap.size() == 0;
//    }
//
//    @Override
//    public void add(E ele)
//    {
//        heap.add(ele);
//    }
//
//    @Override
//    public E remove()
//    {
//        return heap.extractMax();
//    }
//
//    @Override
//    public E peek()
//    {
//        return heap.getMax();
//    }
//
//    @Override
//    public String toString()
//    {
//        return heap.toString();
//    }
//}
