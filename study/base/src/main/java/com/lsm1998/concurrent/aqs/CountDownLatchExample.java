/*
 * 作者：刘时明
 * 时间：2020/5/2-10:33
 * 作用：
 */
package com.lsm1998.concurrent.aqs;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CountDownLatchExample
{
    private static int threadNum = 10;

    public static void study() throws Exception
    {
        ExecutorService executor = Executors.newFixedThreadPool(threadNum);
        final CountDownLatch latch = new CountDownLatch(threadNum);
        for (int i = 0; i < threadNum; i++)
        {
            final int num = i;
            executor.execute(() ->
            {
                try
                {
                    test(num);
                } finally
                {
                    latch.countDown();
                }
            });
        }
        latch.await(30, TimeUnit.SECONDS);
        System.out.println("结束");
        executor.shutdown();
    }

    private static void test(int num)
    {
        try
        {
            Thread.sleep(200);
            System.out.println("name=" + Thread.currentThread().getName() + ",num=" + num);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
