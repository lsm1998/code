package com.lsm1998.aio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

/**
 * AIO客户端
 */
public class AIOClient
{
    private final AsynchronousSocketChannel client;

    public AIOClient() throws Exception
    {
        client = AsynchronousSocketChannel.open();
    }

    public void connect(String host,int port)throws Exception
    {
        client.connect(new InetSocketAddress(host,port),null,new CompletionHandler<Void,Void>()
        {
            @Override
            public void completed(Void result, Void attachment)
            {
                Scanner scanner=new Scanner(System.in);

                try
                {
                    while (true)
                    {
                        System.out.println("输入发送信息:");
                        String msg=scanner.nextLine();
                        client.write(ByteBuffer.wrap(msg.getBytes())).get();
                        System.out.println("已发送至服务器");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void failed(Throwable exc, Void attachment) {
                exc.printStackTrace();
            }
        });
        final ByteBuffer bb = ByteBuffer.allocate(1024);
        client.read(bb, null, new CompletionHandler<>()
                {
                    @Override
                    public void completed(Integer result, Object attachment)
                    {
                        System.out.println("IO操作完成" + result);
                        System.out.println("获取反馈结果" + new String(bb.array()));
                    }
                    @Override
                    public void failed(Throwable exc, Object attachment) {
                        exc.printStackTrace();
                    }
                }
        );
        CountDownLatch latch=new CountDownLatch(1);
        latch.await();
    }

    public static void main(String[] args)throws Exception
    {
        new AIOClient().connect("localhost",8000);
    }
}
