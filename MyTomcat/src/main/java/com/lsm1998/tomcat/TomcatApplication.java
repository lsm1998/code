package com.lsm1998.tomcat;

import com.lsm1998.tomcat.server.TomcatServer;
import lombok.extern.slf4j.Slf4j;

/**
 * @作者：刘时明
 * @时间：2019/5/25-22:47
 * @作用：
 */
@Slf4j
public class TomcatApplication
{
    public static void main(String[] args)
    {
        log.info("start!!!");
        TomcatServer server=new TomcatServer();
        server.start();
    }
}
