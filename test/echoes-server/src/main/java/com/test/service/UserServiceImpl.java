/*
 * 作者：刘时明
 * 时间：2019/12/21-16:23
 * 作用：
 */
package com.test.service;

import com.lsm1998.net.echoes.common.annotaion.EchoesRpcServer;
import com.test.dto.UserDto;

import java.util.List;

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
}
