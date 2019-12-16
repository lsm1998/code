package com.github.nmyphp;

import com.github.nmyphp.mytomcat.Dispatcher;
import com.github.nmyphp.mytomcat.HttpRequest;
import com.github.nmyphp.mytomcat.HttpResponse;
import com.github.nmyphp.mytomcat.StatusCode;
import com.github.nmyphp.mytomcat.util.ConfigUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import lombok.extern.slf4j.Slf4j;

/**
 * Tomcat入口.
 *
 * @author nmyphp
 */
@Slf4j
public class Tomcat
{
    private static int port;

    public static void main(String[] args) throws IOException
    {
        //port = Integer.valueOf(ConfigUtil.properties.getProperty("port", "8080"));
        port=8080;
        new Tomcat().start();
    }

    public void start() throws IOException
    {
        ServerSocket server = null;
        System.out.println("Tomcat is started! [" + port + "]");
        try
        {
            server = new ServerSocket(port);
            Dispatcher dispatcher = new Dispatcher();
            while (true)
            {
                Socket socket = server.accept();
                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();
                HttpRequest req = new HttpRequest(inputStream);
                HttpResponse res = new HttpResponse(outputStream, req.getContentType(), req.getCharsetName());
                res.setStatusCode(StatusCode.OK);
                dispatcher.dispatch(req, res);
                inputStream.close();
                outputStream.close();
                socket.close();
            }
        } finally
        {
            if (null != server)
            {
                server.close();
            }
        }
    }
}
