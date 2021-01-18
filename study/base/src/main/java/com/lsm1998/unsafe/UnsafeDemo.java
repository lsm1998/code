package com.lsm1998.unsafe;

import org.junit.Test;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class UnsafeDemo
{
    private int intVal;

    private static final UnsafeDemo demo = new UnsafeDemo();

    private static final Unsafe unsafe = getUnsafe();

    @Test
    public void unsafeDemo()
    {
        putAndGetField();

        casField();

        memory();

        parkThread();
    }

    /**
     * Unsafe读写字段
     */
    public void putAndGetField()
    {
        long offset = unsafe.objectFieldOffset(getField(demo.getClass(), "intVal"));
        System.out.println("offset=" + offset);
        unsafe.putInt(demo, offset, 100);
        System.out.println(unsafe.getInt(demo, offset));
        System.out.println(demo.intVal);
        // 保证可见性、有序性的写入
        unsafe.putIntVolatile(demo, offset, 101);
        // 保证有序性的写入
        unsafe.putOrderedInt(demo, offset, 102);
    }

    /**
     * CAS操作
     */
    public void casField()
    {
        System.out.println(demo.intVal);
        long offset = unsafe.objectFieldOffset(getField(demo.getClass(), "intVal"));
        // 试图将offset位置的变量从0设置为100
        if (unsafe.compareAndSwapInt(demo, offset, 102, 100))
        {
            System.out.println("操作成功");
        } else
        {
            System.out.println("操作失败");
        }
        System.out.println(demo.intVal);
    }

    /**
     * 内存操作
     */
    public void memory()
    {
        // 申请4字节的内存
        long memoryAddress = unsafe.allocateMemory(4L);
        System.out.println(memoryAddress);

        // 扩展到8字节
        unsafe.reallocateMemory(memoryAddress, 8L);

        // 设置内存
        unsafe.setMemory(memoryAddress, 0L, (byte) 100);

        // copyMemory

        // 释放内存
        unsafe.freeMemory(memoryAddress);
    }

    /**
     * 线程挂起/唤醒
     */
    public void parkThread()
    {
        Thread t = new Thread(() ->
        {
            System.out.println("准备挂起");
            // 挂起线程
            unsafe.park(false, 0);
            System.out.println("被唤醒了");
        });

        t.start();


        new Thread(() ->
        {
            System.out.println("准备唤醒");
            unsafe.unpark(t);
            System.out.println("唤醒完毕");
        }).start();
    }

    private static Unsafe getUnsafe()
    {
        try
        {
            Field unsafeField = Unsafe.class.getDeclaredFields()[0];
            unsafeField.setAccessible(true);
            return (Unsafe) unsafeField.get(null);
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static Field getField(Class<?> clazz, String fieldName)
    {
        try
        {
            return clazz.getDeclaredField(fieldName);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
