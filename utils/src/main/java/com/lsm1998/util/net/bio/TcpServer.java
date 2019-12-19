package com.lsm1998.util.net.bio;

import com.lsm1998.util.net.bean.MsgData;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @作者：刘时明
 * @时间：2019/6/16-20:55
 * @作用：
 */
public class TcpServer
{
    private InetSocketAddress address;
    private List<Socket> list;

    public TcpServer(String host, int port)
    {
        this.address = new InetSocketAddress(host, port);
        this.list = new ArrayList<>();
    }

    public void start(ServerHandle handle) throws IOException
    {
        ServerSocket server = new ServerSocket();
        server.bind(address);
        System.out.println("TCP Server start...");
        while (true)
        {
            Socket socket = server.accept();
            list.add(socket);
            this.new TcpServerThread(socket, handle).start();
        }
    }

    class TcpServerThread extends Thread
    {
        private Socket socket;
        private ObjectInputStream ois;
        private ObjectOutputStream oos;
        private ServerHandle handle;

        public TcpServerThread(Socket socket, ServerHandle handle)
        {
            this.socket = socket;
            this.handle = handle;
            try
            {
                ois = new ObjectInputStream(socket.getInputStream());
                oos = new ObjectOutputStream(socket.getOutputStream());
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
                MsgData data = readData();
                if (data != null)
                {
                    handle.handle(data, oos, list, socket);
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
                list.remove(socket);
            }
            return null;
        }
    }
}
