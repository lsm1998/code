package demo.net.bio;

import com.lsm1998.util.net.bean.MsgData;
import com.lsm1998.util.net.bio.TcpServer;

import java.io.IOException;

/**
 * @作者：刘时明
 * @时间：2019/6/16-22:47
 * @作用：
 */
public class ServerTest
{
    public static void main(String[] args) throws IOException
    {
        TcpServer server = new TcpServer("127.0.0.1", 8888);
        server.start((data, oos, list, socket) ->
        {
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
                list.remove(socket);
                System.out.println("客户端已经退出");
            }
        });
    }
}
