/**
 * 作者：刘时明
 * 时间：2021/3/30
 */
package com.lsm1998.middle.distributeLock;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.Test;

public class LockExample
{
    @Test
    public void lockTest()
    {
        // 重试策略
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString("172.16.200.2:2181")
                .sessionTimeoutMs(5000)  // 会话超时时间，默认60000ms
                .connectionTimeoutMs(10000) // 连接超时时间，默认15000ms
                .retryPolicy(retryPolicy) // 重试策略
                .build();
        client.start();

        InterProcessMutex lock = new InterProcessMutex(client, "/lock");
        try
        {
            lock.acquire();
        } catch (Throwable cause)
        {
            cause.printStackTrace();
            throw new RuntimeException("加锁异常");
        }
        try
        {
            System.out.println("before");
            Thread.sleep(500);
            System.out.println("after");
        } catch (Throwable cause)
        {
            cause.printStackTrace();
        } finally
        {
            try
            {
                lock.release();
            } catch (Throwable cause)
            {
                throw new RuntimeException("解锁异常");
            }
        }
    }
}
