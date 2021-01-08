package com.lsm1998.net.bio;

import com.lsm1998.util.net.bean.MsgData;
import com.lsm1998.util.net.bio.TcpClient;

public class Client
{
    public static void main(String[] args) throws Exception
    {
        TcpClient client = new TcpClient("127.0.0.1", 30001);

        client.start(data -> System.out.println("收到消息：" + data.getData()));

        for (int i = 0; i < 10; i++)
        {
            client.send(MsgData.of(100,"hello"));
        }
    }
}
