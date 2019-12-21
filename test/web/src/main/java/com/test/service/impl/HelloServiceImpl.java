/**
 * 作者：刘时明
 * 时间：2019/12/20-18:04
 * 作用：
 */
package com.test.service.impl;

import com.lsm1998.spring.beans.annotation.MyService;
import com.test.service.HelloService;

@MyService
public class HelloServiceImpl implements HelloService
{
    @Override
    public String hello(String name)
    {
        return "hello:" + name;
    }
}
