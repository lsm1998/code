/*
 * 作者：刘时明
 * 时间：2020/4/18-21:53
 * 作用：
 */
package com.lsm1998.aio.echoes;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EchoesServer
{
    public static void main(String[] args) throws Exception
    {
        //创建一个线程池
        ExecutorService executor = Executors.newFixedThreadPool(20);
        //以指定线程池来创建一个AsynchronousChannelGroup
        AsynchronousChannelGroup channelGroup = AsynchronousChannelGroup.withThreadPool(executor);
        //以指定线程池来创建一个AsynchronousServerSocketChannel
        int port = 9090;
        AsynchronousServerSocketChannel serverChannel = AsynchronousServerSocketChannel.open(channelGroup).bind(new InetSocketAddress(port));
        //使用CompletionHandler接收来自客户端的连接请求(回调)
        serverChannel.accept(null, new CompletionHandler<>()
        {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            @Override
            public void completed(AsynchronousSocketChannel channel, Object attachment)
            {
                System.out.println("一个用户加入");
                serverChannel.accept(null,this);
                channel.read(buffer,null, new CompletionHandler<>()
                {
                    @Override
                    public void completed(Integer result, Object attachment)
                    {
                        buffer.flip();
                        String content = StandardCharsets.UTF_8.decode(buffer).toString();
                        try
                        {
                            System.out.println("content="+content);
                            channel.write(ByteBuffer.wrap(content.getBytes(StandardCharsets.UTF_8))).get();
                        }catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                        buffer.clear();
                        //读取下一次数据
                        channel.read(buffer, null, this);
                    }

                    @Override
                    public void failed(Throwable exc, Object attachment)
                    {

                    }
                });
            }

            @Override
            public void failed(Throwable exc, Object attachment)
            {
                System.out.println("客户端退出");
            }
        });
        CountDownLatch latch=new CountDownLatch(1);
        latch.await();
    }
}
