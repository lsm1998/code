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
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

@Slf4j
public class ChatClient
{
    private final static String HOST = "127.0.0.1";
    private final static int PORT = 8000;
    private Selector selector;
    private SocketChannel socketChannel;
    private String userName;

    public ChatClient() throws IOException
    {
        selector = Selector.open();
        socketChannel = SocketChannel.open(new InetSocketAddress(HOST, PORT));
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
        userName = socketChannel.getLocalAddress().toString().substring(1);
        log.info("连接服务器完成");
    }

    public <E> void sendMsg(MsgData<E> data)
    {
        Optional<byte[]> optional = BitObjectUtil.objectToBytes(data);
        if (optional.isPresent())
        {
            try
            {
                socketChannel.write(ByteBuffer.wrap(optional.get()));
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void readMsg()
    {
        try
        {
            while (true)
            {
                int count = selector.select();
                if (count > 0)
                {
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    for (SelectionKey key : selectionKeys)
                    {
                        selector.selectedKeys().remove(key);
                        if (key.isReadable())
                        {
                            SocketChannel channel = (SocketChannel) key.channel();
                            ByteBuffer buffer = ByteBuffer.allocate(1024);
                            channel.read(buffer);
                            byte[] b = buffer.array();
                            printlnData(b);
                        }
                    }
                }
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void printlnData(byte[] b)
    {
        Optional<MsgData<String>> optional = BitObjectUtil.bytesToObject(b);
        if (optional.isPresent())
        {
            log.info("收到服务端的消息={}", optional.get().getData());
        }
    }

    public static void main(String[] args) throws IOException
    {
        ChatClient chatClient = new ChatClient();
        new Thread(chatClient::readMsg).start();

        Scanner scanner = new Scanner(System.in);
        while (true)
        {
            String str = scanner.nextLine();
            String[] arr = str.split("-");
            MsgData<String> data;
            if (arr.length == 1)
            {
                // 默认群发
                data = new MsgData<>();
                data.setCode(1);
                data.setData(str);
                log.info("群聊消息已经发送了");
            }else if(arr.length==2)
            {
                data = new MsgData<>();
                data.setCode(Integer.parseInt(arr[0]));
                data.setData(arr[1]);
            }else
            {
                log.error("数据输入格式错误");
                continue;
            }
            chatClient.sendMsg(data);
        }
    }
}
