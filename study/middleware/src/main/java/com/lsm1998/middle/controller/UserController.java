/**
 * 作者：刘时明
 * 时间：2021/3/25
 */
package com.lsm1998.middle.controller;

import com.lsm1998.middle.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController
{
    @Autowired
    private UserMapper userMapper;

    @GetMapping()
    public Object list()
    {
        return userMapper.findAll();
    }
}
