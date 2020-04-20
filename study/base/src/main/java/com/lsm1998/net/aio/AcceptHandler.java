package com.lsm1998.net.aio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: code
 * @description:
 * @author: lsm
 * @create: 2020-04-16 17:17
 **/
public class AcceptHandler implements CompletionHandler<AsynchronousSocketChannel, Object> {
    private AsynchronousServerSocketChannel serverChannel;
    private ByteBuffer buffer = ByteBuffer.allocate(1024);
    private List<AsynchronousSocketChannel> channelList = new ArrayList<>();

    public AcceptHandler(AsynchronousServerSocketChannel serverChannel) {
        this.serverChannel = serverChannel;
    }

    // 实际IO操作完成时的回调方法
    @Override
    public void completed(AsynchronousSocketChannel channel, Object o) {
        System.out.println("一个用户加入");
        //记录新连接进来的Channel
        this.channelList.add(channel);
        //准备接收客户端的下一次连接
        serverChannel.accept(null, this);
        channel.read(buffer, null, new MsgHandler(buffer,channel,channelList));
    }

    // 操作失败的回调
    @Override
    public void failed(Throwable throwable, Object o) {
        System.out.println("连接失败:" + throwable.getMessage());
    }
}
