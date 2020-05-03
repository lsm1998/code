/*
 * 作者：刘时明
 * 时间：2020/5/2-10:46
 * 作用：
 */
package com.lsm1998.concurrent.aqs;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreExample
{
    private static int threadNum = 100;

    public static void study() throws Exception
    {
        CountDownLatch latch=new CountDownLatch(threadNum);
        ExecutorService executor = Executors.newCachedThreadPool();
        Semaphore semaphore = new Semaphore(10);
        for (int i = 0; i < threadNum; i++)
        {
            executor.execute(() ->
            {
                try
                {
                    // 获取一个许可
                    semaphore.acquire();
                    System.out.println("一个线程开始");
                    Thread.sleep(200);
                    System.out.println("执行完毕");
                    // 释放一个许可
                    semaphore.release();
                } catch (Exception e)
                {
                    e.printStackTrace();
                }finally
                {
                    latch.countDown();
                }
            });
        }
        latch.await();
        executor.shutdown();
        System.out.println("结束");
    }
}
