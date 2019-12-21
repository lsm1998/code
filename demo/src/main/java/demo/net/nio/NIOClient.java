/*
 * 作者：刘时明
 * 时间：2019/12/21-1:22
 * 作用：
 */
package demo.net.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NIOClient
{
    public static void main(String[] args) throws Exception
    {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        InetSocketAddress socketAddress = new InetSocketAddress("127.0.0.1", 8000);
        if (!socketChannel.connect(socketAddress))
        {
            while (!socketChannel.finishConnect())
            {
                System.out.println("连接需要时间");
            }
        }
        String str = "hello,刘时明!!!";
        ByteBuffer buffer = ByteBuffer.wrap(str.getBytes());
        socketChannel.write(buffer);
        System.in.read();
    }
}
