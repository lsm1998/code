package com.lsm1998.list;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: code
 * @description:
 * @author: lsm
 * @create: 2020-04-10 11:17
 **/
public class RemoveList
{
    private List<Integer> list= new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));

    /**
     * ok
     */
    @Test
    public void test1()
    {
        while (list.size()>0)
        {
            list.remove(0);
        }
        System.out.println(list);
    }

    /**
     * ok
     */
    @Test
    public void test2()
    {
        int size=list.size();
        for (int i = 0; i < size; i++)
        {
            list.remove(Integer.valueOf(i+1));
        }
        System.out.println(list);
    }

    /**
     * ConcurrentModificationException
     */
    @Test
    public void test3()
    {
        for (Integer i:list)
        {
            list.remove(i);
        }
        System.out.println(list);
    }

    /**
     * ConcurrentModificationException
     */
    @Test
    public void test4()
    {
        list.forEach(e->list.remove(e));
        System.out.println(list);
    }

    /**
     * ok
     */
    @Test
    public void test5()
    {
        list=list.stream().filter(e->false).collect(Collectors.toList());
        System.out.println(list);
    }

    /**
     * ok
     */
    @Test
    public void test6()
    {
        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext())
        {
            iterator.next();
            iterator.remove();
        }
        System.out.println(list);
    }
}
