package com.lsm1998.tomcat;

import com.lsm1998.tomcat.server.TomcatServer;

/**
 * @作者：刘时明
 * @时间：2019/5/25-22:47
 * @作用：
 */
public class TomcatApplication
{
    public static void main(String[] args)
    {
        TomcatServer server=new TomcatServer();
        server.start();
    }
}
