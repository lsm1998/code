package com.lsm1998.data.service;

import com.lsm1998.data.domain.User;

import java.util.List;

public interface UserService
{
    int saveUser(User user);

    int updateUser(User user);

    List<User> getList();

    User get(Long id);
}
