/*
 * 作者：刘时明
 * 时间：2020/4/18-22:43
 * 作用：
 */
package com.lsm1998.net.echoes.bio;

import com.lsm1998.net.echoes.Client;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class BIOClient implements Client
{
    private int port;
    private OutputStream output;
    private Socket socket;

    public BIOClient(int port)
    {
        this.port=port;
    }

    @Override
    public void stop()
    {
        try
        {
            socket.close();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void connect()
    {
        try
        {
            socket=new Socket("127.0.0.1",port);
            output=socket.getOutputStream();
            listener(socket.getInputStream());
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void send(String msg)
    {
        try
        {
            output.write(msg.getBytes());
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void listener(InputStream input)
    {
        new Thread(()->
        {
            try
            {
                while (true)
                {
                    byte[] bytes=new byte[1024];
                    int len = input.read(bytes);
                    System.out.println("收到消息="+new String(bytes,0,len));
                }
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }).start();
    }
}
