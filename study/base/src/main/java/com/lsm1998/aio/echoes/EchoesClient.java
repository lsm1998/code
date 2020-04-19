/*
 * 作者：刘时明
 * 时间：2020/4/18-21:54
 * 作用：
 */
package com.lsm1998.aio.echoes;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EchoesClient
{
    public static void main(String[] args) throws Exception
    {
        int port = 9090;
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        ExecutorService executor = Executors.newFixedThreadPool(80);
        AsynchronousChannelGroup channelGroup = AsynchronousChannelGroup.withThreadPool(executor);
        AsynchronousSocketChannel clientChannel = AsynchronousSocketChannel.open(channelGroup);
        clientChannel.connect(new InetSocketAddress("127.0.0.1", port)).get();
        clientChannel.read(buffer, null, new CompletionHandler<>() {
            @Override
            public void completed(Integer result, Object attachment)
            {
                buffer.flip();
                String content = StandardCharsets.UTF_8.decode(buffer).toString();
                //显示从服务器读取的数据
                System.out.println("收到一次："+content);
                buffer.clear();
                clientChannel.read(buffer, null, this);
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                System.out.println("读取数据失败: " + exc);
            }
        });

        for (int i = 0; i < 10; i++)
        {
            try
            {
                clientChannel.write(ByteBuffer.wrap("hello".getBytes(StandardCharsets.UTF_8))).get();
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        CountDownLatch latch=new CountDownLatch(1);
        latch.await();
    }
}
