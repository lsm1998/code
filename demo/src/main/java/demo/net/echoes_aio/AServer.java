package demo.net.echoes_aio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.Future;

/**
 * @作者：刘时明
 * @时间：2019/6/8-15:03
 * @说明：
 */
public class AServer
{
    public static void main(String[] args)
    {
        try(AsynchronousServerSocketChannel serverChannel=AsynchronousServerSocketChannel.open())
        {
            serverChannel.bind(new InetSocketAddress(8888));
            System.out.println("服务器已经启动");
            while (true)
            {
                Future<AsynchronousSocketChannel> future=serverChannel.accept();
                AsynchronousSocketChannel socketChannel=future.get();

                ByteBuffer b= ByteBuffer.wrap("欢迎来到AIO！！！".getBytes("utf-8"));
                socketChannel.write(b).get();
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
