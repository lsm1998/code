/*
 * 作者：刘时明
 * 时间：2019/12/20-23:34
 * 作用：
 */
package demo.net.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

public class BufferArrayDemo
{
    public static void main(String[] args) throws Exception
    {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress socketAddress = new InetSocketAddress(8000);
        serverSocketChannel.socket().bind(socketAddress);

        ByteBuffer[] buffers = new ByteBuffer[2];
        for (int i = 0; i < buffers.length; i++)
        {
            buffers[i] = ByteBuffer.allocate(4);
        }

        SocketChannel socketChannel = serverSocketChannel.accept();
        int max = 8;
        while (true)
        {
            int len = 0;
            while (len < max)
            {
                long read = socketChannel.read(buffers);
                len += read;
                System.out.println("当前读取了=" + len);
                Arrays.stream(buffers).map(byteBuffer -> "position" + byteBuffer.position() + ",limit="
                        + byteBuffer.limit()).forEach(System.out::println);
            }
            Arrays.stream(buffers).forEach(ByteBuffer::flip);
            // 数据回写到客户端
            long write = 0;
            while (write < max)
            {
                long l = socketChannel.write(buffers);
                write += l;
            }
            // ByteBuffer复位
            Arrays.stream(buffers).forEach(ByteBuffer::clear);
        }
    }
}
