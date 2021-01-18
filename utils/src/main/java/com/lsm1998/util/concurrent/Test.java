package com.lsm1998.util.concurrent;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @作者：刘时明
 * @时间：2019/6/18-11:08
 * @作用：
 */
public class Test
{
    public static void main(String[] args) throws Exception
    {
        MyConcurrentHashMap<String, Object> map = new MyConcurrentHashMap<>();
        map.put("1", 1);

        map.remove("1");

        map.contains("1");

        System.out.println(map.size());
    }
}
