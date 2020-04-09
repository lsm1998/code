package com.lsm1998.servlet;

import com.google.gson.Gson;
import com.lsm1998.domain.Hello;
import com.lsm1998.tomcat.annotaion.WebServlet;
import com.lsm1998.tomcat.http.HttpServlet;
import com.lsm1998.tomcat.http.HttpServletRequest;
import com.lsm1998.tomcat.http.HttpServletResponse;

/**
 * @program: code
 * @description:
 * @author: lsm
 * @create: 2020-04-09 10:21
 **/
@WebServlet(url = "/hello")
public class HelloServlet extends HttpServlet
{
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        String name = request.getParameter("name");
        response.setContentType("application/json");
        Gson gson=new Gson();
        Hello hello=new Hello();
        hello.setId(1L);
        hello.setName(name);
        response.getWrite().write(gson.toJson(hello));
    }
}
