package com.lsm1998.util.concurrent;

import java.util.concurrent.locks.LockSupport;

/**
 * @作者：刘时明
 * @时间：2019/6/18-11:08
 * @作用：
 */
public class Test
{
    public static void main(String[] args) throws Exception
    {
        MyLock lock = new MyReentrantLock();

        lock.lock();

        lock.unlock();

        Thread thread1 = new Thread(() ->
        {
            System.out.println(1);
            LockSupport.park(Thread.currentThread());
            System.out.println(2);
        });
        thread1.start();

        new Thread(() ->
        {
            System.out.println("hello");
            LockSupport.unpark(thread1);
        }).start();
    }
}
