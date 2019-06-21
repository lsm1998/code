package com.lsm1998.tomcat.client;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @作者：刘时明
 * @时间：2019/6/16-11:58
 * @作用：
 */
public class HttpClient
{
    public void HttpRequest()
    {
        try(Socket socket=new Socket("www.bejson.com",80);
            InputStream is=socket.getInputStream();
            OutputStream os=socket.getOutputStream())
        {
            // 请求行
            String requestBank="GET /jsonviewernew/ HTTP/1.1\n";
            os.write(requestBank.getBytes());
            // 请求头
            String requestHead="HOST:www.bejson.com\n";
            os.write(requestHead.getBytes());
            os.write("\n".getBytes());

            int data;
            while ((data=is.read())!=-1)
            {
                System.out.print((char)data);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
