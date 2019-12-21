/*
 * 作者：刘时明
 * 时间：2019/12/21-0:59
 * 作用：
 */
package demo.net.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;

/**
 * Selector可以接受多个通道的注册，并监听，返回事件发生的通道
 * 事件类型：
 *  READ（读）、WRITE（写）、CONNECT（连接）、ACCEPT（连接）
 */
public class NIOServer
{
    public static void main(String[] args) throws Exception
    {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        Selector selector = Selector.open();
        serverSocketChannel.bind(new InetSocketAddress(8000));
        serverSocketChannel.configureBlocking(false);
        // 监听连接事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true)
        {
            if (selector.select(10000) == 0)
            {
                System.out.println("服务器等待10秒");
            } else
            {
                // 获取到发生事件的SelectionKey
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                for (SelectionKey key : selectionKeys)
                {
                    if (key.isAcceptable())
                    {
                        System.out.println("一个客户端连接成功");
                        // 新的客户端连接过来，注册到选择器上，并关联buffer
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        // 设置非阻塞
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                    }
                    if (key.isReadable())
                    {
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        ByteBuffer buffer = (ByteBuffer) key.attachment();
                        socketChannel.read(buffer);
                        System.out.println("客户端发送数据="+new String(buffer.array()));
                    }
                    // 删除SelectionKey
                    selector.selectedKeys().remove(key);
                }
            }
        }
    }
}
