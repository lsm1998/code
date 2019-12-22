/*
 * 作者：刘时明
 * 时间：2019/12/21-18:39
 * 作用：
 */
package demo.net.ftp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class NIOFtpServer
{
    public static void main(String[] args) throws IOException
    {
        InetSocketAddress address = new InetSocketAddress(8000);
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(address);
        ByteBuffer buffer=ByteBuffer.allocate(1024);
        while (true)
        {
            SocketChannel socketChannel = serverSocketChannel.accept();
            int len=0;
            while (len!=-1)
            {
                len=socketChannel.read(buffer);
                // position设置为0
                buffer.rewind();
            }
        }
    }
}
