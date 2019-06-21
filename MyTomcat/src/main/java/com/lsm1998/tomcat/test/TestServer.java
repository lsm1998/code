package com.lsm1998.tomcat.test;

import com.lsm1998.util.file.MyFiles;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @作者：刘时明
 * @时间：2019/6/16-16:08
 * @作用：
 */
public class TestServer
{
    private static String url;

    public static void main(String[] args) throws Exception
    {
        ServerSocket server;
        Socket socket = null;
        InputStream is = null;
        OutputStream os = null;
        try
        {
            server = new ServerSocket(8080);
            while (true)
            {
                socket = server.accept();
                is = socket.getInputStream();
                os = socket.getOutputStream();
                parse(is);
                send(os);
                close(is, os, socket);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private static void send(OutputStream os) throws Exception
    {
        os.write("HTTP/1.1 200 ok\n".getBytes());
        os.write("Server:Apache-Coyote/1.1\n".getBytes());
        os.write("content-type:text/html;charset=utf-8\n".getBytes());
        os.write("\n".getBytes());
        File file = MyFiles.getFileByStatic("index.html");
        os.write(MyFiles.getBytes(file));
    }

    private static void parse(InputStream is) throws Exception
    {
        StringBuilder sb = new StringBuilder();
        byte[] bytes = new byte[2048];
        int len = is.read(bytes);
        for (int i = 0; i < len; i++)
        {
            sb.append((char) bytes[i]);
        }
        getUrl(sb);
    }

    private static void getUrl(StringBuilder sb)
    {
        int index1, index2;
        index1 = sb.indexOf(" ");
        if (index1 != -1)
        {
            index2 = sb.indexOf(" ", index1 + 1);
            if (index2 != -1)
            {
                url = sb.substring(index1 + 1, index2);
            }
        }
    }

    private static void close(InputStream is, OutputStream os, Socket socket)
    {
        try
        {
            if (is != null)
            {
                is.close();
                is=null;
            }
            if (os != null)
            {
                os.close();
                os=null;
            }
            if (socket != null)
            {
                socket.close();
                socket=null;
            }
            System.out.println("资源释放");
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
