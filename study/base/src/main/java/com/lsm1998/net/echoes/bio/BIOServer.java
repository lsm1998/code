/*
 * 作者：刘时明
 * 时间：2020/4/18-22:38
 * 作用：
 */
package com.lsm1998.net.echoes.bio;

import com.lsm1998.net.echoes.Server;

import java.net.ServerSocket;
import java.net.Socket;

public class BIOServer implements Server
{
    private int port;

    public BIOServer(int port)
    {
        this.port=port;
    }

    @Override
    public void start()
    {
        try
        {
            this.init();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void init() throws Exception
    {
        ServerSocket serverSocket=new ServerSocket(port);
        while (true)
        {
            Socket socket = serverSocket.accept();
            new BIOHandler(socket).start();
        }
    }
}
