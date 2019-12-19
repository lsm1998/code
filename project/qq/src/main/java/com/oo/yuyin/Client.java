package com.oo.yuyin;

import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import javax.sound.sampled.TargetDataLine;

public class Client
{
    private long acc;
    private static byte[] bos = new byte[1024 * 5];

    public Client(long acc)
    {
        this.acc = acc;
        startClient();
    }

    private void startClient()
    {
        try
        {
            Socket socket = new Socket(InetAddress.getLocalHost(), 8090);
            new ClientThread(socket).start();
            TargetDataLine targetDataLine = AudioUtil.getTargetDataLine();
            OutputStream os = socket.getOutputStream();
            while (true)
            {
                int writeLen = targetDataLine.read(bos, 0, bos.length);
                os.write(bos, 0, writeLen);
            }
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        new Client(123L);
    }
}