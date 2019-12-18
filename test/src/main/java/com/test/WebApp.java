/*
 * 作者：刘时明
 * 时间：2019/12/18-21:44
 * 作用：
 */
package com.test;

import com.lsm1998.springboot.MySpringApplication;
import com.lsm1998.spring.beans.annotation.MySpringBootApplication;

@MySpringBootApplication
public class WebApp
{
    public static void main(String[] args)
    {
        MySpringApplication.run(WebApp.class, args);
    }
}
