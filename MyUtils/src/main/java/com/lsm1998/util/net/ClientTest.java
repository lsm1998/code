package com.lsm1998.util.net;

import com.lsm1998.util.net.bean.MsgData;
import com.lsm1998.util.net.bio.TcpClient;

import java.io.IOException;

/**
 * @作者：刘时明
 * @时间：2019/6/16-22:47
 * @作用：
 */
public class ClientTest
{
    public static void main(String[] args) throws IOException
    {
        TcpClient client = new TcpClient("127.0.0.1", 8888);

        client.start(data -> System.out.println("接受来自服务端的消息：" + data.msg));

        for (int i = 0; i < 10; i++)
        {
            MsgData data = new MsgData();
            data.code = i % 3;
            data.msg = "第" + i + "次信息";
            client.send(data);
        }
    }
}
