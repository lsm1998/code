package demo.net.echoes_aio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.Charset;

/**
 * @作者：刘时明
 * @时间：2019/6/8-15:09
 * @说明：
 */
public class AClient
{
    public static void main(String[] args)
    {
        ByteBuffer buffer=ByteBuffer.allocate(1024);
        Charset charset=Charset.forName("utf-8");
        try(AsynchronousSocketChannel socketChannel=AsynchronousSocketChannel.open())
        {
            socketChannel.connect(new InetSocketAddress("127.0.0.1",8888)).get();
            buffer.clear();
            socketChannel.read(buffer).get();
            buffer.flip();
            String text=charset.decode(buffer).toString();
            System.out.println("收到消息："+text);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
