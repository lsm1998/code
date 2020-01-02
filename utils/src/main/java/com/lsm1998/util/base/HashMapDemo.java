/*
 * 作者：刘时明
 * 时间：2019/12/28-12:36
 * 作用：
 */
package com.lsm1998.util.base;

import java.util.HashMap;

public class HashMapDemo
{
    public static void main(String[] args)
    {
        // 默认容量16
        // 默认负载因子0.75
        HashMap<String, Object> map = new HashMap<>(5);
        map.put("1", new Object());
        map.put("2", 2);
        map.put("3", 3);
        map.put("4", 4);
        map.put("5", 5);
        System.out.println(tableSizeFor(7));
        System.out.println(tableSizeFor(9));
        System.out.println(tableSizeFor(15));
        System.out.println(tableSizeFor(16));
    }

    /**
     * 获取散列表长度，必须是2^n
     *
     * @param cap
     * @return
     */
    static final int tableSizeFor(int cap)
    {
        int n = -1 >>> Integer.numberOfLeadingZeros(cap - 1);
        return n < 0 ? 1 : (n >= 1073741824 ? 1073741824 : n + 1);
    }

    /**
     * 哈希函数
     *
     * @param key
     * @return
     */
    static final int hash(Object key)
    {
        int h;
        return key == null ? 0 : (h = key.hashCode()) ^ h >>> 16;
    }
}


