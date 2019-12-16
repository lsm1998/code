/*
 * 作者：刘时明
 * 时间：2019/12/16-22:50
 * 作用：
 */
package com.lsm1998.tomcat.test;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.AprLifecycleListener;
import org.apache.catalina.core.StandardServer;
import org.apache.catalina.startup.Tomcat;

import javax.servlet.ServletException;
import java.io.File;

public class TomcatTest
{
    private Tomcat tomcat;

    private void startTomcat(int port, String contextPath, String baseDir) throws ServletException, LifecycleException
    {
        tomcat = new Tomcat();
        tomcat.setPort(port);
        tomcat.setBaseDir(".");
        StandardServer server = (StandardServer) tomcat.getServer();
        AprLifecycleListener listener = new AprLifecycleListener();
        server.addLifecycleListener(listener);
        System.out.println();
        //tomcat.addServlet(contextPath, "testServlet", "");
        tomcat.addWebapp(contextPath, baseDir);
        tomcat.start();
    }

    private void stopTomcat() throws LifecycleException
    {
        tomcat.stop();
    }

    public static void main(String[] args)
    {
        try
        {
            int port = 8080;
            String contextPath = "/";
            String baseDir = new File(TomcatTest.class.getResource("/static").getFile()).getAbsolutePath(); // 项目中web目录名称，以前版本为WebRoot、webapp、webapps，现在为WebContent
            TomcatTest tomcat = new TomcatTest();
            tomcat.startTomcat(port, contextPath, baseDir);
            // 由于Tomcat的start方法为非阻塞方法,加一个线程睡眠模拟线程阻塞.
            Thread.sleep(10000000);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
