package com.lsm1998.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * @program: code
 * @description:
 * @author: lsm
 * @create: 2020-04-10 14:51
 **/
public class UDPReceive
{
    public static void main(String[] args) throws IOException
    {
        DatagramSocket ds = new DatagramSocket(10000);
        byte[] words = new byte[1024];
        DatagramPacket dp = new DatagramPacket(words, 0, words.length);
        while (true)
        {
            ds.receive(dp);
            byte[] data = dp.getData();
            String str = new String(data, 0, data.length);
            System.out.println(str);
        }
    }
}
