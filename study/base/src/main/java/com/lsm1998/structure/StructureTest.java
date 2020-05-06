package com.lsm1998.structure;

import com.lsm1998.structure.graph.CityGraph;
import com.lsm1998.structure.heap.BinaryHeap;
import com.lsm1998.structure.tree.BinarySearchTree;
import org.junit.Test;

import java.util.List;

/**
 * @program: code
 * @description:
 * @author: lsm
 * @create: 2020-04-20 18:03
 **/
public class StructureTest
{
    @Test
    public void testTree()
    {
        BinarySearchTree<Integer, Object> tree = new BinarySearchTree<>();
        tree.put(1, "1");
        tree.put(5, "5");
        tree.put(3, "3");
        tree.put(2, "2");
        tree.put(9, "9");
        tree.remove(1);
        tree.forEach((k, v) -> System.out.println("key=" + k));
        System.out.println(tree.size());
        System.out.println("---------------");
        tree.clear();
        System.out.println(tree.size());
        tree.put(1, "1");
        tree.forEach((k, v) -> System.out.println("key=" + k));
    }

    @Test
    public void testHeap()
    {
        BinaryHeap<Integer> heap = new BinaryHeap<>();
        heap.insert(1);
        heap.insert(5);
        heap.insert(1);
        heap.insert(10);
        heap.insert(-100);
        heap.insert(100);
        heap.forEach(System.out::println);
        // System.out.println(heap.size());
    }

    @Test
    public void testCityGraph()
    {
        CityGraph graph = new CityGraph(4);
        graph.insertCity("北京");
        graph.insertCity("上海");
        graph.insertCity("南京");
        graph.insertCity("天津");

        graph.insertEdge(0, 1, 646);
        graph.insertEdge(0, 2, 523);
        graph.insertEdge(0, 3, 20);

        graph.insertEdge(1, 2, 87);
        graph.insertEdge(1, 3, 630);

        graph.insertEdge(2, 3, 598);

        graph.bfs();
    }
}
