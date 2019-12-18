/*
 * 作者：刘时明
 * 时间：2019/12/17-22:55
 * 作用：最基础的NIO服务端
 */
package com.lsm1998.call.net;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashSet;
import java.util.Set;

public class NIOServerTestV1
{
    private static ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
    private static Set<SocketChannel> channelList = new HashSet<>();

    public static void main(String[] args) throws Exception
    {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 绑定地址端口
        SocketAddress address = new InetSocketAddress("127.0.0.1", 8080);
        serverSocketChannel.bind(address);
        // 非阻塞
        serverSocketChannel.configureBlocking(false);

        while (true)
        {
            // 处理连接进来的客户端
            handlerChannel();

            // 新增连接
            addChannel(serverSocketChannel.accept());
        }
    }

    private static void handlerChannel() throws Exception
    {
        for (SocketChannel channel : channelList)
        {
            int read = channel.read(byteBuffer);
            if (read > 0)
            {
                printData(byteBuffer, read);
            }
        }
    }

    private static void printData(ByteBuffer buffer, int size)
    {
        byteBuffer.flip();
        byte[] b = new byte[size];
        buffer.get(b);
        String data = new String(b);
        System.out.println("data  ==>> " + data);
        byteBuffer.flip();
    }

    private static void addChannel(SocketChannel channel) throws Exception
    {
        if (channel != null)
        {
            channel.configureBlocking(false);
            channelList.add(channel);
            System.out.println("一个客户端连接完成,size=" + channelList.size());
        }
    }
}
