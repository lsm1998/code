package com.lsm1998.net.udp;

import java.io.IOException;
import java.net.*;

/**
 * @program: code
 * @description:
 * @author: lsm
 * @create: 2020-04-10 14:50
 **/
public class UDPSend
{
    public static void main(String[] args) throws IOException
    {
        DatagramSocket ds=new DatagramSocket();
        for (int i = 0; i < 100; i++)
        {
            String data=String.format("数据包%d号",i+1);
            byte[] bytes=data.getBytes();
            DatagramPacket dp=new DatagramPacket(bytes,0,bytes.length, InetAddress.getByName("127.0.0.1"),10000);
            ds.send(dp);
        }
        ds.close();
    }
}
