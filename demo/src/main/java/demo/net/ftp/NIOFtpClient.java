/*
 * 作者：刘时明
 * 时间：2019/12/21-18:45
 * 作用：
 */
package demo.net.ftp;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

public class NIOFtpClient
{
    public static void main(String[] args) throws IOException
    {
        SocketChannel socketChannel=SocketChannel.open(new InetSocketAddress("127.0.0.1",8000));
        FileInputStream fis=new FileInputStream("C:\\Users\\Administrator\\Desktop\\1.jpg");
        FileChannel fileChannel=fis.getChannel();
        long start=System.currentTimeMillis();
        // transferTo底层使用零拷贝
        long len = fileChannel.transferTo(0, fileChannel.size(), socketChannel);
        long end=System.currentTimeMillis();
        System.out.println("发送数据字节="+len+"耗时="+(end-start));
        fileChannel.close();
        socketChannel.close();
    }
}
