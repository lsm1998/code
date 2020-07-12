package com.lsm1998.data.service.impl;

import com.lsm1998.data.domain.User;
import com.lsm1998.data.mapper.UserMapper;
import com.lsm1998.data.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService
{
    @Autowired(required = false)
    private UserMapper userMapper;

    @Override
    public int saveUser(User user)
    {
        return userMapper.insert(user);
    }

    @Override
    public int updateUser(User user)
    {
        return userMapper.updateById(user);
    }

    @Override
    public List<User> getList()
    {
        return userMapper.selectList(null);
    }

    @Override
    public User get(Long id)
    {
        return userMapper.selectById(id);
    }
}
