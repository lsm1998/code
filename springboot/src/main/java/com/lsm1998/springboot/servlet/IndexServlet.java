package com.lsm1998.springboot.servlet;

import com.lsm1998.spring.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;

/**
 * @作者：刘时明
 * @时间：2019/1/13-11:51
 * @说明：
 */
@WebServlet(urlPatterns = "/")
public class IndexServlet extends HttpServlet implements BaseServlet
{
    private Properties properties;

    @Override
    public void loadOnStartup()
    {
        System.out.println("加载首页资源");
    }

    public IndexServlet(Properties properties)
    {
        this.properties=properties;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        if(properties.containsKey("index"))
        {
            resp.sendRedirect("/"+properties.getProperty("index"));
        }else
        {
            resp.sendRedirect("/index.html");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        this.doGet(req, resp);
    }
}
