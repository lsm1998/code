package com.lsm1998.net.aio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 异步IO客户端
 */
public class AClient
{
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 30001;

    /**
     * 与服务器端通信的异步Channel
     */
    private AsynchronousSocketChannel clientChannel;

    private void send1()
    {
        Scanner scanner = new Scanner(System.in);
        while (true)
        {
            System.out.println("输入发送内容:");
            String text = scanner.nextLine();
            try
            {
                clientChannel.write(ByteBuffer.wrap(text.getBytes(StandardCharsets.UTF_8))).get();
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    private void send2()
    {
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
    }

    private void connect()
    {
        try
        {
            final ByteBuffer buffer = ByteBuffer.allocate(1024);
            ExecutorService executor = Executors.newFixedThreadPool(80);
            AsynchronousChannelGroup channelGroup = AsynchronousChannelGroup.withThreadPool(executor);
            clientChannel = AsynchronousSocketChannel.open(channelGroup);
            clientChannel.connect(new InetSocketAddress(HOST, PORT)).get();
            System.out.println("---与服务器连接成功---");
            clientChannel.read(buffer, null, new CompletionHandler<>()
            {
                @Override
                public void completed(Integer result, Object attachment)
                {
                    buffer.flip();
                    String content = StandardCharsets.UTF_8.decode(buffer).toString();
                    //显示从服务器读取的数据
                    System.out.println("某人说:" + content);
                    buffer.clear();
                    clientChannel.read(buffer, null, this);
                }

                @Override
                public void failed(Throwable exc, Object attachment)
                {
                    System.out.println("读取数据失败: " + exc);
                }
            });
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        AClient client = new AClient();
        client.connect();
        client.send1();
    }
}
