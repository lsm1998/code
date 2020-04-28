package com.lsm1998.jvm.metadata;

import org.junit.Test;

import java.lang.management.*;
import java.util.Arrays;
import java.util.List;

/**
 * @program: code
 * @description:
 * @author: lsm
 * @create: 2020-04-28 17:37
 **/
public class ManagementFactoryTest
{
    @Test
    public void test1()
    {
        // 运行时信息
        RuntimeMXBean mxb = ManagementFactory.getRuntimeMXBean();
        System.out.println(mxb.getLibraryPath());
        System.out.println(mxb.getVmName());
        System.out.println(mxb.getPid());
        System.out.println(mxb.getName());
        System.out.println(mxb.getSpecName());
        System.out.println(mxb.getUptime());
    }

    @Test
    public void test2()
    {
        // 操作系统信息
        OperatingSystemMXBean osb = ManagementFactory.getOperatingSystemMXBean();
        System.out.println(osb.getName());
        System.out.println(osb.getArch());
        System.out.println(osb.getAvailableProcessors());
        System.out.println(osb.getVersion());
        System.out.println(osb.getSystemLoadAverage());
    }

    @Test
    public void test3()
    {
        // JVM内存信息
        MemoryMXBean mxb = ManagementFactory.getMemoryMXBean();
        //Heap
        System.out.println("最大内存:" + mxb.getHeapMemoryUsage().getMax() / 1024 / 1024 + "MB");    //Max:1776MB
        System.out.println("初始化内存:" + mxb.getHeapMemoryUsage().getInit() / 1024 / 1024 + "MB");  //Init:126MB
        System.out.println("Committed:" + mxb.getHeapMemoryUsage().getCommitted() / 1024 / 1024 + "MB");   //Committed:121MB
        System.out.println("Used:" + mxb.getHeapMemoryUsage().getUsed() / 1024 / 1024 + "MB");  //Used:7MB
        System.out.println(mxb.getHeapMemoryUsage().toString());    //init = 132120576(129024K) used = 8076528(7887K) committed = 126877696(123904K) max = 1862270976(1818624K)

        //Non heap
        System.out.println("Max:" + mxb.getNonHeapMemoryUsage().getMax() / 1024 / 1024 + "MB");    //Max:0MB
        System.out.println("Init:" + mxb.getNonHeapMemoryUsage().getInit() / 1024 / 1024 + "MB");  //Init:2MB
        System.out.println("Committed:" + mxb.getNonHeapMemoryUsage().getCommitted() / 1024 / 1024 + "MB");   //Committed:8MB
        System.out.println("Used:" + mxb.getNonHeapMemoryUsage().getUsed() / 1024 / 1024 + "MB");  //Used:7MB
        System.out.println(mxb.getNonHeapMemoryUsage().toString());    //init = 2555904(2496K) used = 7802056(7619K) committed = 9109504(8896K) max = -1(-1K)
    }

    @Test
    public void test4()
    {
        // 编译器信息
        CompilationMXBean gm=ManagementFactory.getCompilationMXBean();
        System.out.println("getName "+gm.getName());
        System.out.println("getTotalCompilationTime "+gm.getTotalCompilationTime());
    }

    @Test
    public void test5()
    {
        // 内存池信息
        List<MemoryPoolMXBean> mpmList=ManagementFactory.getMemoryPoolMXBeans();
        for(MemoryPoolMXBean mpm:mpmList)
        {
            System.out.println("getUsage "+mpm.getUsage());
            System.out.println("getMemoryManagerNames "+ Arrays.toString(mpm.getMemoryManagerNames()));
        }
    }

    @Test
    public void test6()
    {
        // GC信息
        List<GarbageCollectorMXBean> gcmList=ManagementFactory.getGarbageCollectorMXBeans();
        for(GarbageCollectorMXBean gcm:gcmList)
        {
            System.out.println("getName "+gcm.getName());
            System.out.println(gcm.getCollectionCount());
            System.out.println("getMemoryPoolNames "+ Arrays.toString(gcm.getMemoryPoolNames()));
        }
    }
}
