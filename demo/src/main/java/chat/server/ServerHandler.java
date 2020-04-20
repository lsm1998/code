package chat.server;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;

/**
 * @program: code
 * @description:
 * @author: lsm
 * @create: 2020-04-16 17:17
 **/
public class ServerHandler implements CompletionHandler<AsynchronousSocketChannel, ChatServer>
{
    private AsynchronousServerSocketChannel serverChannel;

    public ServerHandler(AsynchronousServerSocketChannel serverChannel)
    {
        this.serverChannel = serverChannel;
    }

    @Override
    public void completed(AsynchronousSocketChannel channel, ChatServer attachment)
    {
        // 处理下一次的client连接。类似链式调用
        serverChannel.accept(attachment, this);
        // 执行业务逻辑
        doRead(channel);
    }

    /**
     * 读取client发送的消息打印到控制台
     *
     * AIO中OS已经帮助我们完成了read的IO操作，所以我们直接拿到了读取的结果
     *
     * @param channel 服务端于客户端通信的 channel
     */
    private void doRead(AsynchronousSocketChannel channel)
    {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        // 从client读取数据,在我们调用clientChannel.read()之前OS，已经帮我们完成了IO操作
        // 我们只需要用一个缓冲区来存放读取的内容即可
        channel.read(buffer, buffer, new CompletionHandler<>()
                {
                    @Override
                    public void completed(Integer result, ByteBuffer attachment) {
                        // 读取准备
                        attachment.flip();
                        // 读取client发送的数据
                        String content=new String(attachment.array(), StandardCharsets.UTF_8);
                        // 向client写入数据
                        doWrite(channel,content);
                    }

                    @Override
                    public void failed(Throwable exc, ByteBuffer attachment)
                    {

                    }
                }
        );
    }

    private void doWrite(AsynchronousSocketChannel channel,String content)
    {
        // 向client发送数据，clientChannel.write()是一个异步调用，该方法执行后会通知
        // OS执行写的IO操作，会立即返回
        channel.write(ByteBuffer.wrap(content.getBytes(StandardCharsets.UTF_8)));
        System.out.println("发送数据="+content);
        // clientChannel.write(buffer).get(); // 会进行阻塞，直到OS写操作完成
    }

    @Override
    public void failed(Throwable throwable, ChatServer o)
    {
        System.out.println("连接失败:" + throwable.getMessage());
    }
}
