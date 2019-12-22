package com.lsm1998.util.net.bio;

import com.lsm1998.util.net.bean.MsgData;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @作者：刘时明
 * @时间：2019/6/16-22:28
 * @作用：
 */
public class TcpClient
{
    private Socket socket;
    private ObjectOutputStream oos;
    private boolean isClose;

    public TcpClient(String host, int port) throws IOException
    {
        this.socket = new Socket(host, port);
        try
        {
            isClose=false;
            oos = new ObjectOutputStream(socket.getOutputStream());
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void start(ClientHandle handle)
    {
        this.new TcpClientThread(handle).start();
    }

    public void send(MsgData data)
    {
        try
        {
            oos.writeObject(data);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void exit() throws IOException
    {
        this.socket.close();
        isClose=true;
    }

    class TcpClientThread extends Thread
    {
        private ObjectInputStream ois;
        private ClientHandle clientHandle;

        public TcpClientThread(ClientHandle clientHandle)
        {
            this.clientHandle = clientHandle;
            try
            {
                ois = new ObjectInputStream(socket.getInputStream());
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        @Override
        public void run()
        {
            while (true)
            {
                if(isClose)
                {
                    break;
                }
                MsgData data = readData();
                if (data != null)
                {
                    clientHandle.accept(data);
                }
            }
        }

        private MsgData readData()
        {
            try
            {
                return (MsgData) ois.readObject();
            } catch (Exception e)
            {
                System.out.println("服务器socket已经关闭");
                isClose=true;
            }
            return null;
        }
    }
}
