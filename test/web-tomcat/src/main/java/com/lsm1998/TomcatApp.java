package com.lsm1998;

import com.lsm1998.tomcat.Tomcat;

/**
 * @program: code
 * @description:
 * @author: lsm
 * @create: 2020-04-09 10:19
 **/
public class TomcatApp
{
    public static void main(String[] args)
    {
        Tomcat tomcat=new Tomcat();
        try
        {
            tomcat.start();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
