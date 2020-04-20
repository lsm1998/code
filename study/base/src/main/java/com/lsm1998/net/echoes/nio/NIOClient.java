/*
 * 作者：刘时明
 * 时间：2020/4/19-10:18
 * 作用：
 */
package com.lsm1998.net.echoes.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class NIOClient
{
    private int port;
    private Selector selector;
    private SocketChannel channel;
    private ByteBuffer buffer = ByteBuffer.allocate(1024);

    public NIOClient(int port)
    {
        this.port=port;
    }

    public void connect()
    {
        try
        {
            selector = Selector.open();
            InetSocketAddress isa = new InetSocketAddress("127.0.0.1", port);
            channel = SocketChannel.open(isa);
            channel.configureBlocking(false);
            channel.register(selector, SelectionKey.OP_READ);
            listener();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void send(String msg)
    {
        try
        {
            channel.write(ByteBuffer.wrap(msg.getBytes()));
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void close()
    {
        try
        {
            selector.close();
            channel.close();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void listener()
    {
        new Thread(()->
        {
            try
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
                            System.out.println(result.toString());
                        }
                    }
                }
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }).start();
    }
}
