/*
 * 作者：刘时明
 * 时间：2020/4/18-22:38
 * 作用：
 */
package com.lsm1998.net.echoes;

import com.lsm1998.net.echoes.aio.AIOServer;
import com.lsm1998.net.echoes.bio.BIOClient;
import com.lsm1998.net.echoes.bio.BIOServer;
import com.lsm1998.net.echoes.nio.NIOClient;
import com.lsm1998.net.echoes.nio.NIOServer;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

public class PressureTest
{
    @Test
    public void bioServer()
    {
        BIOServer server=new BIOServer(9999);
        server.start();
    }

    @Test
    public void bioClient() throws Exception
    {
        for (int i = 0; i < 2000; i++)
        {
            new Thread(()->
            {
                BIOClient client=new BIOClient(8848);
                client.connect();
                for (int j = 0; j < 100; j++)
                {
                    try
                    {
                        client.send("hello-"+j);
                        Thread.sleep(500);
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                client.stop();
            }).start();
        }
        CountDownLatch latch=new CountDownLatch(1);
        latch.await();
    }

    @Test
    public void nioServer()
    {
        NIOServer server=new NIOServer(9999);
        server.start();
    }

    @Test
    public void aioServer()
    {
        AIOServer server=new AIOServer(8848);
        server.start();
    }

    @Test
    public void nioClient() throws Exception
    {
        for (int i = 0; i < 1200; i++)
        {
            new Thread(()->
            {
                NIOClient client=new NIOClient(8848);
                client.connect();
                for (int j = 0; j < 10; j++)
                {
                    try
                    {
                        client.send("hello-"+j);
                        Thread.sleep(500);
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                client.close();
            }).start();
        }
        CountDownLatch latch=new CountDownLatch(1);
        latch.await();
    }
}
