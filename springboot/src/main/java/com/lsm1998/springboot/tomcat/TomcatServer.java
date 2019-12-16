package com.lsm1998.springboot.tomcat;

import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;

import javax.servlet.http.HttpServlet;
import java.util.List;

/**
 * @作者：刘时明
 * @时间：2019/1/10-11:20
 * @说明：tomcat服务器启动类
 */
public class TomcatServer
{
    public static void start(String contextPath, List<ServletAndParrern> list, int port)
    {
        // 创建tomcat服务器
        Tomcat server = new Tomcat();

        // 指定端口号
        server.setPort(port);

        // 是否自动部署
        server.getHost().setAutoDeploy(false);

        // 创建上下文
        StandardContext context = new StandardContext();
        context.setPath(contextPath);

        // 监听上下文
        context.addLifecycleListener(new Tomcat.FixContextListener());

        // 上下文添加到服务中
        server.getHost().addChild(context);

        for (ServletAndParrern temp : list)
        {
            // 创建Servlet
            server.addServlet(contextPath, temp.getServletName(), (HttpServlet)temp.getServlet());

            // Servlet映射
            String[] mapping=temp.getParrern();
            for (String url:mapping)
            {
                context.addServletMappingDecoded(url, temp.getServletName());
            }

            temp.getServlet().loadOnStartup();
        }

        // 启动服务
        try
        {
            server.start();
            System.out.println("tomcat启动完成...");

            // 异步接收请求
            server.getServer().await();
        }catch (Exception e)
        {
            System.err.println("tomcat启动出现错误...");
            e.printStackTrace();
        }
    }
}
