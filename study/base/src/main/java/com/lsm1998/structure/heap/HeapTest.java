/**
 * 作者：刘时明
 * 时间：2021/3/26
 */
package com.lsm1998.structure.heap;

import org.junit.Test;

public class HeapTest
{
    @Test
    public void heapTest()
    {
        Heap<Integer> heap = new BinaryHeap<>();
        for (int i = 0; i < 100; i++)
        {
            heap.insert(i);
        }
        heap.forEach(System.out::println);
    }
}
