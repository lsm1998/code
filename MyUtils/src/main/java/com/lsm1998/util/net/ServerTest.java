package com.lsm1998.util.net;

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
        server.start((data, oos, list) ->
        {
            System.out.println("服务端接受消息：" + data.msg);
            MsgData result = new MsgData();
            switch (data.code)
            {
                case 0:
                    result.msg = "hello";
                    break;
                case 1:
                    result.msg = "你好";
                    break;
                case 2:
                    result.msg = "回声：" + data.msg;
                    break;
            }
            try
            {
                oos.writeObject(result);
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        });
    }
}
