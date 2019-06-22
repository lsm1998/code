package com.lsm1998.util.structure;


/**
 * @作者：刘时明
 * @时间：2019/6/21-23:59
 * @作用：
 */
public class Test
{
    public static void main(String[] args)
    {
        MySimpleDLinedList<Integer> list = new MySimpleDLinedList();
        list.add(3);
        list.add(5);

        for (Integer i:list)
        {
            System.out.println(i);
        }

        System.out.println(list.indexOf(3));
    }
}
