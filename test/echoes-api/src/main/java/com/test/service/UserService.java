/*
 * 作者：刘时明
 * 时间：2019/12/21-16:20
 * 作用：
 */
package com.test.service;

import com.test.dto.UserDto;

import java.util.List;

public interface UserService
{
    List<UserDto> list();

    boolean insertUser(UserDto user);

    String hello(String name);

    void sayHello();
}
