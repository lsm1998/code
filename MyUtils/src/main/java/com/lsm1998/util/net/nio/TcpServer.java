package com.lsm1998.util.net.nio;

import com.lsm1998.util.bit.BitObjectUtil;
import com.lsm1998.util.net.bean.MsgData;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Optional;

/**
 * @作者：刘时明
 * @时间：2019/6/17-8:55
 * @作用：
 */
public class TcpServer
{
    private static final Charset charset = Charset.forName("utf-8");
    private static final int BUFFER_SIZE = 1024;
    private Selector selector;
    private ServerSocketChannel server;

    public TcpServer(String host, int port) throws IOException
    {
        selector = Selector.open();
        InetSocketAddress isa = new InetSocketAddress(host, port);
        this.server = ServerSocketChannel.open();
        server.bind(isa);
        server.configureBlocking(false);
        server.register(selector, SelectionKey.OP_ACCEPT);
    }

    public void start(NIOServerHandle handle) throws IOException
    {
        System.out.println("TCP Server by nio start...");
        while (selector.select() > 0)
        {
            for (SelectionKey key : selector.selectedKeys())
            {
                selector.selectedKeys().remove(key);
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
                    ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
                    try (SocketChannel channel = (SocketChannel) key.channel();
                         ByteArrayOutputStream baOs = new ByteArrayOutputStream())
                    {
                        while (channel.read(buffer) > 0)
                        {
                            buffer.flip();
                            baOs.write(buffer.array());
                        }
                        byte[] bytes = baOs.toByteArray();
                        Optional<MsgData> data = BitObjectUtil.bytesToObject(bytes);
                        if (data != null)
                        {
                            Channel temp = key.channel();
                            handle.handle(data.get(), (SocketChannel) temp, selector.selectedKeys());
                        }
                        // 准备下一次读取
                        key.interestOps(SelectionKey.OP_READ);
                    } catch (Exception e)
                    {
                        // 从Selector删除数据
                        key.cancel();
                        if (key.channel() != null)
                        {
                            key.channel().close();
                        }
                    }
                }
            }
        }
    }
}
