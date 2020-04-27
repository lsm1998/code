package com.lsm1998.structure;

import com.lsm1998.structure.tree.BinarySearchTree;
import org.junit.Test;

/**
 * @program: code
 * @description:
 * @author: lsm
 * @create: 2020-04-20 18:03
 **/
public class StructureTest
{
    @Test
    public void test1()
    {
        BinarySearchTree<Integer,Object> tree=new BinarySearchTree<>();
        tree.put(1,"1");
        tree.put(5,"5");
        tree.put(3,"3");
        tree.put(2,"2");
        tree.put(9,"9");
        tree.remove(1);
        tree.forEach((k,v)-> System.out.println("key="+k));
        System.out.println(tree.size());
        System.out.println("---------------");
        tree.clear();
        System.out.println(tree.size());
        tree.put(1,"1");
        tree.forEach((k,v)-> System.out.println("key="+k));
    }
}
