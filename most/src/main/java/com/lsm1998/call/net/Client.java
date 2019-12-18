/*
 * 作者：刘时明
 * 时间：2019/12/17-23:19
 * 作用：
 */
package com.lsm1998.call.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client
{
    public static void main(String[] args) throws Exception
    {
        Socket socket = new Socket("127.0.0.1", 8080);
        socket.getOutputStream().write("hello world".getBytes());
        //BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//        while (true)
//        {
//            String str = br.readLine();
//            System.out.println(str);
//        }
    }
}
