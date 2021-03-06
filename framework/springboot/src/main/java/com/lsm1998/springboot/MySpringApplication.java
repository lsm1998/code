package com.lsm1998.springboot;

import com.lsm1998.spring.beans.MyAutoConfigure;
import com.lsm1998.spring.context.MyAnnotationConfigApplicationContext;
import com.lsm1998.spring.web.MyDispatchServlet;
import com.lsm1998.spring.beans.annotation.MySpringBootApplication;
import com.lsm1998.springboot.autoconfigure.MybatisAutoConfigure;
import com.lsm1998.springboot.servlet.IndexServlet;
import com.lsm1998.springboot.servlet.StaticServlet;
import com.lsm1998.springboot.servlet.TemplatesServlet;
import com.lsm1998.springboot.tomcat.ServletAndParrern;
import com.lsm1998.springboot.tomcat.TomcatServer;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @作者：刘时明
 * @时间：2019/1/10-12:35
 * @说明：
 */
@Slf4j
public class MySpringApplication
{
    private static final String DEFAULT_CONFIG="/myspringboot.properties";
    private static final int DEFAULT_PORT = 8080;

    public static MyAnnotationConfigApplicationContext run(Class<?> clazz, String[] args)
    {
        // 获取配置文件
        Properties properties = new Properties();
        String filePath= MySpringApplication.class.getResource(DEFAULT_CONFIG).getFile();
        File file = new File(filePath);
        if (!file.exists())
        {
            log.error("找不到myspringboot配置文件");
            return null;
        }
        try
        {
            properties.load(new FileInputStream(file));
        } catch (Exception e)
        {
            log.error("访问myspringboot配置文件出现错误");
            e.printStackTrace();
        }
        MyAnnotationConfigApplicationContext context=loadSpringContext(properties, clazz);
        run(properties, clazz);
        return context;
    }

    private static MyAnnotationConfigApplicationContext loadSpringContext(Properties properties, Class<?> clazz)
    {
        log.info("MySpring开始加载");
        log.info("init初始化配置");
        List<MyAutoConfigure> autoConfigureList=new ArrayList<>();
        MySpringBootApplication bootApplication = clazz.getAnnotation(MySpringBootApplication.class);
        // 是否排除Mybatis依赖
        Class<?>[] excludeClass = bootApplication.exclude();
        boolean flag = true;
        for (Class<?> c : excludeClass)
        {
            if (c == MybatisAutoConfigure.class)
            {
                flag = false;
                break;
            }
        }
        if (flag)
        {
            autoConfigureList.add(new MybatisAutoConfigure(properties));
        }
        MyAnnotationConfigApplicationContext context = new MyAnnotationConfigApplicationContext(clazz, properties,autoConfigureList);
        log.info("IOC容器初始化完毕");
        return context;
    }

    private static void run(Properties properties, Class<?> clazz)
    {
        // 首页配置Servlet
        ServletAndParrern indexServlet = new ServletAndParrern(new IndexServlet(properties));
        // 静态资源Servlet
        ServletAndParrern staticServlet = new ServletAndParrern(new StaticServlet());
        // 模版页面Servlet
        ServletAndParrern templatesServlet = new ServletAndParrern(new TemplatesServlet());
        // SpringMVC
        ServletAndParrern dispatchServlet = new ServletAndParrern(new MyDispatchServlet(clazz, properties));

        List<ServletAndParrern> list = new ArrayList<>();
        list.add(indexServlet);
        list.add(staticServlet);
        list.add(templatesServlet);
        list.add(dispatchServlet);
        int port = DEFAULT_PORT;
        if (properties.containsKey("port"))
        {
            try
            {
                port = Integer.parseInt(properties.getProperty("port"));
            } catch (Exception e)
            {
                log.error("配置项：port，必须是整数类型");
                e.printStackTrace();
            }
        }
        // 启动servlet容器
        TomcatServer.start("", list, port);
    }
}
