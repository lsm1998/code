/*
 * 作者：刘时明
 * 时间：2020/3/8-17:51
 * 作用：
 */
package com.lsm1998.net.echoes.registry.client;

import com.lsm1998.net.echoes.common.net.EchoesClient;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class DefClient implements EchoesClient
{
    private Selector selector;
    private SocketChannel channel;
    private ByteBuffer buffer = ByteBuffer.allocate(1024);

    public DefClient(String address, int port) throws IOException
    {
        this.connect(address, port);
    }

    public void connect(String address, int port) throws IOException
    {
        selector = Selector.open();
        InetSocketAddress isa = new InetSocketAddress(address, port);
        channel = SocketChannel.open(isa);
        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_READ);
    }

    public void close() throws IOException
    {
        channel.close();
        selector.close();
    }

    public String client2Server(String msg)
    {
        try
        {
            channel.write(ByteBuffer.wrap(msg.getBytes()));
            return getResult();
        } catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public String getResult() throws IOException
    {
        while (selector.select() > 0)
        {
            for (SelectionKey key : selector.selectedKeys())
            {
                selector.selectedKeys().remove(key);
                if (key.isReadable())
                {
                    SocketChannel channel = (SocketChannel) key.channel();
                    buffer.clear();
                    int len;
                    StringBuilder result=new StringBuilder();
                    while ((len=channel.read(buffer))>0)
                    {
                        buffer.flip();
                        result.append(new String(buffer.array(),0,len));
                    }
                    buffer.clear();
                    return result.toString();
                }
            }
        }
        return null;
    }
}
