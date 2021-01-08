package com.lsm1998.net.aio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: code
 * @description:
 * @author: lsm
 * @create: 2020-04-16 17:19
 **/
public class MsgHandler implements CompletionHandler<Integer, Object>
{
    private final ByteBuffer buffer;
    private final AsynchronousSocketChannel channel;
    private static final List<AsynchronousSocketChannel> channelList;

    static
    {
        channelList = new ArrayList<>();
    }

    public MsgHandler(ByteBuffer buffer, AsynchronousSocketChannel channel, List<AsynchronousSocketChannel> channelList)
    {
        this.buffer = buffer;
        this.channel = channel;
    }

    @Override
    public void completed(Integer result, Object o2)
    {
        if (result == -1)
        {
            this.closeChannel();
            return;
        }
        System.out.println("收到一条客户消息");
        buffer.flip();
        String content = StandardCharsets.UTF_8.decode(buffer).toString();
        System.out.println("内容=" + content);
        //遍历每个Channel, 将收到的信息写入各Channel中
        channelList.forEach(e ->
        {
            try
            {
                e.write(ByteBuffer.wrap(content.getBytes(StandardCharsets.UTF_8))).get();
            } catch (Exception ex)
            {
                ex.printStackTrace();
            }
        });
        buffer.clear();
        //读取下一次数据
        channel.read(buffer, null, this);
    }

    @Override
    public void failed(Throwable throwable, Object o)
    {
        System.out.println("读取数据失败:" + throwable.getMessage());
        // 从该Channel中读取数据失败，将该Channel删除
        this.closeChannel();
    }

    private void closeChannel()
    {
        try
        {
            System.out.println("一个连接退出");
            this.channel.close();
            channelList.remove(channel);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
