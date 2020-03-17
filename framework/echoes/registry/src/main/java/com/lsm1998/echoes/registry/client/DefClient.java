/*
 * 作者：刘时明
 * 时间：2020/3/8-17:51
 * 作用：
 */
package com.lsm1998.echoes.registry.client;

import com.lsm1998.echoes.common.net.EchoesClient;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class DefClient implements EchoesClient
{
    private Selector selector;
    private Charset charset = StandardCharsets.UTF_8;
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
                    channel.read(buffer);
                    String result = new String(buffer.array());
                    buffer.clear();
                    return result;
                }
            }
        }
        return null;
    }
}
