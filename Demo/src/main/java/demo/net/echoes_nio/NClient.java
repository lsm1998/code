package demo.net.echoes_nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Scanner;

/**
 * @作者：刘时明
 * @时间：2019/6/8-14:44
 * @说明：
 */
public class NClient
{
    private Selector selector;
    private Charset charset = Charset.forName("utf-8");
    private SocketChannel channel;

    public static void main(String[] args) throws Exception
    {
        new NClient().init();
    }

    public void init() throws Exception
    {
        selector = Selector.open();
        InetSocketAddress isa = new InetSocketAddress("127.0.0.1", 8888);
        channel = SocketChannel.open(isa);
        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_READ);
        new ClientThread().start();

        Scanner scan = new Scanner(System.in);
        while (scan.hasNextLine())
        {
            String line = scan.nextLine();
            channel.write(charset.encode(line));
        }
    }

    private class ClientThread extends Thread
    {
        @Override
        public void run()
        {
            try
            {
                while (selector.select() > 0)
                {
                    for (SelectionKey key : selector.selectedKeys())
                    {
                        selector.selectedKeys().remove(key);
                        if (key.isReadable())
                        {
                            SocketChannel channel = (SocketChannel) key.channel();
                            ByteBuffer buffer = ByteBuffer.allocate(1024);
                            StringBuilder sb = new StringBuilder();
                            while (channel.read(buffer) >0)
                            {
                                channel.read(buffer);
                                buffer.flip();
                                sb.append(charset.decode(buffer));
                                System.out.println("信息：" + sb);
                                key.interestOps(SelectionKey.OP_READ);
                            }
                        }
                    }
                }
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
