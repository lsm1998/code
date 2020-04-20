package chat.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatServer
{
    private static final int PORT = 30001;

    public void init()
    {
        try
        {
            ExecutorService executor = Executors.newCachedThreadPool();
            AsynchronousChannelGroup channelGroup = AsynchronousChannelGroup.withThreadPool(executor);
            AsynchronousServerSocketChannel serverChannel = AsynchronousServerSocketChannel.open(channelGroup).bind(new InetSocketAddress(PORT));
            serverChannel.accept(this, new ServerHandler(serverChannel));
            CountDownLatch latch=new CountDownLatch(1);
            latch.await();
        } catch (IOException | InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        new ChatServer().init();
    }
}
