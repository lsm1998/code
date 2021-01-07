/*
 * 作者：刘时明
 * 时间：2019/12/21-16:23
 * 作用：
 */
package com.test.service;

import com.lsm1998.echoes.common.annotaion.EchoesRpcServer;
import com.test.dto.UserDto;

import java.util.List;

// 通过注解注册rpc服务类
@EchoesRpcServer(timeout = 10000)
public class UserServiceImpl implements UserService
{
    @Override
    public List<UserDto> list()
    {
        return null;
    }

    @Override
    public boolean insertUser(UserDto user)
    {
        return false;
    }

    @Override
    public String hello(String name)
    {
        return "hello :"+name;
    }

    @Override
    public void sayHello()
    {
        System.out.println("hello");
    }
}
