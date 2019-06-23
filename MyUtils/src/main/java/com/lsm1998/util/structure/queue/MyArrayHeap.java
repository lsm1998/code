//package com.lsm1998.util.structure.queue;
//
//import com.lsm1998.util.array.MyArrays;
//import com.lsm1998.util.structure.list.MySimpleArrayList;
//
//import java.util.Comparator;
//
///**
// * @作者：刘时明
// * @时间：2019/6/23-11:45
// * @作用：
// */
//public class MyArrayHeap<E>
//{
//    // 比较器
//    private final Comparator<E> comparator;
//    private final MySimpleArrayList<E> list;
//
//    public MyArrayHeap()
//    {
//        // 默认情况比较hashCode值
//        this(((o1, o2) -> o1.hashCode() > o2.hashCode() ? 1 : o1.hashCode() == o2.hashCode() ? 0 : -1), 0);
//    }
//
//    public MyArrayHeap(Comparator<E> comparator, int capacity)
//    {
//        this.comparator = comparator;
//        this.list = new MySimpleArrayList<>(capacity);
//    }
//
//    public MyArrayHeap(Comparator<E> comparator)
//    {
//        this(comparator, 0);
//    }
//
//    public int size()
//    {
//        return list.size();
//    }
//
//    public boolean isEmpty()
//    {
//        return list.isEmpty();
//    }
//
//    private int parent(int index)
//    {
//        if (index == 0)
//        {
//            throw new IllegalArgumentException("index=0");
//        }
//        return (index - 1) / 2;
//    }
//
//    private int leftChild(int index)
//    {
//        return index * 2 + 1;
//    }
//
//    private int rightChild(int index)
//    {
//        return index * 2 + 2;
//    }
//
//    /**
//     * 向堆中添加元素
//     *
//     * @param ele
//     */
//    public void add(E ele)
//    {
//        list.add(ele);
//        siftUp(list.size() - 1);
//    }
//
//    /**
//     * 出堆操作
//     *
//     * @return
//     */
//    public E extract()
//    {
//        E ret = getFirst();
//        if (ret != null)
//        {
//            MyArrays.swap(0, list.size() - 1, list);
//            list.remove(list.size() - 1);
//            siftDown(0);
//        }
//        return ret;
//    }
//
//    /**
//     * 获取首个元素
//     *
//     * @return
//     */
//    public E getFirst()
//    {
//        return size() > 0 ? list.get(0) : null;
//    }
//
//    /**
//     * 获取末尾元素
//     *
//     * @return
//     */
//    public E getLast()
//    {
//        return size() > 0 ? list.get(size() - 1) : null;
//    }
//
//    private void siftUp(int index)
//    {
//        // 当前节点与父节点进行比较，如果满足则交换
//        while (index > 0 && comparator.compare(list.get(index), list.get(parent(index))) < 0)
//        {
//            MyArrays.swap(index, parent(index), list);
//            index = parent(index);
//        }
//    }
//
//    private void siftDown(int index)
//    {
//        int size = size();
//        while (leftChild(index) < size)
//        {
//            int temp = leftChild(index);
//            // 是否存在右子节点且优于左子节点
//            if (rightChild(index) < size && comparator.compare(list.get(temp + 1), list.get(temp)) > 0)
//            {
//                temp = rightChild(index);
//            }
//            if (comparator.compare(list.get(index), list.get(temp)) >= 0)
//            {
//                break;
//            }
//            MyArrays.swap(index, temp, list);
//            index = temp;
//        }
//    }
//
//    @Override
//    public String toString()
//    {
//        return list.toString();
//    }
//
//    public static void main(String[] args)
//    {
//        Comparator<Integer> comparable = (o1, o2) -> o1 > o2 ? 1 : o1 == o2 ? 0 : -1;
//        MyArrayHeap<Integer> heap = new MyArrayHeap(comparable);
//        heap.add(9);
//        heap.add(10);
//        heap.add(20);
//        heap.add(77);
//        heap.add(-2);
//        heap.add(5);
//        System.out.println(heap);
//        while (heap.size() > 0)
//        {
//            System.out.println(heap.extract());
//        }
//
//        System.out.println(comparable.compare(1, 2));
//    }
//}
