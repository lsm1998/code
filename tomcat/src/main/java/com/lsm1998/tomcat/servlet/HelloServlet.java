package com.lsm1998.tomcat.servlet;

import com.lsm1998.tomcat.HttpRequest;
import com.lsm1998.tomcat.HttpResponse;
import com.lsm1998.tomcat.HttpServlet;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;

// @WebServlet(name = "helloServlet", urlPatterns = "/hello")
public class HelloServlet extends HttpServlet
{
    @Override
    public void doGet(HttpRequest request, HttpResponse response) throws IOException
    {
        response.write("你发送了一个GET请求");
    }

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException
    {
        httpResponse.write("你发送了一个POST请求");
    }
}
