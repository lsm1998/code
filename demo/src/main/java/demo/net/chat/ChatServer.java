/*
 * 作者：刘时明
 * 时间：2019/12/21-11:12
 * 作用：
 */
package demo.net.chat;

import com.lsm1998.util.bit.BitObjectUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Optional;
import java.util.Set;

@Slf4j
public class ChatServer
{
    private Selector selector;
    private ServerSocketChannel listChannel;
    private static final int PORT = 8000;

    public ChatServer()
    {
        try
        {
            selector = Selector.open();
            InetSocketAddress address = new InetSocketAddress(PORT);
            listChannel = ServerSocketChannel.open();
            listChannel.bind(address);
            listChannel.configureBlocking(false);
            listChannel.register(selector, SelectionKey.OP_ACCEPT);
            listen();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void listen() throws IOException
    {
        log.info("服务器开始监听。。。");
        log.info("当size={}",selector.keys().size());
        while (true)
        {
            int count = selector.select(1000);
            if (count > 0)
            {
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                for (SelectionKey key : selectionKeys)
                {
                    selectionKeys.remove(key);
                    if (key.isAcceptable())
                    {
                        SocketChannel channel = listChannel.accept();
                        // onLineSet.add(channel);
                        channel.configureBlocking(false);
                        channel.register(selector, SelectionKey.OP_READ);
                        log.info("{}已经上线了，当前在线人数{}", channel.getRemoteAddress(), selector.keys().size());
                    }
                    if (key.isReadable())
                    {
                        // 获取消息
                        MsgData<String> data = readData(key);
                        // 转发消息
                        if (data != null)
                        {
                            forward(data, (SocketChannel) key.channel());
                        }
                    }
                }
            }
        }
    }

    private void forward(MsgData<String> data, SocketChannel self) throws IOException
    {
        switch (data.getCode())
        {
            case 1:
                // 群发消息
                groupSendMsgToChannel(data, self);
                break;
            case 2:
                // 回声消息
                echoes(data, self);
                break;
            default:
                log.error("不能识别的code，code={}", data.getCode());
                break;
        }
    }

    private void echoes(MsgData<String> data, SocketChannel self) throws IOException
    {
        data.setData("echoes ##=>" + data.getData());
        Optional<byte[]> optional = BitObjectUtil.objectToBytes(data);
        if (optional.isPresent())
        {
            self.write(ByteBuffer.wrap(optional.get()));
        }
    }

    private void groupSendMsgToChannel(MsgData<String> data, SocketChannel self) throws IOException
    {
        for (SelectionKey key : selector.keys())
        {
            Channel channel = key.channel();
            if (channel instanceof SocketChannel && channel != self)
            {
                SocketChannel target = (SocketChannel) channel;
                Optional<byte[]> optional = BitObjectUtil.objectToBytes(data);
                if (optional.isPresent())
                {
                    target.write(ByteBuffer.wrap(optional.get()));
                }
            }
        }
    }

    private <E> MsgData<E> readData(SelectionKey key) throws IOException
    {
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int count = 0;
        try
        {
            count = channel.read(buffer);
        } catch (IOException e)
        {
            // 取消注册
            key.cancel();
            // 关闭通道
            channel.close();
            log.info("客户端{}退出连接", channel.getRemoteAddress());
            return null;
        }
        if (count > 0)
        {
            Optional<MsgData<E>> optional = BitObjectUtil.bytesToObject(buffer.array());
            if (optional.isPresent())
            {
                MsgData<E> data = optional.get();
                log.info("收到客户端消息={}", data);
                return data;
            }
        }
        return null;
    }

    public static void main(String[] args)
    {
        ChatServer server = new ChatServer();
    }
}
