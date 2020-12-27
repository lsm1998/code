package com.lsm1998.util.thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @作者：刘时明
 * @时间：2019/6/18-11:08
 * @作用：
 */
public class Test
{
    public static void main(String[] args) throws Exception
    {
        MyCyclicBarrier cyclicBarrier = new MyCyclicBarrier(10, new Done());

        ReentrantLock lock = new ReentrantLock();


        lock.lock();
        for (int i = 0; i < 100; i++)
        {
            new Thread(() ->
            {
                try
                {
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e)
                {
                    e.printStackTrace();
                }
                System.out.println("线程结束");
            }).start();
        }
    }

    static class Done extends Thread
    {
        @Override
        public void run()
        {
            System.out.println("ok");
        }
    }
}
