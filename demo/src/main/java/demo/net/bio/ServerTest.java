package demo.net.bio;

import com.lsm1998.util.net.bean.MsgData;
import com.lsm1998.util.net.bio.TcpServer;

import java.io.IOException;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

/**
 * @作者：刘时明
 * @时间：2019/6/16-22:47
 * @作用：
 */
public class ServerTest
{
    private static Set<Socket> sockets = new HashSet<>();

    public static void main(String[] args) throws IOException
    {
        TcpServer server = new TcpServer("127.0.0.1", 8888);
        server.start((data, oos, socket) ->
        {
            if (!sockets.contains(socket))
            {
                sockets.add(socket);
                System.out.println("一个客户端连入，当前在线=" + sockets.size());
            }
            System.out.println("服务端接受消息：" + new String((byte[]) data.getData()));
            MsgData result = new MsgData();
            switch (data.getCode())
            {
                case 0:
                    result.setData("hello".getBytes());
                    break;
                case 1:
                    result.setData("你好".getBytes());
                    break;
                case 2:
                    String msg = "回声：" + new String((byte[]) data.getData());
                    result.setData(msg.getBytes());
                    break;
            }
            try
            {
                oos.writeObject(result);
            } catch (IOException e)
            {
                sockets.remove(socket);
                System.out.println("客户端已经退出，当前在线=" + sockets.size());
            }
        });
    }
}
