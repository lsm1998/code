package com.lsm1998.tomcat.server;

import com.lsm1998.util.file.MyFiles;
import com.lsm1998.util.http.parse.HttpRequestParse;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @作者：刘时明
 * @时间：2019/6/16-13:46
 * @作用：
 */
public class HandleThread extends Thread
{
    private Socket socket;
    private OutputStream os;
    private InputStream is;

    public HandleThread(Socket socket)
    {
        this.socket = socket;
        try
        {
            os = socket.getOutputStream();
            is = socket.getInputStream();
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
            //HttpRequestParse.HttpRequest request = HttpRequestParse.parse(is);
//            byte[] bytes = new byte[1024];
//            int len;
//            while ((len = is.read(bytes)) != -1)
//            {
//                System.out.println(new String(bytes, 0, len));
//            }
//
//            String responseLine = "HTTP/1.1 200 ok\n";
//            String[] responseHead = {
//                    "content-type:text/html;charset=utf-8\n",
//                    "Server:Apache-Coyote/1.1\n",
//                    "\n\n"};
//            os.write(responseLine.getBytes());
//            for (String str : responseHead)
//            {
//                os.write(str.getBytes());
//            }
//            File file = MyFiles.getFileByStatic("index.html");
//            os.write(MyFiles.getBytes(file));
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
