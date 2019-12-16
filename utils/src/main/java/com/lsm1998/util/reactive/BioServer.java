package com.lsm1998.util.reactive;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * @作者：刘时明
 * @时间：2019/6/19-15:48
 * @作用：
 */
public class BioServer
{
    public static void main(String[] args)
    {
        server();
    }

    public static void server()
    {
        ServerSocket serverSocket = null;
        InputStream in = null;
        try
        {
            serverSocket = new ServerSocket(8888);
            int recvMsgSize;
            byte[] recvBuf = new byte[1024];
            while (true)
            {
                Socket clntSocket = serverSocket.accept();
                SocketAddress clientAddress = clntSocket.getRemoteSocketAddress();
                System.out.println("Handling client at " + clientAddress);
                in = clntSocket.getInputStream();
                while ((recvMsgSize = in.read(recvBuf)) != -1)
                {
                    byte[] temp = new byte[recvMsgSize];
                    System.arraycopy(recvBuf, 0, temp, 0, recvMsgSize);
                    System.out.println(new String(temp));
                }
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        } finally
        {
            try
            {
                if (serverSocket != null)
                {
                    serverSocket.close();
                }
                if (in != null)
                {
                    in.close();
                }
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
