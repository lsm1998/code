/*
 * 作者：刘时明
 * 时间：2020/4/19-23:02
 * 作用：
 */
package com.lsm1998.echoes.aio;

import com.lsm1998.aio.AcceptHandler;
import com.lsm1998.echoes.Server;

import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AIOServer implements Server
{
    private int port;

    public AIOServer(int port)
    {
        this.port=port;
    }

    @Override
    public void start()
    {
        try
        {
            init();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void init() throws Exception
    {
        ExecutorService executor = Executors.newFixedThreadPool(20);
        AsynchronousChannelGroup channelGroup = AsynchronousChannelGroup.withThreadPool(executor);
        AsynchronousServerSocketChannel serverChannel = AsynchronousServerSocketChannel.open(channelGroup).bind(new InetSocketAddress("127.0.0.1",port));
        serverChannel.accept(null, new AIOHandler(serverChannel));
        CountDownLatch latch=new CountDownLatch(1);
        latch.await();
    }
}
