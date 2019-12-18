package com.oo.yuyin;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Server
{
    private Socket socket;

    public Server()
    {
        startServer();
    }

    private void startServer()
    {
        try
        {
            ServerSocket serverSocket = new ServerSocket(8090);
            while (true)
            {
                socket = serverSocket.accept();
                new ServerThread(socket).start();
            }
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        new Server();
    }
}