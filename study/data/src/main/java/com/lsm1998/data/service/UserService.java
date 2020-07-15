package com.lsm1998.data.service;

import com.lsm1998.data.annotation.Master;
import com.lsm1998.data.annotation.Slave;
import com.lsm1998.data.domain.User;

import java.util.List;

public interface UserService
{
    @Master
    int saveUser(User user);

    @Master
    int updateUser(User user);

    @Slave
    List<User> getList();

    @Slave
    User get(Long id);
}
