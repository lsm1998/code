/**
 * 作者：刘时明
 * 时间：2021/3/26
 */
package com.lsm1998.pool;

import java.util.concurrent.*;

/**
 * Java main方法结束后程序不会立即结束，而是等待所有前台线程结束
 */
public class PoolTest
{
    public static void main(String[] args) throws Exception
    {
        ExecutorService service = Executors.newFixedThreadPool(10);
        // execute和submit区别
        // execute提交Runnable
        // submit提交Callable
        // 所以submit可以有返回值
        service.submit(() ->
        {
            for (int i = 0; i < 100; i++)
            {
                System.out.println(i);
            }
        });
        Callable<String> callable = () ->
        {
            Thread.sleep(100);
            return "Hello";
        };
        FutureTask<String> future = new FutureTask<>(callable);
        service.submit(future);
        System.out.println("-----" + future.get() + "-----");
        // 对于线程池而言，创建了大量空闲线程
        // 如果不使用shutdown等方法声明结束，程序会一直运行
        service.shutdown();
    }
}
