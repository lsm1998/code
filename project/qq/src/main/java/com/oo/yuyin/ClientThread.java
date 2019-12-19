package com.oo.yuyin;

import javax.sound.sampled.SourceDataLine;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Arrays;

/**
 * 作者：刘时明
 * 日期：2018/10/5
 * 时间：23:29
 * 说明：
 */
public class ClientThread extends Thread
{
    private Socket socket;
    private InputStream in;
    private static byte[] bis = new byte[1024 * 2];

    public ClientThread(Socket socket)
    {
        this.socket = socket;
        try
        {
            in = socket.getInputStream();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void run()
    {
        try
        {
            SourceDataLine sourceDataLine = AudioUtil.getSourceDataLine();
            while (true)
            {
                int readLen = in.read(bis);
                sourceDataLine.write(bis, 0, readLen);
            }
        } catch (Exception e)
        {
            System.out.println("断开连接");
        }
    }
}
