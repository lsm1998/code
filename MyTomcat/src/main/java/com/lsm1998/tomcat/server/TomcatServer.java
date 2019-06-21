package com.lsm1998.tomcat.server;

import com.lsm1998.util.file.MyFiles;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @作者：刘时明
 * @时间：2019/6/16-13:43
 * @作用：
 */
public class TomcatServer
{
    public void start()
    {
        ServerSocket server;
        Socket socket;
        try
        {
            server = new ServerSocket(8080);
            while (true)
            {
                socket = server.accept();
                InputStream is=socket.getInputStream();
                OutputStream os=socket.getOutputStream();

                String responseLine = "HTTP/1.1 200 ok\n";
                String[] responseHead = {
                        "content-type:text/html;charset=utf-8\n",
                        "Server:Apache-Coyote/1.1\n",
                        "\n\n"};
                os.write(responseLine.getBytes());
                for (String str : responseHead)
                {
                    os.write(str.getBytes());
                }
                File file = MyFiles.getFileByStatic("index.html");
                os.write(MyFiles.getBytes(file));

                byte[] bytes = new byte[1024];
                int len;
                while ((len = is.read(bytes)) != -1)
                {
                    System.out.println(new String(bytes, 0, len));
                }

                is.close();
                os.close();
                socket.close();
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
