/*
 * 作者：刘时明
 * 时间：2019/12/21-10:53
 * 作用：
 */
package demo.base;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Demo01
{
    public static void main(String[] args)
    {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        for (Integer i:list)
        {
            list.remove((int)i);
        }
        Set<Integer> set=new HashSet<>();
        set.add(1);
        set.add(2);
        set.add(3);
        for (Integer i:set)
        {
            set.remove(i);
        }
    }
}
