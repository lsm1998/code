package demo.net.echoes_nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;

/**
 * @作者：刘时明
 * @时间：2019/6/8-14:24
 * @说明：基于NIO实现的回声服务器
 */
public class NServer
{
    private Selector selector;
    private Charset charset = Charset.forName("utf-8");

    public static void main(String[] args) throws Exception
    {
        new NServer().init();
    }

    public void init() throws Exception
    {
        selector = Selector.open();
        InetSocketAddress isa = new InetSocketAddress("127.0.0.1", 8888);
        ServerSocketChannel server = ServerSocketChannel.open();
        server.bind(isa);
        // 设置非阻塞
        server.configureBlocking(false);
        server.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("服务器启动。。。");
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
                        while (channel.read(buffer) >0)
                        {
                            buffer.flip();
                            sb.append(charset.decode(buffer));
                        }
                        System.out.println("读取到的数据：" + sb.toString());
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
                        Channel temp= key.channel();
                        if(temp instanceof SocketChannel)
                        {
                            SocketChannel dest=(SocketChannel)temp;
                            dest.write(charset.encode("你好,"+sb.toString()));
                        }
                    }
                }
            }
        }
    }
}
