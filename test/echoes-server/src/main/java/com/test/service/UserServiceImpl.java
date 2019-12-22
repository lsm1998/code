/*
 * 作者：刘时明
 * 时间：2019/12/21-16:23
 * 作用：
 */
package com.test.service;

import com.lsm1998.echoes.annotaion.MyRpcServer;
import com.test.dto.UserDto;

import java.util.List;

@MyRpcServer(timeout = 3000)
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
}
