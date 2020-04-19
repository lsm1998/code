package com.lsm1998.aio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @program: code
 * @description:
 * @author: lsm
 * @create: 2020-04-16 17:19
 **/
public class MsgHandler implements CompletionHandler<Integer, Object> {
    private ByteBuffer buffer;
    private AsynchronousSocketChannel channel;
    private List<AsynchronousSocketChannel> channelList;

    public MsgHandler(ByteBuffer buffer, AsynchronousSocketChannel channel, List<AsynchronousSocketChannel> channelList)
    {
        System.out.println("new");
        this.buffer = buffer;
        this.channel=channel;
        this.channelList=channelList;
    }

    @Override
    public void completed(Integer o1, Object o2) {
        System.out.println("收到一条客户消息");
        buffer.flip();
        String content = StandardCharsets.UTF_8.decode(buffer).toString();
        System.out.println("内容="+content);
        //遍历每个Channel, 将收到的信息写入各Channel中
        channelList.forEach(e ->
        {
            try
            {
                e.write(ByteBuffer.wrap(content.getBytes("UTF-8"))).get();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        buffer.clear();
        //读取下一次数据
        channel.read(buffer, null, this);
    }

    @Override
    public void failed(Throwable throwable, Object o) {
        System.out.println("读取数据失败:" + throwable.getMessage());
        //从该Channel中读取数据失败，将该Channel删除
        this.channelList.remove(channel);
    }
}
