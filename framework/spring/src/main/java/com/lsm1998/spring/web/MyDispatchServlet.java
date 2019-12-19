package com.lsm1998.spring.web;

import com.lsm1998.spring.context.MyAnnotationConfigApplicationContext;
import com.lsm1998.spring.web.method.MyHandlerMapping;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;

/**
 * @作者：刘时明
 * @时间:2018/12/20-17:52
 * @说明：实现拦截、分发的Servlet
 */
@WebServlet(urlPatterns = "*.do")
public class MyDispatchServlet extends HttpServlet implements BaseServlet
{
    private Class<?> clazz;
    private Properties properties;

    public MyDispatchServlet(Class<?> clazz, Properties properties)
    {
        this.clazz = clazz;
        this.properties = properties;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        MyHandlerMapping handlerMapping = new MyHandlerMapping();
        handlerMapping.request(req, resp);
    }


    @Override
    public void loadOnStartup()
    {
//        System.out.println("MySpring开始加载");
//        System.out.println("init初始化配置");
//        MyAnnotationConfigApplicationContext context = new MyAnnotationConfigApplicationContext(clazz,properties);
//        System.out.println(context.getIoc());
//        System.out.println("IOC容器初始化完毕");
    }
}
