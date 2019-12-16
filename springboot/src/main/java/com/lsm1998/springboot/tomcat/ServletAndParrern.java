package com.lsm1998.springboot.tomcat;

import com.lsm1998.spring.web.BaseServlet;

import javax.servlet.annotation.WebServlet;

/**
 * @作者：刘时明
 * @时间：2019/1/10-12:02
 * @说明：
 */
public class ServletAndParrern
{
    private String[] parrern;
    private BaseServlet servlet;
    private String servletName;

    public ServletAndParrern(BaseServlet servlet)
    {
        this.servlet = servlet;
        if(servlet.getClass().isAnnotationPresent(WebServlet.class))
        {
            WebServlet webServlet=servlet.getClass().getAnnotation(WebServlet.class);
            this.parrern=webServlet.urlPatterns();
            servletName=
            webServlet.name().equals("")?servletName=servlet.getClass().getName():webServlet.name();
        }else
        {
            System.err.println(servlet.getClass()+"类找不到WebServlet注解");
        }
    }

    public String[] getParrern()
    {
        return parrern;
    }

    public void setParrern(String[] parrern)
    {
        this.parrern = parrern;
    }

    public BaseServlet getServlet()
    {
        return servlet;
    }

    public void setServlet(BaseServlet servlet)
    {
        this.servlet = servlet;
    }

    public String getServletName()
    {
        return servletName;
    }

    public void setServletName(String servletName)
    {
        this.servletName = servletName;
    }
}
