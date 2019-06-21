package com.lsm1998.util.net;

import com.lsm1998.util.net.bean.MsgData;
import com.lsm1998.util.net.bio.TcpServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @作者：刘时明
 * @时间：2019/6/16-22:01
 * @作用：
 */
public class NetTest
{
    public static void main(String[] args) throws Exception
    {
        NetTest test=new NetTest();
        // 启动服务端
        test.new Server().start();

        // 启动客户端
        test.client();
    }

    class Server extends Thread
    {
        @Override
        public void run()
        {
            try
            {
                TcpServer server = new TcpServer("127.0.0.1", 8888);
                server.start((data, oos, list) ->
                        {
                            System.out.println("收到消息："+data.msg);
                            MsgData result = new MsgData();
                            switch (data.code)
                            {
                                case 0:
                                    result.msg = "hello";
                                    break;
                                case 1:
                                    result.msg = "你好";
                                    break;
                            }
                            try
                            {
                                oos.writeObject(result);
                            } catch (IOException e)
                            {
                                e.printStackTrace();
                            }
                        }
                );
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    private void client() throws Exception
    {
        Socket socket=new Socket("127.0.0.1",8888);
        ObjectOutputStream oos=new ObjectOutputStream(socket.getOutputStream());
        this.new Accept(socket).start();
        // 发送数据
        MsgData data = new MsgData();
        data.code=0;
        data.msg="服务端你好";
        oos.writeObject(data);
        System.out.println("数据发出");
    }

    class Accept extends Thread
    {
        private ObjectInputStream ois;
        private Socket socket;

        public Accept(Socket socket)
        {
            this.socket=socket;
            try
            {
                this.ois=new ObjectInputStream(socket.getInputStream());
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        @Override
        public void run()
        {
            while (true)
            {
                try
                {
                    // 读取数据
                    MsgData result= (MsgData)ois.readObject();
                    System.out.println("客户端收到消息："+result.msg);
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}
