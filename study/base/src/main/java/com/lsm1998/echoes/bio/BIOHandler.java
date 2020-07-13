/*
 * 作者：刘时明
 * 时间：2020/4/18-22:39
 * 作用：
 */
package com.lsm1998.echoes.bio;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class BIOHandler extends Thread
{
    private Socket socket;

    public BIOHandler(Socket socket)
    {
        this.socket=socket;
    }

    @Override
    public void run()
    {
        try
        {
            InputStream input = socket.getInputStream();
            OutputStream output=socket.getOutputStream();
            while (true)
            {
                byte[] bytes=new byte[1024];
                int len=input.read(bytes);
                output.write(bytes,0,len);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
