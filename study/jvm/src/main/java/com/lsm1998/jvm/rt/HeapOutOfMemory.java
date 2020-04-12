/*
 * 作者：刘时明
 * 时间：2020/4/12-10:57
 * 作用：
 */
package com.lsm1998.jvm.rt;

import java.util.ArrayList;
import java.util.List;

public class HeapOutOfMemory
{
    // VM options:-Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
    public static void main(String[] args)
    {
        List<HeapOutOfMemory> list=new ArrayList<>();

        while (true)
        {
            list.add(new HeapOutOfMemory());
        }
        //java.lang.OutOfMemoryError: Java heap space
        //Dumping heap to java_pid10564.hprof ...
        //Heap dump file created [30240003 bytes in 0.173 secs]
    }
}
