/*
 * 作者：刘时明
 * 时间：2020/4/19-10:08
 * 作用：
 */
package com.lsm1998.net.echoes.nio;

import com.lsm1998.net.echoes.Server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

public class NIOServer implements Server
{
    private int port;
    private Selector selector;
    private Charset charset = StandardCharsets.UTF_8;

    public NIOServer(int port)
    {
        this.port = port;
    }

    @Override
    public void start()
    {
        try
        {
            init();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void init() throws Exception
    {
        selector=Selector.open();
        ServerSocketChannel server = ServerSocketChannel.open();
        server.bind(new InetSocketAddress("127.0.0.1",port));
        server.configureBlocking(false);
        server.register(selector, SelectionKey.OP_ACCEPT);
        while (true)
        {
            int readyChannels = selector.select();
            if(readyChannels == 0) continue;
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
            while(keyIterator.hasNext())
            {
                SelectionKey key = keyIterator.next();
                keyIterator.remove();
                dealWithSelectionKey(server,key);
            }
        }
    }

    private void dealWithSelectionKey(ServerSocketChannel server, SelectionKey key) throws Exception
    {
        // 是否包含客户端请求
        if (key.isAcceptable())
        {
            SocketChannel channel = server.accept();
            channel.configureBlocking(false);
            channel.register(selector, SelectionKey.OP_READ);
            key.interestOps(SelectionKey.OP_ACCEPT);
        }
        // 是否存在读取的数据
        if (key.isReadable())
        {
            SocketChannel channel = (SocketChannel) key.channel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            StringBuilder content = new StringBuilder();
            try
            {
                while(channel.read(buffer) > 0)
                {
                    buffer.flip();
                    content.append(charset.decode(buffer));
                }
                //将此对应的channel设置为准备下一次接受数据
                key.interestOps(SelectionKey.OP_READ);
            }
            catch (IOException io)
            {
                key.cancel();
                if(key.channel() != null)
                {
                    key.channel().close();
                }
            }
            if (content.length() > 0)
            {
                Channel temp = key.channel();
                if (temp instanceof SocketChannel)
                {
                    SocketChannel dest = (SocketChannel) temp;
                    NIOHandler.handlerData(content.toString(), dest);
                }
            }
        }
    }
}
