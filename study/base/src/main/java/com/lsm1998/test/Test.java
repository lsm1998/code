/*
 * 作者：刘时明
 * 时间：2020/4/29-23:53
 * 作用：
 */
package com.lsm1998.test;

import com.lsm1998.util.concurrent.MyReentrantLock;

import java.util.concurrent.TimeUnit;

public class Test
{
    @org.junit.Test
    public void test1() throws Exception
    {
        MyReentrantLock lock = new MyReentrantLock();

        newThreadLock(lock,0);
        Thread.sleep(200);
        lock.tryLock(1, TimeUnit.HOURS);
    }

    private void newThreadLock(final MyReentrantLock lock, long waitTime)
    {
        new Thread(() ->
        {
            try
            {
                Thread.sleep(waitTime);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            System.out.println("开始lock");
            lock.lock();
            System.out.println("lock ok!");
            // lock.unlock();
        }).start();
    }
}
