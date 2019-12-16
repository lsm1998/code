package com.github.nmyphp.mytomcat.servlet;

import com.github.nmyphp.mytomcat.HttpRequest;
import com.github.nmyphp.mytomcat.HttpResponse;
import com.github.nmyphp.mytomcat.HttpServlet;
import java.io.IOException;

public class HelloServlet extends HttpServlet {

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        httpResponse.write("你发送了一个GET请求");
    }

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        httpResponse.write("你发送了一个POST请求");
    }
}
