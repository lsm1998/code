/*
 * 作者：刘时明
 * 时间：2020/3/8-16:47
 * 作用：
 */
package com.lsm1998.echoes.registry.service.impl;

import com.lsm1998.echoes.registry.config.RegistryConfig;
import com.lsm1998.echoes.registry.service.RegistryServer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Slf4j
public class NIORegistryServerImpl extends RegistryServer
{
    private Selector selector;
    private Charset charset = StandardCharsets.UTF_8;
    private RegistryHandler handler;

    public NIORegistryServerImpl(RegistryConfig config)
    {
        super(config);
        handler = new RegistryHandler(getConfig());
    }

    @Override
    public void start(int port) throws IOException
    {
        selector = Selector.open();
        this.init(port);
    }

    public void init(int port) throws IOException
    {
        InetSocketAddress isa = new InetSocketAddress("127.0.0.1", port);
        ServerSocketChannel server = ServerSocketChannel.open();
        server.bind(isa);
        // 设置非阻塞
        server.configureBlocking(false);
        server.register(selector, SelectionKey.OP_ACCEPT);
        log.info("注册中心启动完毕,port=[{}]",port);
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
                    SocketChannel channel = (SocketChannel) key.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    StringBuilder sb = new StringBuilder();
                    try
                    {
                        while (channel.read(buffer) > 0)
                        {
                            buffer.flip();
                            sb.append(charset.decode(buffer));
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
                    if (sb.length() > 0)
                    {
                        Channel temp = key.channel();
                        if (temp instanceof SocketChannel)
                        {
                            SocketChannel dest = (SocketChannel) temp;
                            handler.handlerData(sb.toString(), dest);
                        }
                    }
                }
            }
        }
    }
}
