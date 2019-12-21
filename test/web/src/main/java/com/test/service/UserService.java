/*
 * 作者：刘时明
 * 时间：2019/12/21-14:09
 * 作用：
 */
package com.test.service;

import com.test.entity.User;

import java.util.List;

public interface UserService
{
    int insertUser(User user);

    List<User> list(int page,int size);
}
