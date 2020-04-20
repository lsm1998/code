package com.lsm1998.net.echoes.aio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;

/**
 * @program: code
 * @description:
 * @author: lsm
 * @create: 2020-04-16 17:17
 **/
public class AIOHandler implements CompletionHandler<AsynchronousSocketChannel, Object>
{
    private AsynchronousServerSocketChannel serverChannel;
    private ByteBuffer buffer = ByteBuffer.allocate(1024);

    public AIOHandler(AsynchronousServerSocketChannel serverChannel)
    {
        this.serverChannel = serverChannel;
    }

    // 实际IO操作完成时的回调方法
    @Override
    public void completed(AsynchronousSocketChannel channel, Object o)
    {
        //准备接收客户端的下一次连接
        serverChannel.accept(null, this);
        channel.read(buffer, null, new CompletionHandler<>()
        {
            @Override
            public void completed(Integer result, Object attachment)
            {
                buffer.flip();
                String content = StandardCharsets.UTF_8.decode(buffer).toString();
                channel.write(ByteBuffer.wrap(content.getBytes()));
                buffer.clear();
                //读取下一次数据
                channel.read(buffer, null, this);
            }

            @Override
            public void failed(Throwable exc, Object attachment)
            {
                System.out.println("读取数据失败:" + exc.getMessage());
            }
        });
    }

    // 操作失败的回调
    @Override
    public void failed(Throwable throwable, Object o)
    {
        System.out.println("连接失败:" + throwable.getMessage());
    }
}
