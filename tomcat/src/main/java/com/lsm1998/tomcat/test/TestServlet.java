/*
 * 作者：刘时明
 * 时间：2019/12/16-22:54
 * 作用：
 */
package com.lsm1998.tomcat.test;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "testServlet",urlPatterns = "test")
public class TestServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String name=req.getParameter("name");
        resp.getWriter().println("hello:"+name);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        super.doPost(req, resp);
    }
}
