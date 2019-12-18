package com.oo.yuyin;

import javax.sound.sampled.SourceDataLine;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

/**
 * 作者：刘时明
 * 日期：2018/10/5
 * 时间：23:40
 * 说明：
 */
public class ServerThread extends Thread
{
    private Socket socket;

    public ServerThread(Socket socket)
    {
        this.socket = socket;
    }

    @Override
    public void run()
    {
        try
        {
            InputStream is=socket.getInputStream();
            OutputStream os=socket.getOutputStream();
            while (true)
            {
               byte[] b=new byte[1024];
               int len;
               while ((len=is.read(b))>1)
               {
                   os.write(b,0,len);
               }
            }
        } catch (Exception e)
        {
            System.out.println("连接断开");
            e.printStackTrace();
        }
    }
}
