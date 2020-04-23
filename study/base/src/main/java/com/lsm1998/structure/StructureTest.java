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
        tree.add(1,"1");
        tree.add(5,"5");
        tree.add(3,"3");
        tree.add(2,"2");
        tree.add(9,"9");
        tree.forEach((k,v)-> System.out.println("key="+k));

        System.out.println(tree.find(3));
        System.out.println(tree.get(3));
    }
}
